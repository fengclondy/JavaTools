package com.jimzhang.itext5;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-10 17:07
 */
public class Pdf5Util {
    /**
     * @Title: main
     * @Description: TODO 测试 PDF 入口
     * @param args
     * @return: void
     */

    public static final String FILE_DIR = "target/itext5/";
    public static Document document;

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
//         createPDF();// 生成一个 PDF 文件
//         decoratePDF();// 设置 PDF 的页面大小 和 背景颜色
//         createPDFAddPassWord();// 设置 PDF 的密码
        // createPDFAddNewPages();// 添加页
        // addWaterMark();//为 PDF 文件添加图片水印，文字水印，背景图ce();
        //addContent();//插入Chunk, Phrase, Paragraph, List
        // addExtraContent();//插入Anchor, Image, Chapter, Section
        // draw();//画图
        // setAlignment();//设置段落
        // deletePage();//删除 page
        // insertPage();// 插入 page
		/*
		 * splitPDF();//分割 page mergePDF();// 合并 PDF 文件
		 */
        // sortpage();// 排序page
//        addOutline();// 目录
        // setHeaderFooter();// 页眉，页脚
        // addColumnText();// 文字左右文字
        // setSlideshow();// 文档视图
         zipPDF();// 压缩PDF到Zip
        // setAnnotation();// 注释
    }

    /**
     * @throws FileNotFoundException
     * @throws DocumentException
     * @Title: createPDF
     * @Description: TODO 创建一个 PDF 文件，并添加文本
     * @return: void
     */
    public static void createPDF() throws FileNotFoundException, DocumentException {
        // 第一步：实例化 document
        document = new Document();
        // 第二步：生成文件
        PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createPDF.pdf"));
        // 第三步：打开 document
        document.open();
        // 第四步：添加文本
        document.add(new Paragraph("Hello World"));
        // 第五步：关闭 document
        document.close();
    }

    /**
     * @throws FileNotFoundException
     * @throws DocumentException
     * @Title: decoratePDF
     * @Description: TODO 创建一个 PDF 文件，修改文件的属性
     * @return: void
     */
    public static void decoratePDF() throws FileNotFoundException, DocumentException {
        // 页面大小
        Rectangle rect = new Rectangle(PageSize.B6.rotate());
        // 页面背景色
        rect.setBackgroundColor(BaseColor.ORANGE);
        document = new Document(rect);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createdecoratePDF.pdf"));
        // PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.VERSION_1_7);

        // 文档属性
        document.addTitle("Title@sample");
        document.addAuthor("Author@rensanning");
        document.addSubject("Subject@iText sample");
        document.addKeywords("Keywords@iText");
        document.addCreator("Creator@iText");

        // 页边空白
        document.setMargins(10, 20, 80, 10);
        // 打开
        document.open();
        document.add(new Paragraph("Hello World"));
        // 关闭
        document.close();
    }

    /**
     * @throws FileNotFoundException
     * @throws DocumentException
     * @Title: createPDFAddPassWord
     * @Description: TODO 创建一个 PDF 文件，并为该文件设置密码
     * @return: void
     */
    public static void createPDFAddPassWord() throws FileNotFoundException, DocumentException {
        // 页面大小
        Rectangle rect = new Rectangle(PageSize.B5.rotate());
        // 页面背景色
        rect.setBackgroundColor(BaseColor.GREEN);
        document = new Document(rect);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createPDFAddPassWord.pdf"));

        // 设置密码为："World"
        writer.setEncryption("Hello".getBytes(), "World".getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_128);
        // open
        document.open();
        document.add(new Paragraph("Hello World"));
        // close
        document.close();
    }

    /**
     * @throws FileNotFoundException
     * @throws DocumentException
     * @Title: createPDFAddNewPages
     * @Description: TODO 创建一个 PDF 文件，并添加新的页
     * @return: void
     */
    public static void createPDFAddNewPages() throws FileNotFoundException, DocumentException {
        document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createPDFAddNewPages.pdf"));
        // open
        document.open();

        document.add(new Paragraph("First page"));
        // 添加版本号..
        document.add(new Paragraph(Document.getProduct()));
        // 添加一页
        document.newPage();
        document.add(new Paragraph("two page"));
        writer.setPageEmpty(true);
        // 添加一页
        document.newPage();
        document.add(new Paragraph("three page"));

        document.close();
    }

    /**
     * @throws IOException
     * @throws DocumentException
     * @throws DocumentException
     * @Title: addWaterMark
     * @Description: TODO 为 PDF 文件添加水印，背景图
     * @return: void
     */
    public static void addWaterMark() throws IOException, DocumentException {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "addWaterMark.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("First page"));

        document.newPage();
        document.add(new Paragraph("New page"));

        document.newPage();
        document.add(new Paragraph("Third page"));

        document.close();

        // 图片水印
        PdfReader reader = new PdfReader(FILE_DIR + "addWaterMark.pdf");
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "addWaterMark2.pdf"));

        Image img = Image.getInstance("resource/watermark.jpg");
        img.setAbsolutePosition(200, 400);
        PdfContentByte under = stamp.getUnderContent(1);
        under.addImage(img);

        // 文字水印
        PdfContentByte over = stamp.getOverContent(2);

        // 加载字库来完成对字体的创建
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

        over.beginText();

        // 设置颜色 默认为蓝色
        over.setColorFill(BaseColor.RED);
        // 设置字体字号
        over.setFontAndSize(bf, 50);
        // 设置起始位置
        over.setTextMatrix(30, 30);
        // 开始写入水印 左-下-倾斜度
        over.showTextAligned(Element.ALIGN_LEFT, "KaiYuTechnology", 245, 400, 30);
        over.endText();

        // 背景图
        Image img2 = Image.getInstance("resource/test.jpg");
        img2.setAbsolutePosition(0, 0);
        PdfContentByte under2 = stamp.getUnderContent(3);
        under2.addImage(img2);

        stamp.close();
        reader.close();

    }

    /**
     * @throws DocumentException
     * @throws FileNotFoundException
     * @Title: addContent
     * @Description: TODO 插入Chunk, Phrase, Paragraph, List
     * @return: void
     */
    public static void addContent() throws DocumentException, FileNotFoundException {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "addContent.pdf");
        document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        // Chunk对象: a String, a Font, and some attributes
        document.add(new Chunk("China"));
        document.add(new Chunk(" "));
        Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id = new Chunk("chinese", font);
        id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id.setTextRise(7);
        document.add(id);
        document.add(Chunk.NEWLINE);

        document.add(new Chunk("Japan"));
        document.add(new Chunk(" "));
        Font font2 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id2 = new Chunk("japanese", font2);
        id2.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id2.setTextRise(3);

        id2.setUnderline(0.2f, -2f);
        document.add(id2);
        document.add(Chunk.NEWLINE);

        // Phrase对象: a List of Chunks with leading
        document.newPage();
        document.add(new Phrase("Phrase page"));

        Phrase director = new Phrase();
        Chunk name = new Chunk("China");
        name.setUnderline(0.2f, -2f);
        director.add(name);
        director.add(new Chunk(","));
        director.add(new Chunk(" "));
        director.add(new Chunk("chinese"));
        director.setLeading(24);
        document.add(director);

        Phrase director2 = new Phrase();
        Chunk name2 = new Chunk("Japan");
        name2.setUnderline(0.2f, -2f);
        director2.add(name2);
        director2.add(new Chunk(","));
        director2.add(new Chunk(" "));
        director2.add(new Chunk("japanese"));
        director2.setLeading(24);
        document.add(director2);

        // Paragraph对象: a Phrase with extra properties and a newline
        document.newPage();
        document.add(new Paragraph("Paragraph page"));

        Paragraph info = new Paragraph();
        info.add(new Chunk("China "));
        info.add(new Chunk("chinese"));
        info.add(Chunk.NEWLINE);
        info.add(new Phrase("Japan "));
        info.add(new Phrase("japanese"));
        document.add(info);

        // List对象: a sequence of Paragraphs called ListItem
        document.newPage();
        List list = new List(List.ORDERED);
        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem(String.format("%s: %d movies", "country" + (i + 1), (i + 1) * 100), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE));
            List movielist = new List(List.ORDERED, List.ALPHABETICAL);
            movielist.setLowercase(List.LOWERCASE);
            for (int j = 0; j < 5; j++) {
                ListItem movieitem = new ListItem("Title" + (j + 1));
                List directorlist = new List(List.UNORDERED);
                for (int k = 0; k < 3; k++) {
                    directorlist.add(String.format("%s, %s", "Name1" + (k + 1), "Name2" + (k + 1)));
                }
                movieitem.add(directorlist);
                movielist.add(movieitem);
            }
            item.add(movielist);
            list.add(item);
        }
        document.add(list);
        document.close();

    }

    /**
     * @throws DocumentException
     * @throws MalformedURLException
     * @throws IOException
     * @Title: addExtraContent
     * @Description: TODO 插入Anchor, Image, Chapter, Section
     * @return: void
     */
    public static void addExtraContent() throws DocumentException, MalformedURLException, IOException {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "addExtraContent.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();

        // Anchor对象: internal and external links
        Paragraph country = new Paragraph();
        Anchor dest = new Anchor("目的地", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        dest.setName("CN");
        dest.setReference("https://www.baidu.com/");// external
        country.add(dest);
        country.add(String.format(": %d sites", 10000));
        document.add(country);

        document.newPage();
        Anchor toUS = new Anchor("跳转->Go to first page.", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        toUS.setReference("#CN");// internal
        document.add(toUS);

        // Image对象
        document.newPage();
        Image img = Image.getInstance("resource/test.jpg");
        img.setAlignment(Image.LEFT | Image.TEXTWRAP);
        img.setBorder(Image.BOX);
        img.setBorderWidth(10);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1000, 72);// 大小
        img.setRotationDegrees(-30);// 旋转
        document.add(img);

        // Chapter, Section对象（目录）
        document.newPage();
        Paragraph title = new Paragraph("Title");
        Chapter chapter = new Chapter(title, 1);// 标题和序号

        title = new Paragraph("Section A");
        Section section = chapter.addSection(title);
        section.setBookmarkTitle("bmk");
        section.setIndentation(30);
        section.setBookmarkOpen(false);
        section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

        Section subsection = section.addSection(new Paragraph("Sub Section A"));
        subsection.setIndentationLeft(20);
        subsection.setNumberDepth(1);

        document.add(chapter);

        document.close();
    }

    /**
     * @throws Exception
     * @Title: draw
     * @Description: TODO 画图
     * @return: void
     */
    public static void draw() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "draw.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();

        // 左右箭头
        document.add(new VerticalPositionMark() {

            @Override
            public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
                canvas.beginText();
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont(BaseFont.ZAPFDINGBATS, "", BaseFont.EMBEDDED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                canvas.setFontAndSize(bf, 12);

                // LEFT
                canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), llx - 10, y, 0);
                // RIGHT
                canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), urx + 10, y + 8, 180);

                canvas.endText();
            }
        });

        // 直线
        Paragraph p1 = new Paragraph("LEFT");
        p1.add(new Chunk(new LineSeparator()));
        p1.add("R");
        document.add(p1);
        // 点线
        Paragraph p2 = new Paragraph("LEFT");
        p2.add(new Chunk(new DottedLineSeparator()));
        p2.add("R");
        document.add(p2);
        // 下滑线
        LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
        Paragraph p3 = new Paragraph("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        p3.add(UNDERLINE);
        document.add(p3);

        document.close();
    }

    /**
     * @throws Exception
     * @Title: setAlignment
     * @Description: TODO 设置段落
     * @return: void
     */
    public static void setAlignment() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "setAlignment.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();

        Paragraph p = new Paragraph(
                "In the previous example, you added a header and footer with the showTextAligned() method. This example demonstrates that it’s sometimes more interesting to use PdfPTable and writeSelectedRows(). You can define a bottom border for each cell so that the header is underlined. This is the most elegant way to add headers and footers, because the table mechanism allows you to position and align lines, images, and text.");

        // 默认
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(p);

        // 慢慢的向右移动
        document.newPage();
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        p.setIndentationLeft(1 * 15f);
        p.setIndentationRight((5 - 1) * 15f);
        document.add(p);

        // 居右
        document.newPage();
        p.setAlignment(Element.ALIGN_RIGHT);
        p.setSpacingAfter(15f);
        document.add(p);

        // 居左
        document.newPage();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setSpacingBefore(15f);
        document.add(p);

        // 居中
        document.newPage();
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(15f);
        p.setSpacingBefore(15f);
        document.add(p);

        document.close();
    }

    /**
     * @throws Exception
     * @Title: deletePage
     * @Description: TODO 删除 page
     * @return: void
     */
    public static void deletePage() throws Exception {

        FileOutputStream out = new FileOutputStream(FILE_DIR + "deletePage.pdf");

        document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("First page"));
        document.add(new Paragraph(Document.getVersion()));

        document.newPage();
        writer.setPageEmpty(false);

        document.newPage();
        document.add(new Paragraph("New page"));

        document.close();

        PdfReader reader = new PdfReader(FILE_DIR + "deletePage.pdf");
        reader.selectPages("1,3");
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "deletePage2.pdf"));
        stamp.close();
        reader.close();
    }

    /**
     * @throws Exception
     * @Title: insertPage
     * @Description: TODO 插入 page
     * @return: void
     */
    public static void insertPage() throws Exception {

        FileOutputStream out = new FileOutputStream(FILE_DIR + "insertPage.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("1 page"));

        document.newPage();
        document.add(new Paragraph("2 page"));

        document.newPage();
        document.add(new Paragraph("3 page"));

        document.close();

        PdfReader reader = new PdfReader(FILE_DIR + "insertPage.pdf");
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "insertPage2.pdf"));

        stamp.insertPage(2, reader.getPageSize(1));

        ColumnText ct = new ColumnText(null);
        ct.addElement(new Paragraph(24, new Chunk("INSERT PAGE")));
        ct.setCanvas(stamp.getOverContent(2));
        ct.setSimpleColumn(36, 36, 559, 770);

        stamp.close();
        reader.close();
    }

    /**
     * @throws Exception
     * @Title: splitPDF
     * @Description: TODO 分割 page
     * @return: void
     */
    public static void splitPDF() throws Exception {

        FileOutputStream out = new FileOutputStream(FILE_DIR + "splitPDF.pdf");

        document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("1 page"));

        document.newPage();
        document.add(new Paragraph("2 page"));

        document.newPage();
        document.add(new Paragraph("3 page"));

        document.newPage();
        document.add(new Paragraph("4 page"));

        document.close();

        PdfReader reader = new PdfReader(FILE_DIR + "splitPDF.pdf");

        Document dd = new Document();
        PdfWriter writer = PdfWriter.getInstance(dd, new FileOutputStream(FILE_DIR + "splitPDF1.pdf"));
        dd.open();
        PdfContentByte cb = writer.getDirectContent();
        dd.newPage();
        cb.addTemplate(writer.getImportedPage(reader, 1), 0, 0);
        dd.newPage();
        cb.addTemplate(writer.getImportedPage(reader, 2), 0, 0);
        dd.close();
        writer.close();

        Document dd2 = new Document();
        PdfWriter writer2 = PdfWriter.getInstance(dd2, new FileOutputStream(FILE_DIR + "splitPDF2.pdf"));
        dd2.open();
        PdfContentByte cb2 = writer2.getDirectContent();
        dd2.newPage();
        cb2.addTemplate(writer2.getImportedPage(reader, 3), 0, 0);
        dd2.newPage();
        cb2.addTemplate(writer2.getImportedPage(reader, 4), 0, 0);
        dd2.close();
        writer2.close();
    }

    /**
     * @throws Exception
     * @Title: mergePDF
     * @Description: TODO 合并 PDF 文件
     * @return: void
     */
    public static void mergePDF() throws Exception {

        PdfReader reader1 = new PdfReader(FILE_DIR + "splitPDF1.pdf");
        PdfReader reader2 = new PdfReader(FILE_DIR + "splitPDF2.pdf");

        FileOutputStream out = new FileOutputStream(FILE_DIR + "mergePDF.pdf");

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();
        PdfContentByte cb = writer.getDirectContent();

        @SuppressWarnings("unused")
        int totalPages = 0;
        totalPages += reader1.getNumberOfPages();
        totalPages += reader2.getNumberOfPages();

        java.util.List<PdfReader> readers = new ArrayList<PdfReader>();
        readers.add(reader1);
        readers.add(reader2);

        int pageOfCurrentReaderPDF = 0;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();

        // Loop through the PDF files and add to the output.
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();

            // Create a new page in the target for each source page.
            while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                document.newPage();
                pageOfCurrentReaderPDF++;
                PdfImportedPage page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                cb.addTemplate(page, 0, 0);
            }
            pageOfCurrentReaderPDF = 0;
        }
        out.flush();
        document.close();
        out.close();
    }

    /**
     * @throws Exception
     * @Title: sortpage
     * @Description: TODO 排序page
     * @return: void
     */
    public static void sortpage() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "sortpage.pdf");

        document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, out);
        writer.setLinearPageMode();

        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.newPage();
        document.add(new Paragraph("4 page"));
        document.newPage();
        document.add(new Paragraph("5 page"));

        int[] order = {4, 3, 2, 1};
        writer.reorderPages(order);

        document.close();
    }

    /**
     * @throws Exception
     * @Title: addOutline
     * @Description: TODO 目录
     * @return: void
     */
    public static void addOutline() throws Exception {
        document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "addOutline.pdf"));
        document.open();

        // Code 1
        document.add(new Chunk("Chapter 1").setLocalDestination("1"));

        document.newPage();
        document.add(new Chunk("Chapter 2").setLocalDestination("2"));
        document.add(new Paragraph(new Chunk("Sub 2.1").setLocalDestination("2.1")));
        document.add(new Paragraph(new Chunk("Sub 2.2").setLocalDestination("2.2")));

        document.newPage();
        document.add(new Chunk("Chapter 3").setLocalDestination("3"));

        // Code 2
        PdfContentByte cb = writer.getDirectContent();
        PdfOutline root = cb.getRootOutline();

        // Code 3
        @SuppressWarnings("unused")
        PdfOutline oline1 = new PdfOutline(root, PdfAction.gotoLocalPage("1", false), "Chapter 1");
        PdfOutline oline2 = new PdfOutline(root, PdfAction.gotoLocalPage("2", false), "Chapter 2");
        oline2.setOpen(false);

        @SuppressWarnings("unused")
        PdfOutline oline2_1 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.1", false), "Sub 2.1");
        @SuppressWarnings("unused")
        PdfOutline oline2_2 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.2", false), "Sub 2.2");

        @SuppressWarnings("unused")
        PdfOutline oline3 = new PdfOutline(root, PdfAction.gotoLocalPage("3", false), "Chapter 3");

        document.close();
    }

    /**
     * @throws Exception
     * @Title: setHeaderFooter
     * @Description: TODO // Header, Footer
     * @return: void
     */
    public static void setHeaderFooter() throws Exception {
        document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "setHeaderFooter.pdf"));

        writer.setPageEvent(new PdfPageEventHelper() {

            @Override
            public void onEndPage(PdfWriter writer, Document document) {

                PdfContentByte cb = writer.getDirectContent();
                cb.saveState();

                cb.beginText();
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cb.setFontAndSize(bf, 10);

                // Header
                float x = document.top(-20);

                // 左
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left", document.left(), x, 0);
                // 中
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + " page", (document.right() + document.left()) / 2, x, 0);
                // 右
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right", document.right(), x, 0);

                // Footer
                float y = document.bottom(-20);

                // 左
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-Left", document.left(), y, 0);
                // 中
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + " page", (document.right() + document.left()) / 2, y, 0);
                // 右
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-Right", document.right(), y, 0);

                cb.endText();

                cb.restoreState();
            }
        });

        document.open();
        document.add(new Paragraph("1 page"));

        document.newPage();
        document.add(new Paragraph("2 page"));

        document.newPage();
        document.add(new Paragraph("3 page"));

        document.newPage();
        document.add(new Paragraph("4 page"));

        document.close();
    }

    /**
     * @throws Exception
     * @Title: addColumnText
     * @Description: TODO 文字左右文字
     * @return: void
     */
    public static void addColumnText() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "addColumnText.pdf");

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();

        PdfContentByte canvas = writer.getDirectContent();

        Phrase phrase1 = new Phrase("This is a test!left");
        Phrase phrase2 = new Phrase("This is a test!right");
        Phrase phrase3 = new Phrase("This is a test!center");
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 10, 500, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, phrase2, 10, 536, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase3, 10, 572, 0);

        document.close();
    }

    /**
     * @throws Exception
     * @Title: setSlideshow
     * @Description: TODO slideshow
     * @return: void
     */
    public static void setSlideshow() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "setSlideshow.pdf");

        document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, out);

        writer.setPdfVersion(PdfWriter.VERSION_1_5);

        writer.setViewerPreferences(PdfWriter.PageModeFullScreen);// 全屏
        writer.setPageEvent(new PdfPageEventHelper() {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.setTransition(new PdfTransition(PdfTransition.DISSOLVE, 3));
                writer.setDuration(5);// 间隔时间
            }
        });

        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.newPage();
        document.add(new Paragraph("4 page"));
        document.newPage();
        document.add(new Paragraph("5 page"));

        document.close();

    }

    /**
     * @throws Exception
     * @Title: zipPDF
     * @Description: TODO 压缩PDF到Zip
     * @return: void
     */
    public static void zipPDF() throws Exception {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(FILE_DIR + "zipPDF.zip"));
        for (int i = 1; i <= 3; i++) {
            ZipEntry entry = new ZipEntry("hello_" + i + ".pdf");
            zip.putNextEntry(entry);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, zip);
            writer.setCloseStream(false);
            document.open();
            document.add(new Paragraph("Hello " + i));
            document.close();
            zip.closeEntry();
        }
        zip.close();
    }

    /**
     * @throws Exception
     * @Title: setAnnotation
     * @Description: TODO Annotation
     * @return: void
     */
    public static void setAnnotation() throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "setAnnotation.pdf");
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        writer.setLinearPageMode();
        doc.open();
        doc.add(new Paragraph("1 page"));
        doc.add(new Annotation("Title", "This is a annotation!"));

        doc.newPage();
        doc.add(new Paragraph("2 page"));
        Chunk chunk = new Chunk("\u00a0");
        chunk.setAnnotation(PdfAnnotation.createText(writer, null, "Title", "This is a another annotation!", false, "Comment"));
        doc.add(chunk);
        // 添加附件
        doc.newPage();
        doc.add(new Paragraph("3 page"));
        Chunk chunk2 = new Chunk("\u00a0\u00a0");
        PdfAnnotation annotation = PdfAnnotation.createFileAttachment(writer, null, "Title", null, "resource/test2.jpg", "img.jpg");
        annotation.put(PdfName.NAME, new PdfString("Paperclip"));
        chunk2.setAnnotation(annotation);
        doc.add(chunk2);
        doc.close();
    }

    private boolean rotatePdf(String src, String dest) throws IOException, DocumentException {
        try {
            PdfReader reader = new PdfReader(src);
            int n = reader.getNumberOfPages();
            PdfDictionary page;
            Rectangle rectangle;
            boolean flag = false;
            for (int p = 1; p <= n; p++) {
                page = reader.getPageN(p);
                PdfNumber rotate = page.getAsNumber(PdfName.ROTATE);
                int rotateInt = 0;
                if (null != rotate) {
                    rotateInt = rotate.intValue();
                }
                rectangle = reader.getPageSizeWithRotation(p);
                if (rectangle.getWidth() > rectangle.getHeight()) {
                    page.put(PdfName.ROTATE, new PdfNumber(rotateInt + 90));
                    flag = true;
                }
            }
            if (flag) {
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
                stamper.close();
                new File(src).renameTo(new File(src + ".bak.pdf"));
                new File(dest).renameTo(new File(src));
            }
            reader.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
