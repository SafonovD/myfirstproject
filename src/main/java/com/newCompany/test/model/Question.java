package com.newCompany.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column (name = "multi_answer",nullable = false)
    private boolean multyAnswer;

    @Column(name = "exam_id")
    private Long exam_id;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id",referencedColumnName = "id", insertable = false, updatable = false)
    private Exam exam;


}
