package com.zhx.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO 文件处理类
 */
public class NIOFileUtil {

    private String path = null;
    private RandomAccessFile raf = null;
    private FileChannel channel = null;
    private StringBuffer headBuffer = null;

    /**
     * @param path 文件夹路径
     */
    public NIOFileUtil(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.path = path;
    }

    /**
     * @param filename 文件名
     * @return
     */
    public NIOFileUtil open(String filename) {
        if (!path.endsWith("/"))
            path = path + "/";
        File file = new File(path + filename);
        try {
            close();
            raf = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            raf = null;
        }
        return this;
    }

    /**
     * 添加到文件起始位置
     *
     * @param msg
     * @return
     */
    public NIOFileUtil head(String msg) {
        if (headBuffer == null)
            headBuffer = new StringBuffer();
        headBuffer.insert(0, msg);
        return this;
    }

    /**
     * 在文件头添加内容后刷新内容到文件中
     *
     * @return
     */
    public NIOFileUtil headFlush() {
        if (raf != null) {
            try {
                // 重新写入
                if (channel == null)
                    channel = raf.getChannel();
                headBuffer.append(read0(0));
                channel.position(0);
                channel.truncate(0);
                write0(headBuffer.toString(), 0);
                headBuffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * 追加到最后
     *
     * @param msg
     * @return
     */

    public NIOFileUtil append(String msg) {
        try {
            if (raf != null) {
                if (channel == null)
                    channel = raf.getChannel();
                write0(msg, channel.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 添加到指定行,不覆盖原有内容
     *
     * @param msg
     * @param line
     * @return
     */
    public NIOFileUtil insertLine(String msg, long line) {
        if (raf != null) {
            long offset = getLineOffset(line);
            insert(msg + "\n", offset);
        }
        return this;
    }

    /**
     * 从指定字符出插入，不覆盖原有内容
     *
     * @param msg
     * @param offset
     * @return
     */
    public NIOFileUtil insert(String msg, long offset) {
        if (raf != null) {
            if (channel == null)
                channel = raf.getChannel();
            if (offset == -1) {
                return this;
            }
            System.out.println(offset);
            String tmp = read0(offset);//偏移后面的内容
            System.out.println(tmp);
            write0(msg, offset);
            write0(tmp, offset + msg.length());
        }
        return this;
    }

    /**
     * 从指定行开始写,覆盖原有内容
     *
     * @param msg
     * @param linenum
     * @return
     */
    public NIOFileUtil writeLine(String msg, long linenum) {
        if (raf != null) {
            if (channel == null)
                channel = raf.getChannel();
            long offset = getLineOffset(linenum);
            if (offset == -1) {
                return this;
            }

            write(msg + "\n", offset);
        }
        return this;
    }

    /**
     * 重新复写某一行
     *
     * @param msg
     * @param linenum
     * @return
     */
    public NIOFileUtil reWriteLine(String msg, long linenum) {
        if (raf != null) {
            if (channel == null)
                channel = raf.getChannel();
            long offset = getLineOffset(linenum);
            if (offset == -1) {
                return this;
            }
            deleteLine(linenum);
            insert(msg + "\n", offset);
        }
        return this;
    }

    public long deleteLine(long linenum) {
        long delete = 0;
        if (raf == null)
            return -1;
        if (channel == null)
            channel = raf.getChannel();
        long offset = getLineOffset(linenum);
        try {
            boolean eof = false;
            boolean fileend = false;
            long tmpoffset = offset;
            channel.position(offset);
            while (!eof) {
                switch (raf.read()) {
                    case -1:  //文件结束
                        eof = true;
                        fileend = true;
                        break;
                    case '\n':
                        eof = true;
                        tmpoffset = channel.position();
                        break;
                    case '\r':
                        eof = true;
                        long cur = raf.getFilePointer();
                        if ((raf.read()) != '\n') {
                            tmpoffset = cur;
                        } else {
                            tmpoffset = raf.getFilePointer();
                        }
                        break;
                    default:
                        break;
                }
            }
            if (fileend) { //文件读完了，直接删除
                channel.truncate(offset);
            } else {  //文件未读完，需要移位
                String tmp = read0(tmpoffset); //读取后面的内容
                delete = tmpoffset - offset;
                channel.truncate(channel.size() - delete);
                write0(tmp, offset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return delete;
    }

    /**
     * 从指定位置写，覆盖原有内容
     *
     * @param msg
     * @param offset
     * @return
     */
    public NIOFileUtil write(String msg, long offset) {
        // TODO: 2017/7/21
        write0(msg, offset);
        return this;
    }

    /**
     * 一次读取全部内容
     *
     * @return
     */
    public String read() {
        return read(0);
    }

    /**
     * 从偏移位置，读取全部内容
     *
     * @return
     */
    public String read(long offset) {
        if (raf != null) {
            if (channel == null) {
                channel = raf.getChannel();
            }
            try {
                channel.position(offset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteBuffer buf = ByteBuffer.allocate(48);
            try {
                StringBuffer sb = new StringBuffer();
                while (channel.read(buf) > 0) {
                    sb.append(new String(buf.array()));
                }
                buf.flip();

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 从文件当前位置开始一行一行的读
     *
     * @return
     */
    public String readLine() {
        if (raf == null)
            return "";
        String s = "";
        try {
            s = raf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将文件指针设置为从头开始
     *
     * @return
     */
    public NIOFileUtil resetPositon() {
        try {
            raf.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 读取指定行
     *
     * @param line
     * @return
     */
    public String readLine(long line) {
        long offset = getLineOffset(line);
        if (offset == -1)
            return "";
        try {
            raf.seek(offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return raf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取行偏移
     *
     * @param lineid
     * @return
     */
    private long getLineOffset(long lineid) {
        if (raf == null)
            return -1;
        if (lineid < 0)
            return -1;
        int linecounter = 0;
        long offset = -1;
        boolean eol = false;
        boolean fileend = false;
        if (lineid == 0)
            return 0;
        try {
            if (channel == null)
                channel = raf.getChannel();
            long pos = channel.position();
            channel.position(0);
            while (!eol) {
                switch (raf.read()) {
                    case -1:  //文件结束
                        eol = true;
                        fileend = true;
                        break;
                    case '\n':
                        linecounter++;
                        offset = channel.position();
                        break;
                    case '\r':
                        linecounter++;
                        long cur = raf.getFilePointer();
                        if ((raf.read()) != '\n') {
                            offset = cur;
                        } else {
                            offset = raf.getFilePointer();
                        }
                        break;
                    default:
                        break;
                }
                if (lineid == linecounter) { //找到行号
                    eol = true;
                }
            }
            if (fileend) { //文件结束也没有找到
                offset = -1;
                System.out.println("getLineOffset: 文件结束也没有找到");
            }
            channel.position(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return offset;
    }

    /**
     * 写入数据到文件,会覆盖之后的内容
     *
     * @param msg
     * @param offset
     * @return
     */
    private int write0(String msg, long offset) {
        if (raf != null) {
            try {
                if (channel == null)
                    channel = raf.getChannel();
                if (offset > raf.length())
                    offset = raf.length();
                channel.position(offset);
                byte[] tmp = msg.getBytes();
                ByteBuffer buf = ByteBuffer.allocate(tmp.length);
                buf.put(tmp);
                buf.flip();
                while (buf.hasRemaining()) {
                    channel.write(buf);
                }
                return buf.limit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 从偏移位置开始读
     *
     * @param offset
     * @return
     */
    private String read0(long offset) {
        if (raf != null) {
            if (channel == null) {
                channel = raf.getChannel();
            }
            try {
                if (offset > channel.size())
                    offset = channel.size();
                channel.position(offset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteBuffer buf = ByteBuffer.allocate(64);
            try {
                StringBuffer sb = new StringBuffer();
                int a;
                while ((a = channel.read(buf)) > 0) {
                    System.out.println("a=" + a);
                    sb.append(ByteBuffer2String(buf, a));
                    buf.flip();
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String ByteBuffer2String(ByteBuffer buf, int length) {
        byte[] tmp = new byte[length];
        System.arraycopy(buf.array(), 0, tmp, 0, length);
        return new String(tmp);
    }

    /**
     * 清空文件内容
     *
     * @return
     */
    public NIOFileUtil clear() {
        if (raf != null)
            try {
                if (channel == null)
                    channel = raf.getChannel();
                channel.truncate(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return this;
    }

    /**
     * 关闭操作
     */
    public void close() {
        if (channel != null)
            try {
                if (channel.isOpen())
                    channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (raf != null) {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

}
