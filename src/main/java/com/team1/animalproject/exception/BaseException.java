package com.team1.animalproject.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties ({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 4022619064467268821L;
	private BaseExceptionType type;
	private String message;
	private List<String> errors;

}
