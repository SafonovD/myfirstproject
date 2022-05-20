package com.newCompany.test.services;

import com.newCompany.test.dto.QuestionDto;
import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionsService {

    Optional<Integer> insertQuestion(QuestionDto questions);

    List<QuestionDto> getAll();

    List<Question> getQuestionForExam(Long examId);

    Question getNextQuestion(Exam exam, Set<Integer> ids);


}
