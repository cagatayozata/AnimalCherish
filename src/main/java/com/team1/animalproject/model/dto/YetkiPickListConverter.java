package com.team1.animalproject.model.dto;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.team1.animalproject.model.Yetki;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "yetkiPickListConverter")
public class YetkiPickListConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String string;
		if (object == null) {
			string = "";
		} else {
			try {
				string = String.valueOf(((Yetki) object).getId());
			} catch (ClassCastException cce) {
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private Yetki getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<Yetki> dualList;
		try {
			dualList = (DualListModel<Yetki>) ((PickList) component).getValue();
			Yetki resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if (resource == null) {
				resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}

			return resource;
		} catch (ClassCastException cce) {
			throw new ConverterException();
		} catch (NumberFormatException nfe) {
			throw new ConverterException();
		}
	}

	private Yetki getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final Yetki resource = (Yetki) object;
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}