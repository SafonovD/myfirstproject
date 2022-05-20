package com.newCompany.test.repositories;

import com.newCompany.test.model.Answer;
import com.newCompany.test.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByName(String name);

    List<Answer> findByQuestion(Question question);

    List<Answer> findByQuestion_id(Long questionId);

    List<Answer> findByQuestionExam_idAndCorrectAnswer(Long examId, Boolean isCorrect);



}
