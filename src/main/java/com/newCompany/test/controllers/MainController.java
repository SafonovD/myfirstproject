package com.newCompany.test.controllers;

import com.newCompany.test.dto.AnswersDto;
import com.newCompany.test.dto.QuestionDto;
import com.newCompany.test.dto.QuestionsAndAnswersDto;
import com.newCompany.test.model.Exam;
import com.newCompany.test.services.AnswerService;
import com.newCompany.test.services.ExamService;
import com.newCompany.test.services.QuestionsService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final AnswerService answerService;
    private final QuestionsService questionsService;
    private final ExamService examService;
    private Collection<QuestionsAndAnswersDto> questionsAndAnswersDtos = null;

    @GetMapping("/index")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> error, HttpServletRequest request) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        log.info("AUTH: {} [{}]", auth, auth.isAuthenticated());

        final Exam exam = examService.getRandomExam();

        request.getSession().setAttribute("examId", exam.getId());

        model.addAttribute("examName", exam.getName());
        model.addAttribute("examDescription", exam.getDescription());

        return "redirect:/index";
    }

    @GetMapping("/admin")
    public String showAdminPage(){
        return "admin";
    }

    @PostMapping("/admin")
    public String pageForAdmin(@RequestParam("file") MultipartFile file,
                               @RequestParam ("descript") String description,
                               @RequestParam ("themename") String  themeName,Model model) {

        final Exam examen = new Exam(themeName, description);
        final Exam examName = examService.save(examen);

        if (file == null) {
            model.addAttribute("message", "Выберите CSV file для загрузки.");
            model.addAttribute("status", false);
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<QuestionsAndAnswersDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(QuestionsAndAnswersDto.class)
                        .withSeparator(';')
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                questionsAndAnswersDtos = csvToBean.stream().collect(Collectors.toList());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        questionsAndAnswersDtos.stream().map(questionsAndAnswersDto -> Pair.of(
                new QuestionDto(questionsAndAnswersDto.getQuestion(),
                        questionsAndAnswersDto.isMultiAnswer(), examName.getId()),
                new AnswersDto(questionsAndAnswersDto.getAnswer(),
                        questionsAndAnswersDto.isCorrectAnswer())))
                .forEach(pair ->
        {
            final Optional<Integer> question = questionsService.insertQuestion(pair.getLeft());
            answerService.insertAnswer(question.orElse(null), pair.getRight());
        });

        return "admin";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
