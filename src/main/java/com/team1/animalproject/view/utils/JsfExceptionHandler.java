package com.team1.animalproject.view.utils;

import com.team1.animalproject.exception.ViewException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	@SuppressWarnings ("deprecation")
	public JsfExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while(i.hasNext()){
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();
			Throwable rootCause = ExceptionUtils.getRootCause(t);
			if(rootCause instanceof ViewException){
				log.error(rootCause.getMessage());
			} else if(rootCause instanceof RestException){
				log.error(((RestException) rootCause).getType().toString() + " - " + rootCause.getMessage());
			} else {
				log.error(t.getMessage(), t);
			}

			final FacesContext fc = FacesContext.getCurrentInstance();
			if(fc == null || fc.getResponseComplete()){
				return;
			}
			final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			final NavigationHandler nav = fc.getApplication().getNavigationHandler();

			//here you do what ever you want with exception
			try{

				// error message to be given
				ResourceBundle bundle = fc.getApplication().getResourceBundle(fc, "exmsg");
				List<String> errorCodes = new ArrayList<>();
				List<String> errorMessages = new ArrayList<>();
				String errorCodePrefix = bundle.getString("general.error.heading.prefix");
				String errorCode = bundle.getString("general.error.code");
				String errorMessage = bundle.getString("general.error.message");

				boolean multipleErrorMessage = false;

				// handle gbs rest exceptions
				if(rootCause instanceof ViewException){

					multipleErrorMessage = true;
					if(!CollectionUtils.isEmpty(((ViewException) rootCause).getBusinessRuleExceptions())){
						((ViewException) rootCause).getBusinessRuleExceptions().forEach(businessRuleException -> {
							errorCodes.add(businessRuleException.getBaseExceptionType().getCode());

							if(businessRuleException.isMessageBundle()){
								businessRuleException.setParams(businessRuleException.getParams().stream().map(p -> {
									if(ResourceBundleFactory.getResourceBundle(ResourceBundleEnum.ENUM_MESSAGES_BUNDLE).containsKey(p)){
										return ResourceBundleFactory.getResourceBundle(ResourceBundleEnum.ENUM_MESSAGES_BUNDLE).getString(p);
									}
									return p;
								}).collect(Collectors.toList()));
							}

							if(!CollectionUtils.isEmpty(businessRuleException.getParams())){
								errorMessages.add(MessageFormat.format(bundle.getString(businessRuleException.getBaseExceptionType().getValidationMessage()),
										businessRuleException.getParams().toArray(new String[businessRuleException.getParams().size()])));
							} else {
								errorMessages.add(MessageFormat.format(bundle.getString(businessRuleException.getBaseExceptionType().getValidationMessage()), businessRuleException.getParams()));

							}

						});
					} else {
						errorCode = ((ViewException) rootCause).getType().getCode();
						errorMessage = bundle.getString(((ViewException) rootCause).getType().getValidationMessage());
					}

				}  else if(rootCause instanceof RestException){

					errorCode = ((RestException) rootCause).getType().getCode();
					if(rootCause instanceof IExceptionCustomMessageContainer){
						errorMessage = bundle.getString(((IExceptionCustomMessageContainer) rootCause).getCustomMessage());
					} else {
						errorMessage = bundle.getString(((RestException) rootCause).getType().getValidationMessage());
					}

				}

				if(fc.getPartialViewContext().isAjaxRequest()){

					if(multipleErrorMessage){
						for(int ix = 0; ix < errorCodes.size(); ix++){
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, errorCodePrefix + errorCodes.get(ix) + " " + errorMessages.get(ix), null));
						}
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, errorCodePrefix + errorCode + " " + errorMessage, null));
					}

				} else {
					if(t instanceof ViewException){
						log.error(t.getMessage());
					} else {
						log.error("General error occured! ", t);
					}
					//redirect error page
					((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("ex", t);
					//TODO : use jsf navigation not needed to rewrite url and use parameters putted from here -> talip
					nav.handleNavigation(fc, null, "pretty:500-url-rwrite");
					fc.renderResponse();
				}

			} finally{
				//remove it from queue
				i.remove();
			}
		}
		//parent hanle
		getWrapped().handle();
	}
}