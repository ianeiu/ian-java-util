package com.ianeiu.utils.paramvalid;

import org.junit.Test;

public class TestD {

    @Test
    public void test01(){
        TestVO testVO = new TestVO();
        ParamValidUtil pvu = new ParamValidUtil(testVO);
        System.out.println(pvu.getExceptions());
    }

    @Test
    public void test02(){
        TestVO testVO = new TestVO();
        testVO.setStatus("0");
        ParamValidUtil pvu = new ParamValidUtil(testVO);
        System.out.println(pvu.getExceptions());
    }

    @Test
    public void test0201(){
        TestVO testVO = new TestVO();
        testVO.setStatus("1");
        ParamValidUtil pvu = new ParamValidUtil(testVO);
        System.out.println(pvu.getExceptions());
    }

    @Test
    public void test03(){
        TestVO testVO = new TestVO();
        testVO.setStatus("1");
        testVO.setContent("666");
        testVO.setTitle("666");
        ParamValidUtil pvu= new ParamValidUtil(testVO);
        System.out.println(pvu.getExceptions());
    }

    @Test
    public void test04(){
        TestVO testVO = new TestVO();
        testVO.setStatus("1");
        testVO.setContent("666");
        testVO.setTitle("五个字五个字");
        ParamValidUtil pvu= new ParamValidUtil(testVO);
        System.out.println(pvu.getExceptions());
    }

}
