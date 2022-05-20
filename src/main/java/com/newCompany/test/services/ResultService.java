package com.newCompany.test.services;

import com.newCompany.test.model.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {

    Result updateResult(Result result);

    Result getResult(Long resultId);

    void calcGrade(Result result, Long examId, List<Long> userAnswers);


}
