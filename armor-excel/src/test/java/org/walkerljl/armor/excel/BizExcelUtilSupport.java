package org.walkerljl.armor.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 *
 * @author xingxun
 */
public class BizExcelUtilSupport {

    @Test
    public void test() throws FileNotFoundException {

        InputStream ins = new FileInputStream("/Users/walkerljl/workbench/alipay/document/商家-运营支撑/数据开发/城市智能运营/物料/物料名称VVV4.xlsx");
        List<List<Object>> dataList = ExcelUtils.read(ins, 0, 0, Integer.MAX_VALUE, true);

        //String template = "('%s' ,getdate() ,getdate() ,'junlin.ljl' ,'junlin.ljl' ,'%s' ,'%s' ,'%s' ,'%s')";

        String template = "named_struct('id' ,'%s' ,'gmt_create' ,'${bizdate}' ,'gmt_modified' ,'${bizdate}' ,'creator' ,'junlin.ljl' "
                + ",'modifier' ,'junlin.ljl' ,'biz_line_code' ,'%s' ,'biz_line_name' ,'%s','item_code' ,'%s' ,'item_name' ,'%s')";
        AtomicInteger idCounter = new AtomicInteger(0);
        AtomicLong bizCodeCounter = new AtomicLong(10000000);
        Map<String, String> bizNameAndCodeMap = new HashMap<String, String>(dataList.size());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(template,
                idCounter.incrementAndGet(),
                bizCodeCounter.incrementAndGet(),
                String.valueOf(dataList.get(idCounter.get() - 1).get(1)).trim(),
                String.valueOf(dataList.get(idCounter.get() - 1).get(2)).trim(),
                String.valueOf(dataList.get(idCounter.get() - 1).get(0)).trim()
        ));

        bizNameAndCodeMap.put(
                String.valueOf(dataList.get(idCounter.get() - 1).get(1)).trim(),
                String.valueOf(bizCodeCounter.get())
        );

        for (int index = 1; index < dataList.size(); index++) {
            String bizName = String.valueOf(dataList.get(idCounter.get() - 1).get(1)).trim();
            String bizCode = bizNameAndCodeMap.get(bizName);
            if (bizCode == null) {
                bizCode = String.valueOf(bizCodeCounter.incrementAndGet());
                bizNameAndCodeMap.put(bizName, bizCode);
            }

            sb.append("\n").append(",").append(String.format(template,
                    idCounter.incrementAndGet(),
                    bizCode,
                    bizName,
                    String.valueOf(dataList.get(idCounter.get() - 1).get(2)).trim(),
                    String.valueOf(dataList.get(idCounter.get() - 1).get(0)).trim()
            ));
        }

        System.out.println(sb.toString());
    }

    @Test
    public void test2() {

        Map<String, String[]> map = new HashMap<String, String[]>(5);
        map.put("西部", "四川省、重庆、云南省、贵州省、甘肃省、新疆、宁夏、青海、西藏".split("、"));
        map.put("华南", "广东、福建、广西、海南".split("、"));
        map.put("华东", "江苏、浙江、湖北、安徽、湖南、上海、江西".split("、"));
        map.put("华北", "山东、河南、河北、山西、陕西、北京、天津".split("、"));
        map.put("东北", "辽宁、黑龙江、吉林、内蒙古".split("、"));
        AtomicLong regionCodeCounter = new AtomicLong(10000000);

        String template = "('%s' ,getdate() ,getdate() ,'junlin.ljl' ,'junlin.ljl' ,'%s' ,'%s' ,'%s')";
        StringBuilder sb = new StringBuilder();

        StringBuilder sb2 = new StringBuilder();

        AtomicInteger idCounter = new AtomicInteger(0);
        for (Map.Entry<String, String[]> entry : map.entrySet()) {

            String regionCode = String.valueOf(regionCodeCounter.incrementAndGet());
            String regionName = String.valueOf(entry.getKey()).trim();
            for (String ele : entry.getValue()) {
                if (idCounter.get() != 0) {
                    sb.append("\n").append(",");
                }

                String provinceName = ele.trim();
                sb.append(String.format(template,
                        String.valueOf(idCounter.incrementAndGet()),
                        regionCode,
                        regionName,
                        provinceName
                ));
                sb2.append("'").append(provinceName).append("',");

                if ("北京".equalsIgnoreCase(provinceName)
                        || "上海".equalsIgnoreCase(provinceName)
                        || "天津".equalsIgnoreCase(provinceName)
                        || "重庆".equalsIgnoreCase(provinceName)) {

                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "市"
                    ));

                } else if ("内蒙古".equalsIgnoreCase(provinceName)) {
                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "自治区"
                    ));
                } else if ("广西".equalsIgnoreCase(provinceName)) {
                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "壮族自治区"
                    ));
                } else if ("西藏".equalsIgnoreCase(provinceName)) {
                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "自治区"
                    ));
                } else if ("宁夏".equalsIgnoreCase(provinceName)) {
                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "回族自治区"
                    ));
                } else if ("新疆".equalsIgnoreCase(provinceName)) {
                    sb.append("\n").append(",").append(String.format(template,
                            String.valueOf(idCounter.incrementAndGet()),
                            regionCode,
                            regionName,
                            provinceName + "维吾尔自治区"
                    ));
                } else {
                    if (provinceName.endsWith("省")) {
                        sb.append("\n").append(",").append(String.format(template,
                                String.valueOf(idCounter.incrementAndGet()),
                                regionCode,
                                regionName,
                                provinceName.substring(0, provinceName.indexOf("省"))
                        ));
                    } else if (provinceName.endsWith("市")) {
                        sb.append("\n").append(",").append(String.format(template,
                                String.valueOf(idCounter.incrementAndGet()),
                                regionCode,
                                regionName,
                                provinceName.substring(0, provinceName.indexOf("市"))
                        ));
                    } else {
                        sb.append("\n").append(",").append(String.format(template,
                                String.valueOf(idCounter.incrementAndGet()),
                                regionCode,
                                regionName,
                                provinceName + "省"
                        ));
                    }
                }
            }
        }

        System.out.println(sb.toString());

        System.out.println(sb2.toString());

    }

    @Test
    public void test3() throws FileNotFoundException {

        InputStream ins = new FileInputStream("/Users/walkerljl/Desktop/工作簿1.xlsx");
        List<List<Object>> dataList = ExcelUtils.read(ins, 0, 0, Integer.MAX_VALUE, true);

        String template = "when trim(b.province_name) = '%s' then '%s'\n";
        StringBuilder sb = new StringBuilder();

        for (List<Object> rowData : dataList) {

            String code = String.valueOf(rowData.get(0)).trim();
            String name = String.valueOf(rowData.get(1)).trim();
            sb.append(String.format(template, name, code));

            if ("北京".equalsIgnoreCase(name)
                    || "上海".equalsIgnoreCase(name)
                    || "天津".equalsIgnoreCase(name)
                    || "重庆".equalsIgnoreCase(name)) {

                sb.append(String.format(template, name + "市", code));
            } else if ("内蒙古".equalsIgnoreCase(name)) {
                sb.append(String.format(template, name + "自治区", code));
            } else if ("广西".equalsIgnoreCase(name)) {

                sb.append(String.format(template, name + "壮族自治区", code));

            } else if ("西藏".equalsIgnoreCase(name)) {

                sb.append(String.format(template, name + "自治区", code));

            } else if ("宁夏".equalsIgnoreCase(name)) {

                sb.append(String.format(template, name + "回族自治区", code));

            } else if ("新疆".equalsIgnoreCase(name)) {

                sb.append(String.format(template, name + "维吾尔自治区", code));
            } else {
                if (name.endsWith("省")) {
                    sb.append(String.format(template, name.substring(0, name.indexOf("省")), code));
                } else if (name.endsWith("市")) {
                    sb.append(String.format(template, name.substring(0, name.indexOf("市")), code));
                } else {
                    sb.append(String.format(template, name + "省", code));
                }
            }
        }

        System.out.println(sb.toString());
    }

}