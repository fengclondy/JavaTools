package com.jimzhang.utils;

import com.jcraft.jsch.*;

/**
02	 * 利用JSch包实现SFTP下载、上传文件
03	 * @param ip 主机IP
04	 * @param user 主机登陆用户名
05	 * @param psw  主机登陆密码
06	 * @param port 主机ssh2登陆端口，如果取默认值，传-1
07	 */
public class SFTPClientUtil {  

	/**
	 * IP:IP地址
	 * user：用户名
	 * psw：密码
	 * port：端口号
	 * files：文件夹
	 * src：上传文件名称
	 * dest:文件来源
	 * 将本地文件名为src的文件上传到目标服务器，目标文件名为dst，若dst为目录，则目标文件名将与src文件名相同,采用默认的传输模式：OVERWRITE
	 */
	public static void sshSftp(String ip, String user, String psw ,int port,String src,String dest) throws Exception{
	    Session session = null;
	    Channel channel = null;
	    JSch jsch = new JSch();
	    if(port <=0){
	        //连接服务器，采用默认端口
	        session = jsch.getSession(user, ip);
	    }else{
	        //采用指定的端口连接服务器
	        session = jsch.getSession(user, ip ,port);
	    }
	 
	    //如果服务器连接不上，则抛出异常
	    if (session == null) {
	        throw new Exception("session is null");
	    }
	     
	    //设置登陆主机的密码
	    session.setPassword(psw);//设置密码  
	    //设置第一次登陆的时候提示，可选值：(ask | yes | no)
    	session.setConfig("StrictHostKeyChecking", "no");
	    //设置登陆超时时间  
	    session.connect(30000);
	         
	    try {
	        //创建sftp通信通道
	        channel = (Channel) session.openChannel("sftp");
	        channel.connect(1000);
	        ChannelSftp sftp = (ChannelSftp) channel;
			// 创建目录
			createDir(sftp, dest);
			sftp.cd(dest);

			sftp.put(src,dest,ChannelSftp.OVERWRITE);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.disconnect();
	        channel.disconnect();
	    }
	}


	/**
	 * 创建目录
	 *
	 *
	 * @param sftp
	 * @param createpath
	 * @return
	 */
	public static boolean createDir(ChannelSftp sftp, String createpath) {
		try {
			if (isDirExist(sftp, createpath)) {
				sftp.cd(createpath);
				System.out.println("进入目录：" + createpath);
				return true;
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(sftp, filePath.toString())) {
					sftp.cd(filePath.toString());
					System.out.println("进入目录：" + createpath);
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
					System.out.println("新建目录成功");
					System.out.println("进入目录：" + createpath);
				}
			}
			sftp.cd(createpath);

			return true;
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断目录是否存在
	 *
	 *
	 * @param sftp
	 * @param directory
	 * @return
	 */
	public static boolean isDirExist(ChannelSftp sftp, String directory) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}
}