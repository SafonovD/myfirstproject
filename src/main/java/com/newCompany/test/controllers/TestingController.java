package com.newCompany.test.controllers;

import com.newCompany.test.dto.EntryDTO;
import com.newCompany.test.dto.ExamDto;
import com.newCompany.test.mapper.AnswerMapper;
import com.newCompany.test.mapper.QuestionsMapper;
import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Question;
import com.newCompany.test.model.Result;
import com.newCompany.test.model.User;
import com.newCompany.test.repositories.UserRepository;
import com.newCompany.test.services.AnswerService;
import com.newCompany.test.services.ExamService;
import com.newCompany.test.services.QuestionsService;
import com.newCompany.test.services.ResultService;
import com.newCompany.test.services.impl.ResultServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TestingController {

    @Value("${exam.time.minutes}")
    private Integer examTimeMins;

    private final QuestionsService questionsService;
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final QuestionsMapper questionsMapper;
    private final UserRepository userRepository;
    private final ExamService examService;
    private final ResultService resultService;

        @RequestMapping("/test")
        public String home(Model model, HttpServletRequest request) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.isAuthenticated()) {

            final Long examId = (Long) request.getSession().getAttribute("examId");

            final Exam exam = examService.getExam(examId);

            final List<Question> questions = questionsService.getQuestionForExam(examId);

            final Result result = new Result();
            result.setExam_id(examId);
            result.setUser_id(getUser().getId());
            result.setStart(Calendar.getInstance().getTime());
            result.setQuestionCount(questions.size());

            final Long resultId = resultService.insertResult(result);

            model.addAttribute("results", new ExamDto(resultId, examId));
            model.addAttribute("examName", String.format("Добро пожаловать \"%s\"!", exam.getName()));
            model.addAttribute("questions", questionsMapper.questionsToDto(questions));

            request.getSession().setAttribute("examStarted", result.getStart().getTime());

            final int remaining = getRemainingTime(request);
            model.addAttribute("examTime", remaining);

            return "test";
        } else {

            return "redirect:/index/test";
        }
    }

    private int getRemainingTime(HttpServletRequest request) {
        final long start = (long) request.getSession().getAttribute("examStarted");
        final int remaining = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
        return remaining;
    }

    @RequestMapping(value = "/questions/{id}")
    @ResponseBody
    public List<Question> getQuestionsForExam(@PathVariable("id") Long examId) {
        return questionsService.getQuestionForExam(examId);
    }

    @RequestMapping(value = "/answers/{id}")
    @ResponseBody
    public List<EntryDTO> getAnswersForQuestion(@PathVariable("id") Long questionId) {
        return answerMapper.answersToDTO(answerService.getAnswerForQuestion(questionId));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String submitResult(Model model, @Valid @ModelAttribute("results") ExamDto frm,
                               BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // TODO?
        }
        log.info("Submit: {}", frm);

        request.getSession().removeAttribute("examId");

        final Result protocol = resultService.getResult(frm.getId());
        protocol.setFinish(Calendar.getInstance().getTime());

        resultService.calcGrade(protocol, frm.getExamId(), frm.getAnswers());

        resultService.updateResult(protocol);

        log.info("Submit: {}", protocol);

        return "redirect:/stat/" + protocol.getId();
    }

//    @GetMapping( "/stat/{id}")
    @RequestMapping(value = "/stat/{id}", method = RequestMethod.GET)
    public String stat(@PathVariable("id") Long protocolId, Model model) {
        if (protocolId < 1) {
            return "redirect:/test";
        }
        final Result result = resultService.getResult(protocolId);

        if (result == null) {
            return "redirect:/test";
        }
        log.info("Stat: {}", result);

        final Exam exam = examService.getExam(result.getExam().getId());
        model.addAttribute("title", String.format("Твой тест %s", exam.getName()));
        model.addAttribute("start", result.getStart());
        model.addAttribute("finish", result.getFinish());
        model.addAttribute("questionCount", result.getQuestionCount());
        model.addAttribute("grade", result.getGrade());
        model.addAttribute("maxGrade", ResultServiceImpl.MAX_GRADE);
        return "stat";
    }



    @RequestMapping(value = "/time")
    @ResponseBody
    public Integer timer(HttpServletRequest request) {
        return getRemainingTime(request);
    }

    private User getUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

}
