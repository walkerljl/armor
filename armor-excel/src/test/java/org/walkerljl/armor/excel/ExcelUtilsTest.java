package org.walkerljl.armor.excel;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * ExcelUtilsTest
 *
 * @author xingxun
 */
public class ExcelUtilsTest {

    @Test
    public void read() {

        InputStream ins = ExcelUtilsTest.class.getResourceAsStream("/data/test.xlsx");
        List<List<Object>> dataList = ExcelUtils.read(ins, 0, 0, Integer.MAX_VALUE, true);

        assertCellDataList(dataList.get(0), new String[] {"10000001", "张三", "20"});
        assertCellDataList(dataList.get(1), new String[] {"10000002", "李四", "21"});
    }

    private void assertCellDataList(List<Object> actualCellDataList, String[] expectedCellDataList) {

        for (int i = 0; i < expectedCellDataList.length; i++) {
            Assert.assertEquals(expectedCellDataList[i], actualCellDataList.get(i));
        }
    }

    // @Test
    public void write() throws Exception {
        List<List<Object>> rowDataList = new ArrayList<List<Object>>(1);
        List<Object> cellDataList = new ArrayList<Object>(1);
        rowDataList.add(cellDataList);
        cellDataList.add("jarvis");
        OutputStream out = new FileOutputStream("C:\\Users\\俊林\\Desktop\\read.xlsx");
        ExcelUtils.write(out, "test", new String[] {"username"}, rowDataList);
    }
}