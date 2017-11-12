package com.jimzhang.itext7.chapter04;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLineAnnotation;

import java.io.File;
import java.io.IOException;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description Simple line annotation example. 简单的行注释的例子
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-10 10:37
 */
public class C04E01_03_LineAnnotation {
    public static final String DEST = "results/chapter04/line_annotation.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E01_03_LineAnnotation().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfPage page = pdf.addNewPage();

        PdfArray lineEndings = new PdfArray();
        lineEndings.add(new PdfName("Diamond"));
        lineEndings.add(new PdfName("Diamond"));

        //Create line annotation with inside caption
        PdfAnnotation annotation = new PdfLineAnnotation(
                new Rectangle(0, 0),
                new float[]{20, 790, page.getPageSize().getWidth() - 20, 790})
                .setLineEndingStyles((lineEndings))
                .setContentsAsCaption(true)
                .setTitle(new PdfString("iText"))
                .setContents("The example of line annotation")
                .setColor(Color.BLUE);
        page.addAnnotation(annotation);

        //Close document
        pdf.close();

    }
}
