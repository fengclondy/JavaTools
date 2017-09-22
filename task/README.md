# 定时任务类项目



## AccountFile_Scheduled

Springboot版本的定时任务，读取数据库生成文件账单并上传到sftp 服务器。



## wechant_Scheduled

纯java版本项目，打包为jar，调用main方法，使用linux的cron定时功能。

````
#  Execute every morning at 1:30 am to upload wechant APP_stores Trading bill by sftp at 106.37.176.97 ()
30 1 * * * java -jar //data/servers/wechant_accountFile_task/wechant_Scheduled.jar >> //data/servers/wechant_accountFile_task/info.log
````



