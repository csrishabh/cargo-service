package com.cargo.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.cargo.service.responce.PaymentSummary;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PaymentSummeryReport extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<PaymentSummary> summaryList = (List<PaymentSummary>) model.get("summaryList");

		PdfPTable table = new PdfPTable(7);

		PdfPCell c1 = new PdfPCell();
		c1.setFixedHeight(20);
		c1.setCellEvent(new PositionEvent(new Phrase("Name", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c1);

		PdfPCell c2 = new PdfPCell();
		c2.setFixedHeight(20);
		c2.setCellEvent(new PositionEvent(new Phrase("Type", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c2);

		PdfPCell c3 = new PdfPCell();
		c3.setFixedHeight(20);
		c3.setCellEvent(new PositionEvent(new Phrase("City", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c3);

		PdfPCell c4 = new PdfPCell();
		c4.setFixedHeight(20);
		c4.setCellEvent(new PositionEvent(new Phrase("Mobile", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c4);

		PdfPCell c5 = new PdfPCell();
		c5.setFixedHeight(20);
		c5.setCellEvent(new PositionEvent(new Phrase("Total Due", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c5);

		PdfPCell c6 = new PdfPCell();
		c6.setFixedHeight(20);
		c6.setCellEvent(new PositionEvent(new Phrase("Total Due", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c6);

		PdfPCell c7 = new PdfPCell();
		c7.setFixedHeight(20);
		c7.setCellEvent(new PositionEvent(new Phrase("Balance", FontFactory.getFont(FontFactory.TIMES_BOLD, 8)), 0.5f,
				0.5f, Element.ALIGN_CENTER));
		table.addCell(c7);

		summaryList.stream().forEach(summary -> {
			PdfPCell cell1 = new PdfPCell();
			cell1.setFixedHeight(20);
			cell1.setCellEvent(
					new PositionEvent(new Phrase(summary.getClientname(), FontFactory.getFont(FontFactory.TIMES, 10)),
							0.5f, 0.5f, Element.ALIGN_CENTER));
			table.addCell(cell1);

			PdfPCell cell2 = new PdfPCell();
			cell2.setFixedHeight(20);
			cell2.setCellEvent(
					new PositionEvent(new Phrase(summary.getClientType(), FontFactory.getFont(FontFactory.TIMES, 8)),
							0.5f, 0.5f, Element.ALIGN_CENTER));
			table.addCell(cell2);

			PdfPCell cell3 = new PdfPCell();
			cell3.setFixedHeight(20);
			cell3.setCellEvent(
					new PositionEvent(new Phrase(summary.getClientCity(), FontFactory.getFont(FontFactory.TIMES, 8)),
							0.5f, 0.5f, Element.ALIGN_CENTER));
			table.addCell(cell3);

			PdfPCell cell4 = new PdfPCell();
			cell4.setFixedHeight(20);
			cell4.setCellEvent(
					new PositionEvent(new Phrase(summary.getClientMobile(), FontFactory.getFont(FontFactory.TIMES, 8)),
							0.5f, 0.5f, Element.ALIGN_CENTER));
			table.addCell(cell4);

			PdfPCell cell5 = new PdfPCell();
			cell5.setFixedHeight(20);
			cell5.setCellEvent(new PositionEvent(
					new Phrase(summary.getTotalDue().toString(), FontFactory.getFont(FontFactory.TIMES, 8)), 0.5f, 0.5f,
					Element.ALIGN_CENTER));
			table.addCell(cell5);

			PdfPCell cell6 = new PdfPCell();
			cell6.setFixedHeight(20);
			cell6.setCellEvent(new PositionEvent(
					new Phrase(summary.getTotalPaid().toString(), FontFactory.getFont(FontFactory.TIMES, 8)), 0.5f,
					0.5f, Element.ALIGN_CENTER));
			table.addCell(cell6);

			PdfPCell cell7 = new PdfPCell();
			cell7.setFixedHeight(20);
			cell7.setCellEvent(new PositionEvent(
					new Phrase(summary.getBalance().toString(), FontFactory.getFont(FontFactory.TIMES, 8)), 0.5f, 0.5f,
					Element.ALIGN_CENTER));
			table.addCell(cell7);

		});
		Paragraph header = new Paragraph();
		header.setAlignment(Element.ALIGN_CENTER);
		header.add("Payment Summary Report");

		Paragraph blankLine = new Paragraph();
		blankLine.setAlignment(Element.ALIGN_CENTER);
		blankLine.add(" ");

		document.add(header);
		document.add(blankLine);
		document.add(table);
	}

	class PositionEvent implements PdfPCellEvent {
		protected Phrase content;
		protected float wPct;
		protected float hPct;
		protected int alignment;

		public PositionEvent(Phrase content, float wPct, float hPct, int alignment) {
			this.content = content;
			this.wPct = wPct;
			this.hPct = hPct;
			this.alignment = alignment;
		}

		public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
			PdfContentByte canvas = canvases[PdfPTable.TEXTCANVAS];
			float x = position.getLeft() + wPct * position.getWidth();
			float y = position.getBottom() + hPct * (position.getHeight() - content.getLeading());
			ColumnText.showTextAligned(canvas, alignment, content, x, y, 0);
		}
	}
}
