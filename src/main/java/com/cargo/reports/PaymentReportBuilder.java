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
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cargo.model.PaymentInfo;
import com.cargo.model.Person;
import com.cargo.service.responce.ConsignmentInfoResponse;

public class PaymentReportBuilder extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest arg2,
			HttpServletResponse response) throws Exception {
		List<PaymentInfo> payments = (List<PaymentInfo>) data.get("payments");
		Person person = (Person) data.get("person");
		List<ConsignmentInfoResponse> consignments = (List<ConsignmentInfoResponse>) data.get("consignments");
		Double totalDue = getTotalDueAmount(consignments);
		Double totalPaidAmt = getTotalPaidAmount(payments);
		Double balance = totalDue - totalPaidAmt;
		
		response.setHeader("Content-Disposition", "attachment; filename=\"my-xlsx-file.xlsx\"");
		// create a new Excel sheet
        Sheet sheet = workbook.createSheet("Payment Sheet");
        sheet.setDefaultColumnWidth(20);
        
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
        
        Row id = sheet.createRow(1);
        id.createCell(0).setCellValue("ID");
        id.getCell(0).setCellStyle(style);
        id.createCell(1).setCellValue(person.getId());
          
        Row name = sheet.createRow(2);
        name.createCell(0).setCellValue("Name");
        name.getCell(0).setCellStyle(style);
        name.createCell(1).setCellValue(person.getName());
        
        name.createCell(7).setCellValue("Total Due Amount");
        name.getCell(7).setCellStyle(style);
        name.createCell(8).setCellValue(totalDue);
         
        
        // create header row
        Row header = sheet.createRow(5);
         
        header.createCell(0).setCellValue("Date");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("Type");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("Receipt/Cheque Number");
        header.getCell(2).setCellStyle(style);
         
        header.createCell(3).setCellValue("Amount");
        header.getCell(3).setCellStyle(style);
          
        // create data rows
        int rowCount = 6;
        
        CellStyle dateColStyle = workbook.createCellStyle();
        dateColStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));
        for (PaymentInfo payment : payments) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(payment.getDate());
            aRow.getCell(0).setCellStyle(dateColStyle);
            aRow.createCell(1).setCellValue(payment.getType().toString());
            aRow.createCell(2).setCellValue(payment.getChequeNum());
            aRow.createCell(3).setCellValue(payment.getAmount());
        }
              
        Row totalPaid = sheet.createRow(sheet.getLastRowNum()+3);      
        totalPaid.createCell(0).setCellValue("Total Paid Amount");
        totalPaid.getCell(0).setCellStyle(style);
        totalPaid.createCell(1).setCellValue(totalPaidAmt);
        totalPaid.createCell(7).setCellValue("Balance");
        totalPaid.getCell(7).setCellStyle(style);
        totalPaid.createCell(8).setCellValue(balance);
        
        
        Row consignmentHeading = sheet.createRow(sheet.getLastRowNum()+4);
        consignmentHeading.createCell(0).setCellValue("Consignments");
        consignmentHeading.getCell(0).setCellStyle(style);
        
     // create Consignments header row
        Row consignmentHeader = sheet.createRow(sheet.getLastRowNum()+2);
         
        consignmentHeader.createCell(0).setCellValue("Booking Date");
        consignmentHeader.getCell(0).setCellStyle(style);
        
        consignmentHeader.createCell(1).setCellValue("Consignment ID");
        consignmentHeader.getCell(1).setCellStyle(style);
        
        consignmentHeader.createCell(2).setCellValue("Consignor");
        consignmentHeader.getCell(2).setCellStyle(style);
         
        consignmentHeader.createCell(3).setCellValue("Consignee");
        consignmentHeader.getCell(3).setCellStyle(style);
        
        consignmentHeader.createCell(4).setCellValue("From");
        consignmentHeader.getCell(4).setCellStyle(style);
        
        consignmentHeader.createCell(5).setCellValue("To");
        consignmentHeader.getCell(5).setCellStyle(style);
        
        consignmentHeader.createCell(6).setCellValue("Pay Type");
        consignmentHeader.getCell(6).setCellStyle(style);
         
        consignmentHeader.createCell(7).setCellValue("Total Amount");
        consignmentHeader.getCell(7).setCellStyle(style);
        
        
        rowCount = sheet.getLastRowNum()+1;
        
        for (ConsignmentInfoResponse consignment : consignments) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(consignment.getEntry_Date());
            aRow.getCell(0).setCellStyle(dateColStyle);
            aRow.createCell(1).setCellValue(consignment.getDisplayIndex());
            aRow.createCell(2).setCellValue(consignment.getConsignor());
            aRow.createCell(3).setCellValue(consignment.getConsingee());
            aRow.createCell(4).setCellValue(consignment.getFrom());
            aRow.createCell(5).setCellValue(consignment.getTo());
            aRow.createCell(6).setCellValue(consignment.getPayment_Type());
            aRow.createCell(7).setCellValue(consignment.getTotal());
        }
        
       
    }

	private Double getTotalPaidAmount(List<PaymentInfo> paymentInfos){
			
		return paymentInfos.stream().mapToDouble(payment -> payment.getAmount()).sum();
	}
	
	private Double getTotalDueAmount(List<ConsignmentInfoResponse> consignments) {

		return consignments.stream().mapToDouble(consignmentInfoResponse -> consignmentInfoResponse.getTotal()).sum();
	}
	

}
