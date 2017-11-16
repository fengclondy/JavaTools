<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>网页图片批量下载器</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script>
	function All() {
		var checkElements = document.getElementsByName('selected');
		for (var i = 0; i < checkElements.length; i++) {
			var checkElement = checkElements[i];
			checkElement.checked = "checked";
		}
	}
	function Un() {
		var checkElements = document.getElementsByName('selected');
		for (var i = 0; i < checkElements.length; i++) {
			var checkElement = checkElements[i];
			checkElement.checked = null;
		}

	}
</script>
</head>
<!-- 元素加背景图片 -->
<body style="background: url(bg2.jpg)">
	<!--水平居中只需要margin:0 auto  -->
	<!--垂直居中只需要line-height: 480px  -->
	<!-- 设置测试边框border:5px solid red; -->
	<div
		style="width: 100%; height: 480px; margin: 20px auto; text-align: center">
		<h1>网页图片自定义批量预览/下载器</h1>
		<form action="/getpic/img" method="post"
			onsubmit="return validator();">
			<input type="text" name="url" id="url"
				style="width: 260px; height: 35px"
				placeholder="请输入网页地址,域名请加上http://协议头"><input type="submit"
				value="获取网页图片" style="width: 120px; height: 35px">
		</form>
		<form action="/getpic/addreptile" method="post" id="ff">
			<h1>
				一共找到${imageSrc.size() }张图片&nbsp;&nbsp;
				<input type="button" name="checkall" id="checkall" value="全选" onclick="All()">&nbsp;&nbsp;
				<input type="button" name="checkall" id="checkall" value="全部取消" onclick="Un()"> &nbsp;&nbsp;
				<input type="button" value="保存路径" onclick="choose()">&nbsp;&nbsp;文件默认下载D盘 请自行修改
				<%--<input type="submit" value="下载选中的图片" >&nbsp;&nbsp;--%>
				<input type="button" value="下载选中的图片" onclick="dowload()" >&nbsp;&nbsp;


			</h1>
			<table>
				<c:forEach items="${imageSrc }" var="i">
					<div style='float: left; margin: 5px'>
						<img src='${i }' width='180' height='200'> <input
							name="selected" id="selected" type="checkbox" value="${i }">
					</div>
				</c:forEach>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		function validator() {
			var url = document.getElementById("url").value;
			//空判断
			if (url == "" || url.length == 0) {
				alert("请输入URL,如http://www.baidu.com");
				document.getElementById("url").focus();
				return false;
			}

			//合法性判断
			if (url != "" && url.indexOf("http://") == -1 && url.indexOf("https://") == -1) {
				alert("请输入一个正确的URL,如http://www.baidu.com");
				document.getElementById("url").focus();
				return false;
			}

			return true;
		}

		function choose() {
			$.ajax({
                type: 'post',
                url: "/getpic/chooseFolder",
                dataType: 'json',
                data: {
                },
                success: function (data) {
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }

			});
        }

        function dowload() {
			$.post("/getpic/addreptile",$("#ff").serialize(),function (data) {
			    alert(data)
				if (data == "true") {
                    alert("下载成功")
                }else {
                    alert("下载失败")
                }
            })
        }

		
	</script>
</body>
</html>
