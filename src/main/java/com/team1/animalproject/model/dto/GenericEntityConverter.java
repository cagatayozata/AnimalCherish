package com.team1.animalproject.model.dto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings ("rawtypes")
public class GenericEntityConverter implements Converter {

	private static final String KEY = "com.team1.animalproject.converter";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object entity) {

		if(entity == null){
			return "";
		}

		String uuid;
		if(entity instanceof Identifier){
			uuid = (String) ((Identifier) entity).getId();
		} else {
			uuid = UUID.randomUUID().toString();
		}

		getViewMap(context).put(uuid, entity);
		return uuid;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String uuid) {

		if(uuid.isEmpty()){
			return null;
		}

		return getViewMap(context).get(uuid);

	}

	@SuppressWarnings ("unchecked")
	private Map<String, Object> getViewMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		Map<String, Object> idMap = (Map) viewMap.get(KEY);
		if(idMap == null){
			idMap = new HashMap<String, Object>();
			viewMap.put(KEY, idMap);
		}
		return idMap;
	}

}
