package com.team1.animalproject.view.utils;

import com.team1.animalproject.exception.BaseException;
import com.team1.animalproject.exception.BaseExceptionType;

import java.util.List;

public class RestException extends BaseException {

	private static final long serialVersionUID = 8137308385800569772L;

	public RestException(BaseExceptionType type, String message, List<String> errors) {
		super(type, message, errors);
	}

}
