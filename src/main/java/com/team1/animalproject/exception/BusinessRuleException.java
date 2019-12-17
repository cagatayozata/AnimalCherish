package com.team1.animalproject.exception;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"baseExceptionType"}, includeFieldNames = false)
@Builder
public class BusinessRuleException {

	private BaseExceptionType baseExceptionType;
	@Builder.Default
	private List<String> params = new ArrayList<>();
	private boolean messageBundle;
	private String problematicItem;

}
