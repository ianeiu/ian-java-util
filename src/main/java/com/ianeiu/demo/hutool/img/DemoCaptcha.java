package com.ianeiu.demo.hutool.img;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;
import org.junit.Test;

/**
 * 验证码
 * 1. 加线干扰
 * 2. 加圈干扰
 * 3. 穿线干扰
 * 4. 自定义验证码
 * 5. 运算验证码
 */
public class DemoCaptcha {
    @Test
    public void LineTest() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/line.png");
        //lineCaptcha.write(response.getOutputStream());
        //Servlet的OutputStream记得自行关闭哦！
        //输出code
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("d:/line2.png");
        //新的验证码
        Console.log(lineCaptcha.getCode());
    }

    @Test
    public void CircleTest() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/circle.png");
    }

    @Test
    public void ShearTest() {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        captcha.write("d:/shear.png");
    }

    @Test
    public void RandomGeneratorTest() {
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        captcha.setGenerator(randomGenerator);
        // 重新生成code
        captcha.createCode();
        captcha.write("d:/randomGenerator.png");
    }

    @Test
    public void MathGeneratorTest() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 2);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();
        captcha.write("d:/mathGenerator.png");
    }


}
