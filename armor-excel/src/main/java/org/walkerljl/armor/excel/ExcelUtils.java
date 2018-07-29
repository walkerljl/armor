package org.walkerljl.armor.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.walkerljl.toolkit.lang.StringUtils;
import org.walkerljl.toolkit.lang.io.StreamUtils;
import org.walkerljl.toolkit.standard.exception.AppException;

/**
 *
 * ExcelUtils
 *
 * @author xingxun
 */
public class ExcelUtils {

    /**
     * 读取Excel数据
     * @param ins
     * @return
     */
    public static List<List<Object>> read(InputStream ins) {
        return ExcelUtils.read(ins, 0, 0, Integer.MAX_VALUE, true);
    }

    /**
     * 读取Excel数据
     * @param ins 数据流
     * @param sheetIndex 工作区索引
     * @param rowIndex 行索引
     * @param rowNumLimit 允许读取的行限制
     * @param isAllowNull 是否允许列为空
     * @return
     */
    public static List<List<Object>> read(InputStream ins, int sheetIndex, int rowIndex, int rowNumLimit, boolean isAllowNull) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(ins);
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet == null) {
                return null;
            }
            int rowCount = sheet.getLastRowNum();
            if (rowCount > rowNumLimit) {
                throw new AppException(String.format("读取的Excel行数(%s)不能超过%s.", rowCount, rowNumLimit));
            }
            int cellCount = 0;
            if (rowIndex < rowCount) {
                cellCount = sheet.getRow(rowIndex).getLastCellNum();
            }
            List<List<Object>> rowValues = new ArrayList<List<Object>>(rowCount);
            for (int i = rowIndex + 1; i <= rowCount; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                List<Object> cellValues = new ArrayList<Object>(cellCount);
                for (int j = 0; j < cellCount; j++) {
                    XSSFCell cell = row.getCell(j);
                    String value = ExcelCellUtils.getValueAsString(cell);
                    boolean isAdd = (isAllowNull || (!isAllowNull && StringUtils.isNotBlank(value)));
                    if (isAdd) {
                        cellValues.add(value);
                    }
                }
                boolean isAdd = (isAllowNull || (!isAllowNull && cellCount == cellValues.size()));
                if (isAdd) {
                    rowValues.add(cellValues);
                }
            }
            return rowValues;
        } catch (Exception e) {
            throw new AppException("Fail to read excel:" + e.getMessage(), e);
        } finally {
            StreamUtils.close(ins);
        }
    }

    /**
     * 输出Excel
     * @param out 输出流
     * @param title Excel title
     * @param heads Excel 头
     * @param data Excel数据
     */
    public static void write(OutputStream out, String title, String[] heads, List<List<Object>> data) {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(title);
            Row row = sheet.createRow((int) 0);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontName("黑体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 10);
            style.setFont(font);
            Cell cell = null;

            if (heads == null) {
                throw new AppException("Heads is null");
            }
            int headsLen = heads.length;
            for (int i = 0; i < headsLen; i++) {
                cell = row.createCell(i);
                cell.setCellValue(heads[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < data.size(); i++) {
                int rowCursor = i + 1;
                row = sheet.createRow(rowCursor);
                List<Object> rowData = data.get(i);
                for (int j = 0; j < headsLen; j++) {
                    Object cellValue = rowData.get(j);
                    row.createCell(j).setCellValue(StringUtils.valueOf(cellValue));
                }
            }
        } catch (Exception e) {
            throw new AppException("Fail to write excel file : " + e.getMessage(), e);
        } finally {
            try {
                if (wb != null) {
                    wb.write(out);
                }
            } catch (Exception e2) {
                //ignore
            }
            StreamUtils.close(out);
        }
    }

}