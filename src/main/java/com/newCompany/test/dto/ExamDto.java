package com.newCompany.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto implements Serializable {

    private Long id;
    private Long examId;
//    private String name;
//    private String description;

    private List<Long> answers;

    public ExamDto(Long resultId, Long examId) {
        this.id = resultId;
        this.examId = examId;
    }
}
