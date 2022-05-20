package com.newCompany.test.services.impl;

import com.newCompany.test.dto.QuestionDto;
import com.newCompany.test.mapper.QuestionsMapper;
import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Question;
import com.newCompany.test.repositories.QuestionsRepository;
import com.newCompany.test.services.QuestionsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final QuestionsMapper questionsMapper;

    public Optional<Integer> insertQuestion(QuestionDto questionsDto) {

        final String questionsName = questionsDto.getName();

        if (StringUtils.isBlank(questionsName)) {
            return Optional.empty();
        }
        Question question = new Question();
        question.setName(questionsName);
        question.setExam_id(questionsDto.getId());

        if(BooleanUtils.isTrue(questionsDto.isMultiAnswer())){
            question.setMultyAnswer(questionsDto.isMultiAnswer());
        }else {
            question.setMultyAnswer(false);
        }
        questionsRepository.save(question);

        final Question result = questionsRepository.findByName(questionsName);
        String idString = String.valueOf(result.getId());
        Integer id = Integer.valueOf(idString);

        return Optional.of(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getAll() {
        return questionsMapper.questionsToDto(questionsRepository.findAll());
    }

    public List<Question> getQuestionForExam(Long examId) {
        return questionsRepository.findByExam_id(examId);
    }

    public Question getNextQuestion(Exam exam, Set<Integer> ids) {
        final Set<Integer> identificators = new HashSet<>(ids);
        if (identificators.size() == 0) {
            identificators.add(0); // избегаем null
        }
        return questionsRepository.findFirstByExamAndIdNotIn(exam, identificators);
    }

}