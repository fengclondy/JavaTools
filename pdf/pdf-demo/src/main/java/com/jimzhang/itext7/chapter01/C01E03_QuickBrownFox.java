package com.jimzhang.itext7.chapter01;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description Simple image example.
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-09 14:26
 */
public class C01E03_QuickBrownFox {
//    public static final String DOG = "src/main/resources/static/img/dog.bmp";
//    public static final String FOX = "src/main/resources/static/img/fox.bmp";
    public static final String DOG = "src/main/resources/static/img/Go.jpg";
    public static final String FOX = "src/main/resources/static/img/Java.jpg";

    public static final String DEST = "results/chapter01/quick_brown_fox.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C01E03_QuickBrownFox().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Compose Paragraph
        Image fox = new Image(ImageDataFactory.create(FOX));
        Image dog = new Image(ImageDataFactory.create(DOG));
        Paragraph p = new Paragraph("The quick brown ")
                .add(fox)
                .add(" jumps over the lazy ")
                .add(dog);
        // Add Paragraph to document
        document.add(p);

        //Close document
        document.close();
    }
}
