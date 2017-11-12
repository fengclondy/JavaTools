package com.jimzhang.itext7.chapter01;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-09 14:07
 */
public class HelloWorld {
    public static final String DEST = "results/chapter01/hello_world.pdf";
    public static final String HEITI = "src/main/resources/static/font/simhei.ttf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HelloWorld().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // 设置字体
        PdfFont font = PdfFontFactory.createFont(HEITI, PdfEncodings.IDENTITY_H, true);
        PdfFont font1 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        //Add paragraph to the document
        document.add(new Paragraph("Hello World!张晋苗").setFont(font));
        document.add(new Paragraph("这里用的是itext7的版本！").setFont(font1));

        //Close document
        document.close();
    }
}
