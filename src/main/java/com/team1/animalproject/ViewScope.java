package com.team1.animalproject;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ViewScope implements Scope {
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getViewRoot() != null) {
			Map<String, Object> viewMap = context.getViewRoot().getViewMap();

			if (viewMap.containsKey(name)) {
				return viewMap.get(name);
			} else {
				Object object = objectFactory.getObject();
				viewMap.put(name, object);

				return object;
			}
		}
		return null;
	}

	@Override
	public Object remove(String name) {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
	}

	@Override
	public void registerDestructionCallback(String s, Runnable runnable) {

	}

	@Override
	public Object resolveContextualObject(String s) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}
}
