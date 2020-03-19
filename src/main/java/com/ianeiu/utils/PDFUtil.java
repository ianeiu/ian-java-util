package com.ianeiu.utils;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * 导出PDF文件转换工具
 */
public class PDFUtil{

    /**
     * 生成 PDF 文件
     * @param out 输出流
     * @param html HTML字符串
     * @throws IOException IO异常
     * @throws DocumentException Document异常
     */
    public static void createPDF(OutputStream out, String html) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("pdf/font/STHUPO.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont("pdf/font/STXINGKA.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(out);
    }

    public static void main(String[] args) throws IOException, DocumentException {
        OutputStream os = new FileOutputStream("D://test.pdf");
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>文字</h1>\n" +
                "\n" +
                "<p style=\"background-color:#FFFF00\">\n" +
                "使用十六进制值设置的颜色\n" +
                "</p>\n" +
                "\n" +
                "<p style=\"color:#FFFF00\">\n" +
                "颜色\n" +
                "</p>\n" +
                "\n" +
                "<form action=\"/demo/demo_form.asp\">\n" +
                "<select name=\"cars\">\n" +
                "<option value=\"volvo\">Volvo</option>\n" +
                "<option value=\"saab\">Saab</option>\n" +
                "<option value=\"fiat\">Fiat</option>\n" +
                "<option value=\"audi\">Audi</option>\n" +
                "</select>\n" +
                "<br/><br/>\n" +
                "<input type=\"submit\"/>\n" +
                "</form>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        createPDF(os,html);
    }

}