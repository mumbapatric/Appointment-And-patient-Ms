package project.Appointment.And.Patient.MS.report;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class PdfReportGenerator {

    public byte[] generate(List<Map<String, Object>> data, String title) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add a blue border to each page
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, (IEventHandler) event -> {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            canvas.setLineWidth(2f);
            canvas.setStrokeColor(ColorConstants.BLUE);
            canvas.rectangle(pageSize.getLeft() + 10, pageSize.getBottom() + 10,
                    pageSize.getWidth() - 20, pageSize.getHeight() - 20);
            canvas.stroke();
        });


        // Add logo image
        try {
            String logoPath = "src/main/resources/templates/logo.png";
            ImageData imageData = ImageDataFactory.create(logoPath);
            Image logo = new Image(imageData)
                    .scaleToFit(80, 40)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        } catch (Exception e) {
            // Ignore if logo not found
        }

// Add hospital name
        document.add(new Paragraph("Doctor and Patient Appointment System")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(10)
        );

        // Add a title with decoration
        document.add(new Paragraph(title)
                .setBold()
                .setFontSize(18)
                .setFontColor(ColorConstants.BLUE)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20)
                .setBorderBottom(new SolidBorder(ColorConstants.BLUE, 2))
        );

        if (!data.isEmpty()) {
            Table table = new Table(data.get(0).keySet().size());
            table.setWidth(UnitValue.createPercentValue(100));

            // Table header
            for (String header : data.get(0).keySet()) {
                table.addHeaderCell(new Cell().add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setFontColor(ColorConstants.BLUE)
                        .setTextAlignment(TextAlignment.CENTER)
                );
            }

            // Table rows
            for (Map<String, Object> row : data) {
                for (Object value : row.values()) {
                    table.addCell(new Cell().add(new Paragraph(value != null ? value.toString() : ""))
                            .setTextAlignment(TextAlignment.CENTER)
                    );
                }
            }

            document.add(table);
        } else {
            document.add(new Paragraph("No appointments found for the selected period.")
                    .setFontColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        document.close();
        return out.toByteArray();
    }
}
