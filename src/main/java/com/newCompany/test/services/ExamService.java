package com.newCompany.test.services;

import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Result;
import com.newCompany.test.model.User;

import java.util.List;
import java.util.Optional;

public interface ExamService {

    Exam getRandomExam();



    Exam getExam(Long examId);

    Exam save(Exam exam);

    List<Exam> getAll();

}
