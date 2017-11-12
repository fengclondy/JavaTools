package com.jimzhang.itext7.chapter05;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.File;
import java.io.IOException;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description Simple changing page properties example.  简单更改页面属性示例。
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-10 13:27
 */
public class C05E04_ChangePage {
    public static final String SRC = "src/main/resources/static/pdf/ufo.pdf";
    public static final String DEST = "results/chapter05/change_page.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C05E04_ChangePage().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException {

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));


        float margin = 72;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i);
            // change page size
            Rectangle mediaBox = page.getMediaBox();
            Rectangle newMediaBox = new Rectangle(mediaBox.getLeft() - margin, mediaBox.getBottom() - margin,
                    mediaBox.getWidth() + margin * 2, mediaBox.getHeight() + margin * 2);
            page.setMediaBox(newMediaBox);
            // add border 增加边框
            PdfCanvas over = new PdfCanvas(page);
            over.setStrokeColor(Color.GRAY);
            over.rectangle(mediaBox.getLeft(), mediaBox.getBottom(), mediaBox.getWidth(), mediaBox.getHeight());
            over.stroke();
            // change rotation of the even pages
            if (i % 2 == 0) {
                page.setRotation(180); // 旋转180度
            }
        }

        pdfDoc.close();

    }
}
