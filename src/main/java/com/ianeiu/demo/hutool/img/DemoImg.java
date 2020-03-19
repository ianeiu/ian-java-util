package com.ianeiu.demo.hutool.img;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import java.awt.*;

//针对awt中图片处理进行封装，这些封装包括：缩放、裁剪、转为黑白、加水印等操作。
public class DemoImg {

    @Test
    public void cutTest() {
        // 切割为圆形
        Img.from(FileUtil.file("d:/test.png"))
                .cut(0, 0, 200)
                .write(FileUtil.file("d:/test2.png"));

        //ImgUtil.cut(Img.from(FileUtil.file("d:/test.png")).getImg(),0,0,200);
    }

    @Test
    public void quaTest() {
        //图片压缩只支持Jpg文件。
        Img.from(FileUtil.file("d:/test.png"))
                .setQuality(0.5)//压缩比率
                .write(FileUtil.file("d:/test3.jpg"));
    }

    @Test
    public void grayTest() {
        //置灰
        Img.from(FileUtil.file("d:/test.png"))
                .gray()
                .write(FileUtil.file("d:/test4.png"));
    }

    @Test
    public void flipTest() {
        //水平翻转
        Img.from(FileUtil.file("d:/test.png"))
                .flip()
                .write(FileUtil.file("d:/test5.png"));
    }

    @Test
    public void scaleTest() {
        //缩放
        Img.from(FileUtil.file("d:/test.png"))
                .scale(1.5f)
                .write(FileUtil.file("d:/test6.png"));
    }

    @Test
    public void pressTextTest() {
        //水印
        Img.from(FileUtil.file("d:/test.png"))
                .pressText("ianeiu", Color.WHITE, new Font("黑体", Font.BOLD, 20), 1, 1, 0.5f)
                .write(FileUtil.file("d:/test7.png"));
    }

    @Test
    public void moreTest() {
        //水印
        Img.from(FileUtil.file("d:/test.png"))
                //.round(0.5) //边角
                //.binary() //反色
                //.rotate(20) //旋转 20度
                .write(FileUtil.file("d:/test8.png"));

    }
}
