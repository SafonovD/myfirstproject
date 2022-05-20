package com.newCompany.test.services;

import com.newCompany.test.dto.AnswersDto;
import com.newCompany.test.model.Answer;

import java.util.List;

public interface AnswerService {

    void insertAnswer(Integer questionId, AnswersDto answersDto);

    Answer findByName(String name);

    List<Answer> getAll();

    Answer findById(Long id);

    List<Answer> getAnswerForQuestion(Long questionId);
}
