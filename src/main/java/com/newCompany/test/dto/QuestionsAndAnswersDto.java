package com.newCompany.test.dto;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAndAnswersDto {

    @CsvBindByName(column = "Question")
    private String question;
    @CsvBindByName(column = "Answer")
    private String answer;
    @CsvBindByName(column = "CorrectAnswer")
    private boolean correctAnswer;
    @CsvBindByName(column = "MultiAnswer")
    private boolean multiAnswer;

}
