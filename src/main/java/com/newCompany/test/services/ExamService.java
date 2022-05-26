package com.newCompany.test.services;

import com.newCompany.test.model.Exam;

import java.util.List;

public interface ExamService {

    Exam getRandomExam();

    Exam getExam(Long examId);

    Exam save(Exam exam);

    List<Exam> getAll();

}
