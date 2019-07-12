package com.ianeiu.utils.paramvalid;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ParamValidUtil {
    private ParamValid pValid;
    private Class obj;
    private ArrayList<Exception> exceptions;

    public ParamValidUtil(Object object) {
        obj = object.getClass();
        exceptions = new ArrayList<Exception>();
        Field[] fields = obj.getDeclaredFields();
        for (Field field : fields) {
            // 设置field为private时设置可以访问权限,
            field.setAccessible(true);
            // 开始验证
            try {
                verify(field, object);
            } catch (Exception e) {
                //logger
                exceptions.add(e);
            }
            // 重新对field设置权限
            field.setAccessible(false);

        }
    }

    public ArrayList<Exception> getExceptions() {
        return exceptions;
    }

    private void verify(Field field, Object object) throws Exception {
        pValid = field.getAnnotation(ParamValid.class);
        // 检查这个field是否被ParamValid注释
        if (!field.isAnnotationPresent(ParamValid.class)) {
            return;
        }
        // 取出object对象中的field值
        Object value = field.get(object);
        String desc = "".equals(pValid.desc()) ? field.getName() : pValid.desc();

        // ===============================start=======================================
        // ============================必填校验=======================================

        this.validNull(value, desc, pValid, exceptions);
        this.validNullForRef(value, desc, object, pValid, exceptions);

        // ============================必填校验=======================================
        // ================================end========================================

        this.validNumber(value, desc, pValid, exceptions);
        this.validStrLength(value, desc, pValid, exceptions);

    }

    /**
     * 校验是否为空
     *
     * @param value
     * @param desc
     * @param pValid
     * @param exceptions
     */
    private void validNull(Object value, String desc, ParamValid pValid, ArrayList<Exception> exceptions) {
        if (!pValid.notNull()) {
            return;
        }
        if (value == null || "".equals(value)) {
            exceptions.add(new Exception(desc + "不能为空"));
        }
    }

    /**
     * 校验是否为空（关联字段refField值为refValue时）
     *
     * @param value
     * @param desc
     * @param object
     * @param pValid
     * @param exceptions
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void validNullForRef(Object value, String desc, Object object, ParamValid pValid, ArrayList<Exception> exceptions) throws NoSuchFieldException, IllegalAccessException {
        if ("".equals(pValid.refField())) {
            return;
        }
        Field refField = obj.getDeclaredField(pValid.refField());
        refField.setAccessible(true);
        String refValue = (String) refField.get(object);
        refField.setAccessible(false);
        if (pValid.refValue().equals(refValue)) {
            if (value == null || "".equals(value)) {
                exceptions.add(new Exception(desc + "不能为空"));
            }
        }
    }

    /**
     * 校验数字
     *
     * @param value
     * @param desc
     * @param pValid
     * @param exceptions
     */
    private void validNumber(Object value, String desc, ParamValid pValid, ArrayList<Exception> exceptions) throws NumberFormatException {
        if (!pValid.validNum()) {
            return;
        }
        double intValue = (value == null) ? 0 : Double.valueOf(value.toString());
        if (intValue < pValid.minNum() || intValue > pValid.maxNum()) {
            exceptions.add(new Exception(desc + "必须在" + pValid.minNum() + "到" + pValid.maxNum() + "之间"));
        }
    }

    /**
     * 校验字符串长度
     *
     * @param value
     * @param desc
     * @param pValid
     * @param exceptions
     */
    private void validStrLength(Object value, String desc, ParamValid pValid, ArrayList<Exception> exceptions) {
        if (!pValid.validStrLength()) {
            return;
        }
        int length = (value == null) ? 0 : value.toString().length();
        if (length < pValid.minLength() || length > pValid.maxLength()) {
            exceptions.add(new Exception(desc + "字符长度必须在" + pValid.minLength() + "到" + pValid.maxLength() + "之间"));
        }
    }
}