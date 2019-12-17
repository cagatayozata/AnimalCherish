package com.team1.animalproject.view.utils;

import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@Component
@Scope("application")
@Data
public class JSFUtil implements Serializable {

    private static final long serialVersionUID = 3827321153791034423L;

    public static final String URL_PARAM_SEPARATOR = "\\";
    public static final String URL_PARAM_SEPARATOR_REPORT = "/";

    //kodlar degistirilecek.
    public static void redirectWithParams(String url, String... pathParams) throws IllegalStateException {

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (pathParams != null) {
            for (String pathParam : pathParams) {
                sb.append(URL_PARAM_SEPARATOR);
                sb.append(pathParam);
            }
        }

        try {
            redirect(sb.toString());
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "general.url.redirect.error", null));
        }
    }

    public static String getUrlWithParams(String url, String... pathParams) throws IllegalStateException {
        return getUrl(url, URL_PARAM_SEPARATOR, pathParams);
    }

    public static String getUrlWithParamsForReport(String url, String... pathParams) throws IllegalStateException {
        return getUrl(url, URL_PARAM_SEPARATOR_REPORT, pathParams);
    }

    private static String getUrl(String url, String separator, String... pathParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (pathParams != null) {
            for (String pathParam : pathParams) {
                if (pathParam != null) {
                    sb.append(separator);
                    sb.append(pathParam);
                }
            }
        }
        return sb.toString();
    }

    public static void redirect(String url) throws IllegalStateException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public static String getOriginUrl() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("origin");
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static void executeScript(String script) {
        PrimeFaces.current().executeScript(script);
    }

    public static void jsfUpdate(String id) {
        PrimeFaces.current().ajax().update(id);
    }

    public static void scrollTo(String clientId) {
        PrimeFaces.current().scrollTo(clientId);
    }

    public static void hideDialog(String widgetVar) {
        PrimeFaces.current().executeScript("PF('" + widgetVar + "').hide();");
    }

    public static void showDialog(String widgetVar) {
        PrimeFaces.current().executeScript("PF('" + widgetVar + "').show();");
    }

    public static void scrollToPageTop() {
        PrimeFaces.current().executeScript("scrollToPageTop();");
    }

    public static void reload() {
        PrimeFaces.current().executeScript("$(window).attr('location').reload();");
    }

}
