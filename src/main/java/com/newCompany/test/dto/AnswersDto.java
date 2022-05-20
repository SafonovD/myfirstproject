package com.newCompany.test.dto;

import com.newCompany.test.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswersDto {

    private String name;
    public boolean correctAnswer;


}
