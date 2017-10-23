<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
<title>微信支付</title>
<meta name="description" content="微信支付" />
<meta name="keywords" content="微信支付" />
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<link href="css/style.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript">
	   //var timer;
		/* $(function(){
			var handler = function(){
				var out_trade_no = $('input[name=out_trade_no]').val();
				$.post("testPayResultQuery?out_trade_no="+out_trade_no,null,function(msg){
					//alert(msg);
					if(msg == '1'){
// 						alert("支付成功");
						document.location.href="pay_success.jsp";
						clearInterval(timer);
					}
				});
			}
			timer = setInterval(handler , 5000);
		}); */
		//调用微信JS api 支付
		
		function jsApiCall()
		{	
			var appId = $('input[name=appId]').val();
			var timeStamp = $('input[name=timeStamp]').val();
			var nonceStr = $('input[name=nonceStr]').val();
			var package1 = $('input[name=package1]').val();
			var signType = $('input[name=signType]').val();
			var paySign = $('input[name=paySign]').val();
			
			
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest',{
					"appId" : appId, //公众号名称，由商户传入
					"timeStamp": timeStamp, //时间戳，自1970 年以来的秒数
					"nonceStr" : nonceStr, //随机串
					"package" : package1,
					"signType" : signType, //微信签名方式:
					"paySign" : paySign  //微信签名,
				},function(res){
					if(res.err_msg == "get_brand_wcpay_request:ok" ) {
						// 此处可以使用此方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回ok，但并不保证它绝对可靠。
						document.location.href="pay_success.jsp";
					}
					/* for(var i in res){
						alert(res[i]);
					} */
				}
			);
		}
		
	</script>
</head>
<body>
<div align="center" bgcolor="#666666">
   <div>
<!--扫描代码-->
	<input type="hidden" name="out_trade_no"  value="${out_trade_no}"/>
	<input type="hidden" name="appId"  value="${appId}"/>
	<input type="hidden" name="timeStamp"  value="${timeStamp}"/>
	<input type="hidden" name="nonceStr"  value="${nonceStr}"/>
	<input type="hidden" name="package1"  value="${package1}"/>
	<input type="hidden" name="signType"  value="${signType}"/>
	<input type="hidden" name="paySign"  value="${paySign}"/>
	
      <div class="s-con" id="codem">
	   <div class="m26">
               <h1 style="font-size:50px;">订单提交成功，请您尽快付款！</h1>
               <div class="num"><span class="color1 ml16" style="font-size:50px;">订单号：<label id="out_trade_no" class="orange">${out_trade_no}</label></span></div>
         </div>
         <div class="num"><span class="color1 ml16" style="font-size:50px;">商品名称：<label class="orange">${body}</label></span>
		 
		 <div class="num"><span class="color1 ml16" style="font-size:50px;">订单金额：<label class="orange">${total_fee/100}</label></span></div>
		 </div>
         
         <div align="center">
		<button style="width:600px; height:100px; background-color:#FE6714; border:0px #FE6714 solid; cursor: pointer;  color:white;  font-size:50px;" type="button" onclick="jsApiCall()" >微信支付</button>
	</div>
      </div>

</div>
</body>
</html>
