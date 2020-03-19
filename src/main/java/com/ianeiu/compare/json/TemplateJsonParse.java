package com.ianeiu.compare.json;

public abstract class TemplateJsonParse {

    public void parseObjAndPrint(String json) {

        System.out.println(utilName() +  "执行parseObj开始：");
        long start = System.currentTimeMillis();
        parseObj(json);
        long end = System.currentTimeMillis();
        System.out.println("耗时 " + (end - start) + " 毫秒");
        System.out.println("----------------------------");
    }

    public abstract String utilName();

    public abstract void parseObj(String json);
}
