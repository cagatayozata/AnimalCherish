package com.team1.animalproject.view.utils;

import lombok.Getter;

public enum ResourceBundleEnum {

	SERVER_MESSAGE_BUNDLE("servermessage"),
	FACESMESSAGE_MESSAGE_BUNDLE("facesMessage"),
	ENUM_MESSAGES_BUNDLE("msg"),
	EXECEPTION_MESSAGES_BUNDLE("exmsg");

	@Getter
	private final String bundleKey;

	ResourceBundleEnum(String bundleKey) {
		this.bundleKey = bundleKey;
	}

}
