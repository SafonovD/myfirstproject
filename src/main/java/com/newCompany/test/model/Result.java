package com.newCompany.test.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "start")
    private Date start;

    @Column(name = "finish")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish;

    @Column(name = "question_count")
    private Integer questionCount;

    @Column(name = "correct_answers")
    private Integer correctAnswers;
    @Column(name = "grade")
    private Integer grade;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "exam_id")
    private Long exam_id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id",referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private Exam exam;

}
