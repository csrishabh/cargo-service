package com.cargo.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.cargo.service.responce.ConsignmentInfoResponse;

public class CollectionReportBuilder extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<ConsignmentInfoResponse> consignments = (List<ConsignmentInfoResponse>) model.get("consignments");
		
		    Sheet sheet = workbook.createSheet("Collection Sheet");
	        sheet.setDefaultColumnWidth(10);
	        
	        //for cell formatting
	        CreationHelper createHelper = workbook.getCreationHelper();
	        
	     // create style for header cells
	        CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setFontName("Arial");
	       
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFillForegroundColor(HSSFColor.BLUE.index);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        font.setColor(HSSFColor.WHITE.index);
	        style.setFont(font);
	        
	        
	       
	        // create header row
	        Row header = sheet.createRow(1);
	         
	        header.createCell(0).setCellValue("ID");
	        header.getCell(0).setCellStyle(style);
	        
	        header.createCell(1).setCellValue("Entry Date");
	        header.getCell(1).setCellStyle(style);
	        
	        header.createCell(2).setCellValue("Status");
	        header.getCell(2).setCellStyle(style);
	        
	        header.createCell(3).setCellValue("Dispatcher ID");
	        header.getCell(3).setCellStyle(style);
	        
	        header.createCell(4).setCellValue("CBID");
	        header.getCell(4).setCellStyle(style);
	         
	        header.createCell(5).setCellValue("Consinor");
	        header.getCell(5).setCellStyle(style);
	         
	        header.createCell(6).setCellValue("Consignee");
	        header.getCell(6).setCellStyle(style);
	         
	        header.createCell(7).setCellValue("From");
	        header.getCell(7).setCellStyle(style);
	        
	        header.createCell(8).setCellValue("To");
	        header.getCell(8).setCellStyle(style);
	        
	        header.createCell(9).setCellValue("Weignt");
	        header.getCell(9).setCellStyle(style);
	        
	        header.createCell(10).setCellValue("Quantity");
	        header.getCell(10).setCellStyle(style);
	        
	        header.createCell(11).setCellValue("Rate");
	        header.getCell(11).setCellStyle(style);
	        
	        header.createCell(12).setCellValue("Carrige Charge");
	        header.getCell(12).setCellStyle(style);
	        
	        header.createCell(13).setCellValue("Other Charge");
	        header.getCell(13).setCellStyle(style);
	        
	        header.createCell(14).setCellValue("Service Tax");
	        header.getCell(14).setCellStyle(style);
	        
	        header.createCell(15).setCellValue("Total");
	        header.getCell(15).setCellStyle(style);
	        
	        header.createCell(16).setCellValue("Payment Type");
	        header.getCell(16).setCellStyle(style);
	        
	        header.createCell(17).setCellValue("Type");
	        header.getCell(17).setCellStyle(style);
	          
	        header.createCell(18).setCellValue("Remarks");
	        header.getCell(18).setCellStyle(style);
	        
	        header.createCell(19).setCellValue("Driver Name");
	        header.getCell(19).setCellStyle(style);
	        
	        header.createCell(20).setCellValue("Via");
	        header.getCell(20).setCellStyle(style);
	        
	        header.createCell(21).setCellValue("State");
	        header.getCell(21).setCellStyle(style);
	        // create data rows
	        int rowCount = 2;
	        
	        CellStyle dateColStyle = workbook.createCellStyle();
	        dateColStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));
	        for (ConsignmentInfoResponse consignment : consignments) {
	            Row aRow = sheet.createRow(rowCount++);
	            aRow.createCell(0).setCellValue(consignment.getDisplayIndex());
	            aRow.createCell(1).setCellValue(consignment.getEntry_Date());
	            aRow.getCell(1).setCellStyle(dateColStyle);
	            aRow.createCell(2).setCellValue(consignment.getStatus());
	            aRow.createCell(3).setCellValue((consignment.getDispatcherId()!=null)?consignment.getDispatcherId():0);
	            aRow.createCell(4).setCellValue(consignment.getCbId());
	            aRow.createCell(5).setCellValue(consignment.getConsignor());
	            aRow.createCell(6).setCellValue(consignment.getConsingee());
	            aRow.createCell(7).setCellValue(consignment.getFrom());
	            aRow.createCell(8).setCellValue(consignment.getTo());
	            aRow.createCell(9).setCellValue((consignment.getWeight()));
	            aRow.createCell(10).setCellValue(consignment.getQuantity());
	            aRow.createCell(11).setCellValue(consignment.getRate());
	            aRow.createCell(12).setCellValue(consignment.getCarrige_charge());
	            aRow.createCell(13).setCellValue(consignment.getOther_charge()); 
	            aRow.createCell(14).setCellValue(consignment.getS_Tax());
	            aRow.createCell(15).setCellValue(consignment.getTotal());
	            aRow.createCell(16).setCellValue(consignment.getPayment_Type());
	            aRow.createCell(17).setCellValue(consignment.getType());
	            aRow.createCell(18).setCellValue(consignment.getRemarks());
	            aRow.createCell(19).setCellValue(consignment.getDriverName());
	            aRow.createCell(20).setCellValue(consignment.getVia());
	            aRow.createCell(21).setCellValue(consignment.getState());
	            
	        }
	                   
	}

}
