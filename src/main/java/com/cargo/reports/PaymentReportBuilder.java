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

public class PaymentReportBuilder extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest arg2,
			HttpServletResponse response) throws Exception {
		List<PaymentInfo> payments = (List<PaymentInfo>) data.get("payments");
		Person person = (Person) data.get("person");
		Double totalDue = (Double) data.get("totaldue");
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
        
        
       
    }

	private Double getTotalPaidAmount(List<PaymentInfo> paymentInfos){
			
		return paymentInfos.stream().mapToDouble(payment -> payment.getAmount()).sum();
	}
	

}
