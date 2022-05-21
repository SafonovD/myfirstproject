package com.newCompany.test.services.impl;

import com.newCompany.test.model.Exam;
import com.newCompany.test.model.Result;
import com.newCompany.test.repositories.ExamRepository;
import com.newCompany.test.repositories.ResultRepository;
import com.newCompany.test.services.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ResultRepository resultRepository;

    public Exam getRandomExam() {
        final int examCount = (int) examRepository.count();
        assert examCount > 0;
        final int examNumber = new Random().nextInt(examCount);
        return examRepository.findAll().get(examNumber);
    }


    public Exam getExam(Long examId) {
        final Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            log.warn("IN findById - no exam faund by id: {}", examId);
            return null;
        }
        log.info("IN findById - exam: {} found by id: {}", exam, examId);
        return exam;
    }

    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAll() {
        List<Exam> examList = examRepository.findAll();
        log.info("IN getAll - {} exzamen found ",examList.size());
        return examList;
    }
}
