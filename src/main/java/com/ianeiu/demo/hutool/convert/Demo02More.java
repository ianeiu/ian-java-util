package com.ianeiu.demo.hutool.convert;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author: wuweimian
 * @date: 2019/5/30 17:23
 * @description:
 * 通过Convert.convert(Class<T>, Object)方法可以将任意类型转换为指定类型，Hutool中预定义了许多类型转换，
 * 例如转换为URI、URL、Calendar等等，这些类型的转换都依托于ConverterRegistry类。
 * 通过这个类和Converter接口，我们可以自定义一些类型转换
 */
public class Demo02More {

    public static void main(String[] args) {
        //半角和全角转换
        //toSBCtoDBC();
        //16进制（Hex）
        //toHex();
        //Unicode和字符串转换
        //toUnicode();
        //编码转换 注意 经过测试，UTF-8编码后用GBK解码再用GBK编码后用UTF-8解码会存在某些中文转换失败的问题
        //toCharset();
        //时间单位转换
        //toTime();
        //金额大小写转换
        toMoney();
        //原始类和包装类转换
        toWrap();
    }

    private static void toWrap() {
        //去包装
        Class<?> wrapClass = Integer.class;
        //结果为：int.class
        Class<?> unWraped = Convert.unWrap(wrapClass);

        //包装
        Class<?> primitiveClass = long.class;
        //结果为：Long.class
        Class<?> wraped = Convert.wrap(primitiveClass);
    }

    private static void toMoney() {
        double a = 67556.32;

        //结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
        String digitUppercase = Convert.digitToChinese(a);
        System.out.println(digitUppercase);
    }

    private static void toTime() {
        long a = 4535345;
        //结果为：75
        long minutes = Convert.convertTime(a, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);
    }

    private static void toCharset() {
        String a = "我不是乱码";
        //转换后result为乱码
        String result = Convert.convertCharset(a, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
        String raw = Convert.convertCharset(result, CharsetUtil.ISO_8859_1, "UTF-8");
        System.out.println(ObjectUtil.equal(raw, a));
    }

    private static void toUnicode() {
        String a = "我是一个小小的可爱的字符串";
        //结果为："\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32"
        String unicode = Convert.strToUnicode(a);
        //结果为："我是一个小小的可爱的字符串"
        String raw = Convert.unicodeToStr(unicode);
    }

    private static void toHex() {
        String a = "我是一个小小的可爱的字符串";

        //结果："e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2"
        String hex = Convert.toHex(a, CharsetUtil.CHARSET_UTF_8);

        //String hex = "e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2";

        //结果为："我是一个小小的可爱的字符串"
        String raw = Convert.hexStrToStr(hex, CharsetUtil.CHARSET_UTF_8);

        //注意：在4.1.11之后hexStrToStr将改名为hexToStr
        String raw2 = Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8);
    }

    private static void toSBCtoDBC() {
        String a = "123456789";
        //结果为："１２３４５６７８９"
        String sbc = Convert.toSBC(a);
        //结果为"123456789"
        String dbc = Convert.toDBC(a);
    }
}
