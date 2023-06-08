package com.algamoney.algamoney.common.api.controller;

import java.time.OffsetDateTime;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoney.common.api.model.Problem;
import com.algamoney.algamoney.common.api.model.ProblemType;

@RestController
@RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
public class JsonErrorController extends AbstractErrorController {

	public JsonErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@GetMapping
	public ResponseEntity<Problem> error(HttpServletRequest request) {
		var status = this.getStatus(request);
		var problemType = ProblemType.of(status).orElse(ProblemType.SYSTEM_ERROR);
		var problem = Problem.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.build();
		
		return ResponseEntity.status(status).body(problem);
	}
}
