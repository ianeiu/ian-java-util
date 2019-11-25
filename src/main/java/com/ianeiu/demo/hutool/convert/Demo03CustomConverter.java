package com.ianeiu.demo.hutool.convert;

import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;

/**
 * @author: wuweimian
 * @date: 2019/11/1 17:16
 * @description:
 * Converter 类型转换接口，通过实现这个接口，重写convert方法，以实现不同类型的对象转换
 * ConverterRegistry 类型转换登记中心。将各种类型Convert对象放入登记中心，通过convert方法查找目标类型对应的转换器，将被转换对象转换之。
 * 在此类中，存放着默认转换器和自定义转换器，默认转换器是Hutool中预定义的一些转换器，自定义转换器存放用户自定的转换器。
 */
public class Demo03CustomConverter {

    public static class CustomConverter implements Converter<String> {
        @Override
        public String convert(Object value, String defaultValue) throws IllegalArgumentException {
            return "Custom: " + value.toString();
        }
    }

    public static void main(String[] args) {
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        //此处做为示例自定义String转换，因为Hutool中已经提供String转换，请尽量不要替换
        //替换可能引发关联转换异常（例如覆盖String转换会影响全局）
        converterRegistry.putCustom(String.class, CustomConverter.class);

        int num = 454553;
        String result = converterRegistry.convert(String.class, num);
        System.out.println("Custom: 454553".equals(result));
    }

}
