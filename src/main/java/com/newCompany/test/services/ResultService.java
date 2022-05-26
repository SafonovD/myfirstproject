package com.newCompany.test.services;

import com.newCompany.test.model.Result;

import java.util.List;

public interface ResultService {

    Result updateResult(Result result);

    Result getResult(Long resultId);

    Long insertResult(Result result);

    void calcGrade(Result result, Long examId, List<Long> userAnswers);


}
