package com.sunlight.common.utils;

import com.sunlight.common.annotation.ExcelHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ExcelUtils {

    private static <T> HSSFSheet createSheet(HSSFWorkbook workbook, List<T> data, Class<T> clz, String sheetName, Integer index) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = clz.getDeclaredFields();
        List<String> variables = new LinkedList<>();
        // 创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(index, sheetName);
        // 创建表头
        Row rowHeader = sheet.createRow(0);

        // 表头处理
        int headerIndex = 0;
        for (int h = 0; h < fields.length; h++) {
            Field field = fields[h];
            if (field.isAnnotationPresent(ExcelHeader.class)) {
                // 表头
                ExcelHeader annotation = field.getAnnotation(ExcelHeader.class);
//                headers.add(annotation.value());
                rowHeader.createCell(headerIndex).setCellValue(annotation.value());
                headerIndex ++;
                // 字段
                variables.add(field.getName());
            }
        }

        // 数据处理
        for (int i = 0; i < data.size(); i++) {

            //创建工作表的行(表头占用1行, 这里从第二行开始)
            HSSFRow row = sheet.createRow(i + 1);
            // 获取一行数据
            T t = data.get(i);
            Class<?> aClass = t.getClass();
            // 填充列数据
            for (int j = 0; j < variables.size(); j++) {
                Field declaredField = aClass.getDeclaredField(variables.get(j));
                declaredField.setAccessible(true);
                String key = declaredField.getName();
                Object value = declaredField.get(t);
                row.createCell(j).setCellValue(value == null ? "" : value.toString());
            }
        }
        log.info("Excel文件创建成功");
        return sheet;
    }
    /**
     * @param datas 需要导出的数据
     * @param clz  数据对应的实体类
     * @param <T>  泛型
     * @return Excel文件
     */
    public static  <T> HSSFWorkbook exportExcel(Map<String, List<T>> datas, Class<T> clz) throws NoSuchFieldException, IllegalAccessException {
        HSSFWorkbook workbook = new HSSFWorkbook();//这里也可以设置sheet的Name

        Set<String> keys = datas.keySet();
        int index = 0;
        for(String key: keys) {
            List<T> data = datas.get(key);
            createSheet(workbook, data, clz, key, index);
            index++;
        }
        // 创建工作薄对象
        return workbook;
    }

    public static <T> File exportExcelFile(Map<String, List<T>> datas, Class<T> clz, String fileName, String path) {
        try {
            HSSFWorkbook workbook = exportExcel(datas, clz);

            File file = new File(path + File.separator + fileName);
            log.info("export excelFile, path: " + file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook.write(file);
            return file;
        } catch (Exception e) {
            log.error("导出excel 异常：", e);
            e.printStackTrace();
            return null;
        }
    }
}
