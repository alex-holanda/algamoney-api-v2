package com.algamoney.algamoney.common.web;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class YearMonthConverter implements Converter<String, YearMonth> {

	@Override
	public YearMonth convert(String text) {
		return YearMonth.parse(text, DateTimeFormatter.ofPattern("yyyy-MM"));
	}

}
