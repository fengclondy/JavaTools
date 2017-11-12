参考：
>官网例子：https://developers.itextpdf.com/content/itext-7-jump-start-tutorial/examples/
>- itext5：http://rensanning.iteye.com/blog/1538689
>- 牛蛙itext5：https://my.oschina.net/lujianing/blog/894365
>- itext7：http://blog.csdn.net/u012397189/article/details/78471319


# 第一章 介绍基本构建块
>我们讨论了一些高级对象，比如段落、列表、图像、表格和单元格，这些都是iText的基本构建块。 

# 第二章 添加低级内容
>在本章中，我们已经尝试了PDF操作符和操作数以及相应的iText方法。我们已经了解了一种叫做图形状态的概念，它可以跟踪诸如当前转换矩阵、线宽、颜色等属性。文本状态是图形状态的一个子集，它涵盖了与文本相关的所有属性，比如文本矩阵、文本的字体和大小，以及我们尚未讨论的许多其他属性。我们将在另一个教程中详细介绍。

# 第三章 使用renderers和事件处理程序
>在本章中，我们了解了为什么对前一章中讨论的低级功能有一些了解是很重要的。我们可以将此功能与基本构建块结合使用，以创建自定义功能。我们已经为单元对象创建了自定义边界。我们已经在页面中添加了背景颜色，我们已经介绍了页眉和页脚。当我们添加一个水印时，我们发现我们真的不需要知道PDF语法的所有细节。我们可以使用一种方便的方法来处理变换矩阵的旋转和分度

# 第四章 做一个PDF互动
>我们在这一章中开始了一些注释类型:
 一个文本注释,
 一个链接注释,
 一行注释
 一个文本标记注释。
 我们还提到了小部件注释。这把我们引向了互动形式的主题。我们学习了如何创建一个表单，但更重要的是如何填写和精简表单。
 在填充和扁平示例中，我们遇到了一个新的类，PdfReader。在下一章中，我们将介绍更多使用这个类的例子。

# 第五章 操作现有的PDF文档
>在前一章中，我们学习了交互式PDF表单。在本章中，我们继续使用这些表单。我们添加了一个注释、一些文本和一个现有表单的额外字段。我们还在填写表单时更改了一些属性。
 然后，我们在没有任何交互作用的情况下转移到PDFs。首先，我们添加了一个标题、一个页脚和一个水印。然后，我们使用现有文档页面的大小和方向。
 在下一章中，我们将扩展现有文档，并将发现如何将多个文档组合成一个PDF。

# 第六章 重用现有的PDF文档
>在本章中，我们一直在缩放、tiling、n - upping一个文件和一个不同的文件作为结果。我们还以许多不同的方式组装文件。我们发现在合并交互表单时存在一些缺陷。关于重用现有PDF文档的内容，还需要做更多的工作。
 在下一章，我们将讨论PDF文档，这些文档符合PDF / UA和PDF / A等特殊PDF标准。我们将发现合并PDF /文档也需要一些特别的注意。

# 第七章 创建PDF / UA和PDF /文件
>在本章中，我们已经发现了PDF格式的内容比我们看到的要多。我们已经学习了如何将结构引入到我们的文档中，以便盲人和视障人士可以访问它们。我们还确保我们的PDFs是自包含的，例如嵌入字体，这样我们的文档就可以长期存档。

## 中文
````
 // 设置字体
PdfFont font = PdfFontFactory.createFont("src/main/resources/static/font/simhei.ttf", PdfEncodings.IDENTITY_H, true);
PdfFont font1 = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
//Add paragraph to the document
document.add(new Paragraph("Hello World!张晋苗").setFont(font));
document.add(new Paragraph("这里用的是itext7的版本！").setFont(font1));
````


