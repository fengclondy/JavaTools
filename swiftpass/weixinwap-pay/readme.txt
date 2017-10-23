1.需要修改src下面的config.properties中的配置
	1.1 mch_id：威富通商户号
	1.2 key：威富通交易密钥
	1.3 req_url：支付请求url
	1.4 notify_url：威富通通知商户url

2.本项目没有记录文件或者数据库日志，只是记录在容器日志里面，如有需要可以将System.out.println类似的地方改写成log文件或者数据库
3.本项目的订单数据没有记录到数据库中，只是在内存中设置了一个map（具体可以见TestPayServlet.java中的orerResult）,这里如果有需要可以将订单数据存储到数据库
