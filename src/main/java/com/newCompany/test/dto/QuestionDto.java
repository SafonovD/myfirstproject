package com.newCompany.test.dto;

import com.newCompany.test.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {

    private Long id;
    @NotNull
    private String name;
    private boolean multiAnswer = false;

//    private List<Answer> answersList;

    public QuestionDto(String name, boolean multiAnswer, Long id) {
        this.name = name;
        this.multiAnswer = multiAnswer;
        this.id = id;
    }

    public QuestionDto(Question question) {
        this(question.getId(), question.getName(), question.isMultyAnswer());
    }

}
