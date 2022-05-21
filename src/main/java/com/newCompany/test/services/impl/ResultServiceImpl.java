package com.newCompany.test.services.impl;

import com.newCompany.test.model.Answer;
import com.newCompany.test.model.Result;
import com.newCompany.test.repositories.AnswerRepository;
import com.newCompany.test.repositories.ResultRepository;
import com.newCompany.test.services.ExamService;
import com.newCompany.test.services.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    public static final double MAX_GRADE = 100; // 100 %

    private final ResultRepository resultRepository;
    private final AnswerRepository answerRepository;

    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    @Transactional
    public Result updateResult(Result result) {
        return resultRepository.save(result);
    }

    public Result getResult(Long resultId) {
        Result result =  resultRepository.findById(resultId).orElse(null);
        if(result == null){
            log.warn("IN findById - no exam faund by id: {}",resultId);
            return null;
        }
        log.info("IN findById - exam: {} found by id: {}", result ,resultId);
        return result;
    }
    @Transactional
    public Long insertResult(Result result) {
        final Result p = resultRepository.save(result);
        resultRepository.flush();
        return result.getId();
//        return p.getId();
    }

    public void calcGrade(Result result, Long examId, List<Long> userAnswers) {
        if (result == null || userAnswers == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final List<Answer> correctAnswers = answerRepository.findByQuestionExam_idAndCorrectAnswer(examId, true);
        if (correctAnswers.size() == 0) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }

        final float step = (float) (MAX_GRADE / correctAnswers.size());
        final Set<Long> correctCount = new HashSet<>();
        final Set<Long> incorrectCount = new HashSet<>();

        final Set<Long> answers = userAnswers.stream().filter(a -> a > 0).collect(Collectors.toSet());

        float grade = 0;
        for (final Answer answer : correctAnswers) {
            final Long id = answer.getId();
            if (answers.contains(id)) {
                grade += step;
                correctCount.add(id);
            } else {
                incorrectCount.add(id);
            }
        }
        // fix for multi-answers questions
        correctCount.removeAll(incorrectCount);

        // оценка
        result.setCorrectAnswers(correctCount.size());
        result.setGrade(Math.round(grade));
    }

}
