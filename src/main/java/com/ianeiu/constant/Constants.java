package com.ianeiu.constant;

/**
 * 常量类
 * @author wm
 */
public interface Constants {
	
	/**
	 * @Description: 状态码
	 * @author: wm
	 * @date: 2018年10月11日 上午10:57:20
	 * @version: 1.0
	 */
	interface StatusCode {
		
		String CODE_200 = "200";
		
		String CODE_404 = "404";
		
		String CODE_500 = "500";
		
	}
	
	
	/**
	 * 页面请求结果返回代码
	 * 1：成功  0：失败
	 */
	interface HttpRequestResultCode {
		String CODE_SUCCESS = "1";
		String CODE_ERROR = "0";
		
		String CODE_BUSINESS_FAIL = "100100";
		String CODE_VALIDATE_FAIL = "100200";
		String CODE_SYSTEM_ERROR_FAIL = "100500";
	}

	/**
	 * 页面请求结果返回描述
	 * SUCCESS：操作成功  ERROR：操作失败
	 */
	interface HttpRequestResultDesc{
		String SUCCESS = "操作成功";
		String ERROR = "操作失败";
	}
	
	interface BCConvert {
		/**
		 * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
		 */
		char DBC_CHAR_START = 33; // 半角!

		/**
		 * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
		 */
		char DBC_CHAR_END = 126; // 半角~

		/**
		 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
		 */
		char SBC_CHAR_START = 65281; // 全角！

		/**
		 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
		 */
		char SBC_CHAR_END = 65374; // 全角～

		/**
		 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
		 */
		int CONVERT_STEP = 65248; // 全角半角转换间隔

		/**
		 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
		 */
		char SBC_SPACE = 12288; // 全角空格 12288

		/**
		 * 半角空格的值，在ASCII中为32(Decimal)
		 */
		char DBC_SPACE = ' '; // 半角空格
	}
	
	/**
	 * @Description: 文件类型
	 * @author: wm
	 * @date: 2018年10月11日 上午11:00:27
	 * @version: 1.0
	 */
	interface FileType{
		
		String TXT = "txt";
		
		String IMG_JPG = "jpg";
		
		String IMG_PNG = "png";
		
		String EXCEL_XLS = "xls";
		
		String EXCEL_XLSX = "xlsx";
		
	}
	
	/**
	 * @Description: 符号字符串
	 * @author: wm
	 * @date: 2018年10月11日 上午11:03:28
	 * @version: 1.0
	 */
	interface SymbolString {
		String F = ";";
		String X = "*";
		String XZ = "/";
		String XY = "\\";
		String XHX = "_";
	}
}
