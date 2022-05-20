package com.newCompany.test.services.impl;

import com.newCompany.test.dto.AnswersDto;
import com.newCompany.test.model.Answer;
import com.newCompany.test.repositories.AnswerRepository;
import com.newCompany.test.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiсeImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    private static Long quest_ID;

    @Override
    public void insertAnswer(Integer questionId, AnswersDto answersDto) {
        Answer answer = new Answer();
        if (questionId != null) {

            answer.setName(answersDto.getName());
            answer.setCorrectAnswer(answersDto.isCorrectAnswer());
            quest_ID = Long.valueOf(questionId);
            answer.setQuestion_id(quest_ID);
            answerRepository.save(answer);
        } else {
            answer.setName(answersDto.getName());
            answer.setCorrectAnswer(answersDto.isCorrectAnswer());
            answer.setQuestion_id(quest_ID);

            answerRepository.save(answer);
        }
    }

    @Override
    public Answer findByName(String name) {
        Answer answers = answerRepository.findByName(name);
        return answers;
    }

    @Override
    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public List<Answer> getAnswerForQuestion(Long questionId) {
        final List<Answer> answers = answerRepository.findByQuestion_id(questionId);
        return answers;
    }

}