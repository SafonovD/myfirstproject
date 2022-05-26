package com.newCompany.test.mapper;

import com.newCompany.test.dto.AnswersDto;
import com.newCompany.test.dto.EntryDTO;
import com.newCompany.test.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AnswerMapper {

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    AnswersDto toDto(Answer entity);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    Answer toEntity(AnswersDto dto);

   default List<EntryDTO> answersToDTO(List<Answer> answers) {
        return answers.stream().map(q -> new EntryDTO(q.getId(), q.getName())).collect(Collectors.toList());
    }
}
