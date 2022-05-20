package com.newCompany.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "correct_answer",nullable = false)
    private boolean correctAnswer;

    @Column(name = "questions_id")
    private Long question_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questions_id",referencedColumnName = "id",insertable = false, updatable = false)
    private Question question;

}
