package com.team1.animalproject.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode ()
public class ViewException extends BaseException {

	private static final long serialVersionUID = -6198437952417001060L;

	@Getter
	@Setter
	private List<BusinessRuleException> businessRuleExceptions;

	public ViewException() {
		super(BaseExceptionType.VIEW_EXCEPTION, null, null);
		this.businessRuleExceptions = new ArrayList<>();
	}

	public ViewException(List<BusinessRuleException> businessRuleExceptions) {
		super(BaseExceptionType.VIEW_EXCEPTION, businessRuleExceptions.toString(), null);
		this.businessRuleExceptions = businessRuleExceptions;
	}

	public ViewException(BusinessRuleException businessRuleException) {
		super(BaseExceptionType.VIEW_EXCEPTION, businessRuleException.toString(), null);
		this.businessRuleExceptions = Arrays.asList(businessRuleException);
	}

}
