参考：http://www.360doc.com/content/14/0306/17/16088576_358273704.shtml

    简单邮件发送
    
##1. 导包

- mail.jar
- activation.jar


##2. 使用

	邮件的配置文件可以从配置文件中写入。
	这里也可调用里面的set 的方法来对各各参数进行初始化，也不用从配置文件中读取的方式
    配置文件 ： src根目录下 文件名为： email_template.properties
    按自己的需来填写
        subject=
        content=
        from= 
        host=smtp.qq.com
        username=
        password=

### 收件人为1人

 * 只要用setTo("mailaddress@host");来设置发送给谁就行了
 * 然后调用sendMessage();来发送邮件
 
 
### 收件人为多人
 - 设置setAddresses(adds)
 - 然后调用sendMessageMulit();来发送邮件
 