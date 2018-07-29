package org.walkerljl.armor.excel;

import java.text.DecimalFormat;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.walkerljl.toolkit.standard.exception.AppException;

/**
 * ExcelCellUtils
 *
 * @author xingxun
 */
class ExcelCellUtils {

    /**
     * 获取字符串列值
     * @param cell Cell
     * @return
     */
    public static String getValueAsString(XSSFCell cell) {
        String cellValue = "";
        if (null == cell) {
            return cellValue;
        }

        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                Double doubleValue = cell.getNumericCellValue();
                String str = String.valueOf(doubleValue);
                if (str.contains("E")) {
                    Double subStr = Double.parseDouble(str);
                    str = new DecimalFormat("#").format(subStr);
                }
                if (str.contains(".0")) {
                    str = str.replace(".0", "");
                }
                cellValue = str;
                break;
            case XSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                Boolean booleanValue = cell.getBooleanCellValue();
                cellValue = booleanValue.toString();
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_ERROR:
                break;
            default:
                throw new AppException("未知Cell类型");
        }
        return cellValue.trim();
    }

}