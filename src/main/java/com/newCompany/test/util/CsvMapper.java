package com.newCompany.test.util;

import com.newCompany.test.dto.QuestionsAndAnswersDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.experimental.UtilityClass;

import java.io.Reader;

@UtilityClass
public class CsvMapper {

    public CsvToBean getCsvToBeanBuild(Reader reader) {
        return new CsvToBeanBuilder(reader)
                .withType(QuestionsAndAnswersDto.class)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }
}