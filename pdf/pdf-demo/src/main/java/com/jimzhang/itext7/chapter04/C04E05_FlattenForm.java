package com.jimzhang.itext7.chapter04;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description Simple filling out form example.   简单填写表单示例。只读
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-10 12:39
 */
public class C04E05_FlattenForm {
    public static final String SRC = "src/main/resources/static/pdf/job_application.pdf";
    public static final String DEST = "results/chapter04/flatten_form.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E05_FlattenForm().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfReader(src), new PdfWriter(dest));


        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("name").setValue("James Bond");
        fields.get("language").setValue("English");
        fields.get("experience1").setValue("Off");
        fields.get("experience2").setValue("Yes");
        fields.get("experience3").setValue("Yes");
        fields.get("shift").setValue("Any");
        fields.get("info").setValue("I was 38 years old when I became an MI6 agent.");
        form.flattenFields();

        pdf.close();

    }
}
