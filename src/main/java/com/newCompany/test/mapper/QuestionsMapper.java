package com.newCompany.test.mapper;

import com.newCompany.test.dto.QuestionDto;
import com.newCompany.test.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public interface QuestionsMapper {

    @Mapping(target = "name", source = "entity.name")
    QuestionDto toDto(Question entity);

    @Mapping(target = "name", source = "dto.name")
    Question toEntity(QuestionDto dto);

    default List<QuestionDto> toDtos(List<Question> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<Question> toEntity(List<QuestionDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default Optional<QuestionDto> toOptionalDto(Optional<Question> entity) {
        return entity.map(this::toDto);
    }

    default List<QuestionDto> questionsToDto(List<Question> questions) {
        return questions.stream().map(q -> new QuestionDto(q)).collect(Collectors.toList());
    }

}
