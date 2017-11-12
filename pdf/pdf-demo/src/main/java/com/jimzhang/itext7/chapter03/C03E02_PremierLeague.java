package com.jimzhang.itext7.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description Simple table renderer example. 简单的事件处理程序的例子
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-09 16:15
 */
public class C03E02_PremierLeague {
    public static final String DATA = "src/main/resources/static/data/premier_league.csv";
    public static final String DEST = "results/chapter03/premier_league.pdf";

    Color greenColor = new DeviceCmyk(0.78f, 0, 0.81f, 0.21f);
    Color yellowColor = new DeviceCmyk(0, 0, 0.76f, 0.01f);
    Color redColor = new DeviceCmyk(0, 0.76f, 0.86f, 0.01f);
    Color blueColor = new DeviceCmyk(0.28f, 0.11f, 0, 0);

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E02_PremierLeague().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        // 不使用默认大小，自定义页面大小
        PageSize ps = new PageSize(842, 680);

        // Initialize document
        Document document = new Document(pdf, ps);
        // 将表的内容的文本对齐方式设置为以它为中心。改变了表本身的水平对齐方式——注意，这并不重要，因为表占用了所有可用宽度的100%。
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Table table = new Table(new float[]{1.5f, 7, 2, 2, 2, 2, 3, 4, 4, 2});
        table.setWidthPercent(100)
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();

        document.add(table);

        //Close document
        document.close();

    }

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int columnNumber = 0;
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                // 使用一个自定义RoundedCornersCellRenderer
                cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
                // 定义了单元格内容的填充，并将边界设置为null。如果没有setBorder(null)，将会绘制两个边界:一个由iText，
                // 一个由我们将要检查的单元渲染器绘制
                cell.setPadding(5).setBorder(null);
                table.addHeaderCell(cell);
            } else {
                columnNumber++;
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                // 使用setBorder()方法来覆盖缺省边界。将边界定义为一个带有0.5个用户单元的黑色实线边界。
                cell.setFont(font).setBorder(new SolidBorder(Color.BLACK, 0.5f));
                // 根据列号改变背景颜色
                switch (columnNumber) {
                    case 4:
                        cell.setBackgroundColor(greenColor);
                        break;
                    case 5:
                        cell.setBackgroundColor(yellowColor);
                        break;
                    case 6:
                        cell.setBackgroundColor(redColor);
                        break;
                    default:
                        cell.setBackgroundColor(blueColor);
                        break;
                }
                table.addCell(cell);
            }
        }
    }


    private class RoundedCornersCellRenderer extends CellRenderer {
        public RoundedCornersCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        /**
         * 重写drawBorder()方法，以绘制在顶部圆形的矩形
         * @param drawContext
         */
        @Override
        public void drawBorder(DrawContext drawContext) {
            // 返回一个矩形对象，我们可以使用它来查找BlockElement的边界框(第8行)，我们使用getX()、getY()、getWidth()和
            // getHeight()方法来定义单元格的左下角和右上角的坐标
            Rectangle rectangle = getOccupiedAreaBBox();
            float llx = rectangle.getX() + 1;
            float lly = rectangle.getY() + 1;
            float urx = rectangle.getX() + getOccupiedAreaBBox().getWidth() - 1;
            float ury = rectangle.getY() + getOccupiedAreaBBox().getHeight() - 1;
            // 把边界画成一条线和曲线的序列
            PdfCanvas canvas = drawContext.getCanvas();
            float r = 4;
            float b = 0.4477f;
            canvas.moveTo(llx, lly).lineTo(urx, lly).lineTo(urx, ury - r)
                    .curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury)
                    .lineTo(llx + r, ury)
                    .curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r)
                    .lineTo(llx, lly).stroke();
            super.drawBorder(drawContext);
        }
    }
}
