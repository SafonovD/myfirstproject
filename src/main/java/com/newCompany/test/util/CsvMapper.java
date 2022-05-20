package com.newCompany.test.util;

import com.newCompany.test.dto.QuestionsAndAnswersDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CsvMapper {

    public List<QuestionsAndAnswersDto> getQuestionInfo(File file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(CsvMapper.class.getResourceAsStream(file.getAbsolutePath())))) {
            CsvToBean<QuestionsAndAnswersDto> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(QuestionsAndAnswersDto.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.stream().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}