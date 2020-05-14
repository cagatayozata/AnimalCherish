package com.team1.animalproject.view.utils;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
@Scope ("application")
@Data
public class ApplicationDisplaySettingsUtil implements Serializable {

	private static final long serialVersionUID = 6541254390915456102L;

	private String dataTablePerPageTemplate;
	private String dataTableDefaultPageSize;
	private String dataTableOnlyTimeFormat;
	private String dataTableTimeFormat;
	private String dataTableDateFormat;
	private String dataTablePageReportTemplate;
	private String dataTablePaginatorTemplate;
	private String dataTablePaginatorPosition;
	private String dataTableFilterEvent;
	private String dataTableFilterDelay;
	private String dataTableEmptyMessage;
	private int spinnerDigitSize;
	private int spinnerMaxSize;
	private int spinnerMinSize;
	private int gosterilecekKarakterSayisi;

	@PostConstruct
	public void init() {

		dataTablePerPageTemplate = "5,10,25";
		dataTableDefaultPageSize = "10";
		dataTableOnlyTimeFormat = "dd/MM/yyyy HH:mm";
		dataTableTimeFormat = "dd/MM/yyyy HH:mm:ss";
		dataTableDateFormat = "dd/MM/yyyy";
		dataTablePaginatorTemplate = "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
		dataTablePageReportTemplate = " {currentPage}/{totalPages} / {totalRecords} ";
		dataTablePaginatorPosition = "bottom";
		dataTableFilterEvent = "enter";
		dataTableFilterDelay = "1000";
		dataTableEmptyMessage = "general.no.record.found";
		spinnerDigitSize = 3;
		spinnerMaxSize = 500;
		spinnerMinSize = 0;
		gosterilecekKarakterSayisi = 20;
	}
}
