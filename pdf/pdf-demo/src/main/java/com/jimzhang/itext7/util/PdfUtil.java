package com.jimzhang.itext7.util;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-10 15:29
 */
public class PdfUtil {

//    public static final String SRC = "src/main/resources/static/pdf/fapiao.pdf";
    public static final String SRC = "src/main/resources/static/pdf/123.pdf";


    public static void main(String[] args) throws IOException {
        PdfUtil.readPdf(SRC);
    }

    public static void readPdf(String dest) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument sourcePdfDocument = new PdfDocument(new PdfReader(SRC), new PdfWriter(baos));
        // 读取字段
        PdfAcroForm form = PdfAcroForm.getAcroForm(sourcePdfDocument, true);
        Map<String, PdfFormField> formFields = form.getFormFields();
        System.out.println(formFields.toString());
        PdfObject signature1 = formFields.get("Signature1").getValue();
        System.out.println(signature1);

    }
}
