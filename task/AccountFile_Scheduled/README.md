支付成功SQL：
```
SELECT
	p.gatewayOrderNo,
	p.backOrderNo AS transactionId,
	p.amount,
	p.updateDate AS payTime,
	p.`status`,
	p.refundStatus
FROM
	gateway_order o,
	gateway_order_pay_detail p
WHERE
	TO_DAYS('2017-09-02 16:42:05') - TO_DAYS( p.updateDate) = 1
AND
	o.paymentWay = '501'
AND p.`status` = '00'
-- AND p.refundStatus IS NULL -- (去掉这一行，包含已退款记录)
AND o.gatewayOrderNo = p.gatewayOrderNo
ORDER BY
	p.id DESC
```
如下：

 | gatewayOrderNo   | transactionId                | amount | payTime             | status | refundStatus |
 | ---------------- | ---------------------------- | ------ | ------------------- | ------ | ------------ |
 | zhxgw19314962825 | 4003602001201709019441999359 | 3      | 2017-09-01 08:36:06 | 00     |              |
 | zhxgw19314732590 | 4003602001201709019443646930 | 3      | 2017-09-01 08:34:43 | 00     |              |
 | zhxgw19314588064 | 4003602001201709019443415581 | 3      | 2017-09-01 08:29:37 | 00     |              |
 | zhxgw19290336133 | 4003602001201708217461702335 | 3      | 2017-09-01 17:01:43 | 00     | 00           |


退款成功SQL：
```
SELECT
	p.gatewayOrderNo,
	p.backOrderNo AS transactionId,
	p.amount,
	p.createDate AS creataTime,
	p.updateDate AS payTime,
	p.refundUpdateDate as redundTime
FROM
	gateway_order o,
	gateway_order_pay_detail p
WHERE
	TO_DAYS('2017-09-02 08:34:57') - TO_DAYS(p.updateDate) = 1
AND o.paymentWay = '501'
AND p.`status` = '00'
AND p.refundStatus = '00'
AND o.gatewayOrderNo = p.gatewayOrderNo
ORDER BY
	p.updateDate DESC
	p.id DESC
```

如下：
订单号为：zhxgw19290336133，保存到文件中该订单记录会有2条，1条是支付的，1条
是退款的。

| gatewayOrderNo   | transactionId                | amount | payTime             | status | refundStatus |
| ---------------- | ---------------------------- | ------ | ------------------- | ------ | ------------ |
| zhxgw19290336133 | 4003602001201708217461702335 | 3      | 2017-09-01 17:01:43 | 00     | 00           |

思路：
- 先读取数据库获取数据
- 新建文件夹、文件，确保文件夹、文件路径
    - 1.若文件已存在，先删除（防止多次执行任务追加文件造成数据重复）
- 写入数据到文件
- 判断文件是否在临时路径生成成功
    - 1. 若文件未生成，重新开始该方法
- 开始上传文件到sftp


