package project.Appointment.And.Patient.MS.report;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ExcelReportGenerator {

    public byte[] generate(List<Map<String, Object>> data, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        int rowNum = 0;

        if (!data.isEmpty()) {
            // Header
            Row headerRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String key : data.get(0).keySet()) {
                headerRow.createCell(colNum++).setCellValue(key);
            }

            // Data rows
            for (Map<String, Object> rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int cellNum = 0;
                for (Object value : rowData.values()) {
                    row.createCell(cellNum++).setCellValue(value.toString());
                }
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
