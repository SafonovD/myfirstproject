package com.newCompany.test.repositories;

import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {

    Optional<Integer> findById(String name);

    Question findByName(String name);

    List<Question> findByExam(Exam exam);

    List<Question> findByExam_id(Long examId);

    // Нахождение первого вопроса без ответа на экзамене
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);

    int countByExam(Exam exam);
}
