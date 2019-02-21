package com.ianeiu.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ianeiu.constant.Constants;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * 未加注释的方法是从org.apache.commons.lang.StringUtils提取出
 */
public final class StringUtil {
	public static void main(String[] args) {
		//23
		System.out.println(StringUtils.remove("123", "1"));
		//1
		System.out.println(StringUtils.countMatches("abab", "ba"));
		//null
		System.out.println(StringUtils.trimToNull("  "));
		//ing
		System.out.println(StringUtils.substringAfter("string", "str"));
		//xixi%%fff
		System.out.println(StringUtils.replace( "xixi**fff", "*", "%"));
		//Ccc
		System.out.println(StringUtils.capitalize("ccc"));
		//<span>123sdf</span>
		System.out.println(StringEscapeUtils.unescapeHtml(StringEscapeUtils.escapeHtml("<span>123sdf</span>")));
		//12
		System.out.println(StringUtils.left("123as", 2));
		//x|x|x
		System.out.println(StringUtils.repeat("x", "|", 3));
		//abczzzzzzz
		System.out.println(StringUtils.rightPad("abc", 10,"z"));
		//0.7 0.7 0.7 0.7
		System.out.println(similarDegree("wwwwmmmads", "qwww1wammm"));
		//0.7 0.6 0.5 0.4
		System.out.println(SimilarityRatio("wwwwmmmads", "qwww1wammm"));
		System.out.println();
		System.out.println();
	}

	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }
	
	/**
     * 判读俩个字符串右侧的length个字符串是否一样
     * @param str1
     * @param str2
     * @param length
     * @return
     */
    public  static boolean rigthEquals(String str1,String str2,int length){
        return right(str1,length).equals(right(str2,length));
    }

    /**
     * 判读俩个字符串左侧的length个字符串是否一样
     * @param str1
     * @param str2
     * @param length
     * @return
     */
    public  static boolean leftEquals(String str1,String str2,int length){
        return left(str1,length).equals(left(str2,length));
    }
	
    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 需要处理的字符串
     */
    public  static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }
    
    /**
     * 删除所有的标点符号
     *
     * @param str 处理的字符串
     */
    public  static String replacePunct(String str) {
        if(isEmpty(str)){
            return "";
        }
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }


    /**
     * 截取字符串左侧的Num位截取到末尾
     *
     * @param str1 处理的字符串
     * @param num  开始位置
     * @return 截取后的字符串
     */
    public  static String leftSubstring(String str1, int num) {
        String tt = "";
        if (!isEmpty(str1) && str1.length() >= num) {
            tt = str1.substring(num, str1.length());
        }
        return tt;

    }

    /**
     * 截取字符串右侧第0位到第Num位
     *
     * @param str 处理的字符串
     * @param num 截取的位置
     * @return 截取后的字符串
     */
    public  static String rigthSubstring(String str, int num) {
        if (!isEmpty(str) && str.length() > num) {
            str = str.substring(0, str.length() - num);
        }
        return str;
    }
    
    /**
	 * 隐藏邮件地址前缀
	 * @param email - EMail邮箱地址 例如: ssss@koubei.com 等等...
	 * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
	 */
	public static String getHideEmailPrefix(String email) {
		if (null != email) {
			int index = email.lastIndexOf('@');
			if (index > 0) {
				email = repeat("*", index).concat(email.substring(index));
			}
		}
		return email;
	}
	
	/**
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src - 源字符串 例如: 空格(" "), 星号("*"), "g" 等等...
     * @param num - 重复生成次数
     * @return 返回已生成的重复字符串
     */
    public  static String repeat(String src, int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }
    
    /**
     * 删除所有的标点符号
     *
     * @param str 处理的字符串
     */
    public  static String trimPunct(String str) {
        if(isEmpty(str)){
            return "";
        }
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }

    
    //------------------
    //相似度比较-第一种实现方式
    //------------------
    
	private static String longestCommonSubstring(String strA, String strB) {
		char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1])
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
            }
        }
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1])
                n--;
            else if (matrix[m][n] == matrix[m - 1][n])
                m--;
            else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }

    private static boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A' && charValue <= 'Z') || (charValue >= '0' && charValue <= '9');
    }

    private static String removeSign(String str) {
        StringBuffer sb = new StringBuffer();
        for (char item : str.toCharArray()){
            if (charReg(item)) {
                sb.append(item);
            }
        }
        return sb.toString();
    }

    /**
     * 快速比较俩个字符串的相似度
     *
     * @param strA 较长的字符串
     * @param strB 较短的字符串
     * @return 俩个字符串的相似度
     * <p>summary</p>:较长的字符串放到前面有助于提交效率
     */
    public static double similarDegree(String strA, String strB) {
        String newStrA = removeSign(strA);
        String newStrB = removeSign(strB);
        int temp = Math.max(newStrA.length(), newStrB.length());
        int temp2 = longestCommonSubstring(newStrA, newStrB).length();
        return temp2 * 1.0 / temp;
    }

    //------------------
    //相似度比较-第二种实现方式
    //------------------
    
    private static int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取字符串的相似度
     *
     * @param str
     * @param target
     * @return
     */
    public static double SimilarityRatio(String str, String target) {
        return 1 - (double) compare(str, target) / Math.max(str.length(), target.length());
    }




    /**
     * 获取字符串编码
     *
     * @param str 需要处理的字符串
     */
    public static String simpleEncoding(String str) {
        try{
            byte[] bs = str.getBytes(SysUtil.JVM_ENCODING);
            if(str.equals(new String(bs,CharsetUtil.UTF_8))){
                return CharsetUtil.UTF_8;
            }
            if(str.equals(new String(bs,CharsetUtil.GBK))){
                return CharsetUtil.GBK;
            }
            if(str.equals(new String(bs,"ISO-8859-1"))){
                return "ISO-8859-1";
            }
        }catch(UnsupportedEncodingException e) {
            System.out.println("111111111");
            e.printStackTrace();
        }
        String encode = "GB2312";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (UnsupportedEncodingException exception1) {
            exception1.printStackTrace();
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (UnsupportedEncodingException exception1) {
            exception1.printStackTrace();
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;

            }
        } catch (UnsupportedEncodingException exception1) {
            exception1.printStackTrace();
        }
        return "";
    }
    
    /**
     * <PRE>
     * 半角字符->全角字符转换
     * 只处理空格，!到˜之间的字符，忽略其他
     * </PRE>
     */
    public static String bj2qj(String src) {
        if(StringUtil.isEmpty(src)){
            return "";
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (char t:ca) {
            if (t == Constants.BCConvert.DBC_SPACE) {
                // 如果是半角空格，直接用全角空格替代
                buf.append(Constants.BCConvert.SBC_SPACE);
            } else if ((t >= Constants.BCConvert.DBC_CHAR_START) && (t <= Constants.BCConvert.DBC_CHAR_END)) {
                // 字符是!到~之间的可见字符
                buf.append((char) (t + Constants.BCConvert.CONVERT_STEP));
            } else {
                // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(t);
            }
        }
        return buf.toString();
    }

    /**
     * <PRE>
     * 全角字符->半角字符转换
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     * </PRE>
     */
    public static String qj2bj(String src) {
        if(StringUtil.isEmpty(src)){
            return "";
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= Constants.BCConvert.SBC_CHAR_START && ca[i] <= Constants.BCConvert.SBC_CHAR_END) {
                // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - Constants.BCConvert.CONVERT_STEP));
            } else if (ca[i] == Constants.BCConvert.SBC_SPACE) {
                // 如果是全角空格
                buf.append(Constants.BCConvert.DBC_SPACE);
            } else {
                // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    
}