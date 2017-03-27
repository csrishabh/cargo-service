package com.cargo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cargo.config.ExportReportRestURIConstants;
import com.cargo.reports.CollectionReportBuilder;
import com.cargo.reports.PaymentReportBuilder;
import com.cargo.service.ExportReportService;

@RestController
public class ExportReportController {
	
	@Autowired
	private ExportReportService exportReportService;
	
	@RequestMapping(value = ExportReportRestURIConstants.GENERATE_PAYMENT_REPORT ,method = RequestMethod.GET)
	public ModelAndView exportPaymentReport(@RequestParam Map<String, String> param , HttpServletResponse response){
		
		Map<String, Object> fileData = exportReportService.exportPaymentReport(param);
		 response.setContentType( "application/ms-excel" );
	     response.setHeader( "Content-disposition", "attachment; filename=myfile.xls" );
		return new ModelAndView(new PaymentReportBuilder(), fileData);
		
	}
	
	@RequestMapping(value = ExportReportRestURIConstants.GENERATE_COLLECTION_REPORT ,method = RequestMethod.GET)
	public ModelAndView exportCollectionReport(@RequestParam Map<String, String> param , HttpServletResponse response){
		
		Map<String, Object> fileData = exportReportService.exportCollectionReport(param);
		response.setContentType( "application/ms-excel" );
	    response.setHeader( "Content-disposition", "attachment; filename=myfile.xls" );
		return new ModelAndView(new CollectionReportBuilder() , fileData);
		
	}

}
