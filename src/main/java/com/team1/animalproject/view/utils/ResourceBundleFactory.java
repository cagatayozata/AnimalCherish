package com.team1.animalproject.view.utils;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class ResourceBundleFactory {

	private static FacesContext facesContext;

	private ResourceBundleFactory() {
	}

	public static ResourceBundle getResourceBundle(ResourceBundleEnum bundleEnum) {
		facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getResourceBundle(facesContext, bundleEnum.getBundleKey());

	}

}
