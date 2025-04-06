package project.Appointment.And.Patient.MS.report;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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

        document.add(new Paragraph(title).setBold().setFontSize(16));

        if (!data.isEmpty()) {
            Table table = new Table(data.get(0).keySet().size());

            for (String header : data.get(0).keySet()) {
                table.addHeaderCell(new Cell().add(new Paragraph(header)));
            }

            for (Map<String, Object> row : data) {
                for (Object value : row.values()) {
                    table.addCell(new Cell().add(new Paragraph(value.toString())));
                }
            }

            document.add(table);
        }

        document.close();
        return out.toByteArray();
    }
}
