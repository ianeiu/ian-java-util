package com.ianeiu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Description: Excel读取工具类（封装poi）
 * @author wm
 * @date 2018/12/27 17:20
 * @version: 1.0
 */
public class ExcelUtil {

	/**
	 * 获取文件的数据 - xls
	 * @param is
	 * @return List<Map<String, String>>
	 * @throws Exception 
	 * @throws IOException
	 */
	public static List<Map<String, String>> getDataFromExecl2003(InputStream is) throws Exception{
		HSSFWorkbook wb;
		try {
			// 把一张xls的数据表读到wb里
			wb = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new Exception("获取文件的数据(把一张xls的数据表读到wb里)出现IOException!" + e);
		}
		// 读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
		HSSFSheet sheet = wb.getSheetAt(0);
		// 表数据 转换成的 对象
		List<Map<String, String>> targetList = new ArrayList<>();
		// 需要读取的列数
		int col = 0;

		List<String> paramList = new ArrayList<>();
		//从第一行开始读
		HSSFRow row = sheet.getRow(0);
		if (row != null) {
			col = row.getPhysicalNumberOfCells();
			// 遍历第1行的所有列
			for (short j = 0; j < col; j++) {
				HSSFCell cell = row.getCell(j);
				if (cell != null) {
					// 读取cell内容转换为字符串value
					String value = cell.getStringCellValue().trim();
					paramList.add(value);
				}
			}
		}

		// 遍历:i等于1从第二行开始读
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			// 取第i+1行数据
			row = sheet.getRow(i);
			if (row != null) {
				Map<String, String> map = new HashMap<>(1000);
				// 遍历第i+1行的列
				for (short j = 0; j < col; j++) {
					HSSFCell cell = row.getCell(j);
					if (cell != null) {
						// 读取cell内容转换为字符串value
						String value = cell.getStringCellValue().trim();
						map.put(paramList.get(j), value);
					}
				}
				targetList.add(map);
			}
		}

		return targetList;
	}

	/**
	 * 获取文件的数据 - xlsx
	 * @param is
	 * @return List<Map<String, String>>
	 * @throws Exception 
	 * @throws IOException
	 */
	public static List<Map<String, String>> getDataFromExecl2007(InputStream is) throws Exception{
		XSSFWorkbook wb;
		try {
			// 把一张xlsx的数据表读到wb里
			wb = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new Exception("获取文件的数据(把一张xlsx的数据表读到wb里)出现IOException!" + e);
		}
		// 读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
		XSSFSheet sheet = wb.getSheetAt(0);
		// 表数据 转换成的 对象
		List<Map<String, String>> targetList = new ArrayList<>();
		// 需要读取的列数
		int col = 0;
		
		List<String> paramList = new ArrayList<>();
		//从第一行开始读
		XSSFRow row = sheet.getRow(0);
		if (row != null) {
			col = row.getPhysicalNumberOfCells();
			// 遍历第1行的所有列
			for (short j = 0; j < col; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell != null) {
					// 读取cell内容转换为字符串value
					String value = cell.getStringCellValue().trim();
					paramList.add(value);
				}
			}
		}

		// 遍历: i等于1从第二行开始读
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			// 取第i+1行数据
			row = sheet.getRow(i);
			if (row != null) {
				Map<String, String> map = new HashMap<>(1000);
				// 遍历第i+1行的列
				for (short j = 0; j < col; j++) {
					XSSFCell cell = row.getCell(j);
					if (cell != null) {
						// 读取cell内容转换为字符串value
						String value = cell.getStringCellValue().trim();
						map.put(paramList.get(j), value);
					}
				}
				targetList.add(map);
			}
		}

		return targetList;
	}
	
	
	/**
     * 
     * excel数据 读取
     * @param customClass  实体类
     * @param is  excel 
     * @param indexSheet    sheet 下标
     * @param startingLine   数据起始行
     * @param attr  实体类对应字段名称数组，
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     */
    public static <T> List<T> excel2003ImportBoty(Class<T> customClass, InputStream is,
            Integer indexSheet, Integer startingLine, String[] attr) throws Exception {
    	indexSheet--;
    	startingLine--;
    	
    	HSSFWorkbook workbook;
		try {
			// 把一张xls的数据表读到wb里
			workbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new Exception("获取文件的数据(把一张xls的数据表读到wb里)出现IOException!"+e);
		}
    	
        List<T> result = new ArrayList<T>();
        // 获取sheet
        HSSFSheet sheetAt = workbook.getSheetAt(indexSheet);
        // 获取总行数
        int lastRowNum = sheetAt.getLastRowNum();
        HSSFRow createRow = null;
        Field commandField;
        // 开始循环读取数据 ,从数据其实行开始
        for (int i = startingLine; i < lastRowNum + 1; i++) {
            createRow = sheetAt.getRow(i);
			// 创建实例化对象，创建新的存储数据的对象
            T ob = customClass.newInstance();
            // 遍历所有属性值
            for (int j = 0; j < attr.length; j++) {
                commandField = customClass.getDeclaredField(attr[j]);
                // 开启属性访问权限
                commandField.setAccessible(true);
                // 获取数据
                String data = createRow.getCell(j).toString();
                // 判断属性的类型， 将读入的数据（在con中）封装到对象（ob）的属性（fi[i]）中
                if (commandField.getType().toString().equals("class java.lang.String")) {
                    commandField.set(ob, data);
                } else if (commandField.getType().toString().equals("class java.math.BigDecimal")) {
                    commandField.set(ob, new BigDecimal(data));
                } else if (commandField.getType().toString().equals("class java.math.BigInteger")) {
                    commandField.set(ob, new BigInteger(data));
                } else if (commandField.getType().toString().equals("class java.lang.Integer")) {
                    commandField.set(ob, Integer.valueOf(data));
                }

            }
            result.add(ob);
        }
        return result;

    }
    
    /**
     * 
     * excel数据 读取
     *
     * @author 博客园-唯所欲为 update by wm
     * 
     * @param customClass  实体类
     * @param is  excel 
     * @param indexSheet    sheet 下标
     * @param startingLine   数据起始行
     * @param attr  实体类对应字段名称数组，
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     */
    public static <T> List<T> excel2007ImportBoty(Class<T> customClass, InputStream is,
            Integer indexSheet, Integer startingLine, String[] attr) throws Exception {
    	indexSheet--;
    	startingLine--;
    	
    	XSSFWorkbook workbook;
		try {
			// 把一张xls的数据表读到wb里
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new Exception("获取文件的数据(把一张xls的数据表读到wb里)出现IOException!"+e);
		}
    	
        List<T> result = new ArrayList<T>();
        // 获取sheet
        XSSFSheet sheetAt = workbook.getSheetAt(indexSheet);
        // 获取总行数
        int lastRowNum = sheetAt.getLastRowNum();
        XSSFRow createRow = null;
        Field commandField;
        // 开始循环读取数据 ,从数据其实行开始
        for (int i = startingLine; i < lastRowNum + 1; i++) {
            createRow = sheetAt.getRow(i);
			// 创建实例化对象，创建新的存储数据的对象
            T ob = customClass.newInstance();
            // 遍历所有属性值
            for (int j = 0; j < attr.length; j++) {
                commandField = customClass.getDeclaredField(attr[j]);
                // 开启属性访问权限
                commandField.setAccessible(true);
                // 获取数据
                String data = createRow.getCell(j).toString();
                // 判断属性的类型，将读入的数据（在con中）封装到对象（ob）的属性（fi[i]）中
                if (commandField.getType().toString().equals("class java.lang.String")) {
                    commandField.set(ob, data);
                } else if (commandField.getType().toString().equals("class java.math.BigDecimal")) {
                    commandField.set(ob, new BigDecimal(data));
                } else if (commandField.getType().toString().equals("class java.math.BigInteger")) {
                    commandField.set(ob, new BigInteger(data));
                } else if (commandField.getType().toString().equals("class java.lang.Integer")) {
                    commandField.set(ob, Integer.valueOf(data));
                }

            }
            result.add(ob);
        }
        return result;

    }
	
}
