package com.bupt.liu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

/**
 * 用这个方法把本地文件 上传到hdfs是可行的！！！ 测试了一个晚上 ohmygod
 * Created by lpeiz on 2017/9/9.
 */
public class AppDemo1 {
    public static void main(String[] args) throws Exception{

        String[] str = new String[]{"F:\\atestDir\\tmp\\lxw_orc1_file.orc","hdfs://10.109.253.156:8020/user/test/lxw_orc1_fileaaaaaaa.orc"};
        Configuration conf = new Configuration();

        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        Path path = new Path("hdfs://10.109.253.156:8020/user/test/lxw_orc1_fileaaaaaaa.orc");
        //                FileSystem fileS= FileSystem.get(conf);
        FileSystem fileS = path.getFileSystem(conf);
        FileSystem localFile = FileSystem.getLocal(conf);  //得到一个本地的FileSystem对象



        Path input = new Path("F:\\atestDir\\tmp\\lxw_orc1_file.orc"); //设定文件输入保存路径
        Path out = new Path(str[1]);  //文件到hdfs输出路径

        try{
            FileStatus[] inputFile = localFile.listStatus(input);  //listStatus得到输入文件路径的文件列表
            FSDataOutputStream outStream = fileS.create(out);      //创建输出流
            for (int i = 0; i < inputFile.length; i++) {
                System.out.println(inputFile[i].getPath().getName());
                FSDataInputStream in = localFile.open(inputFile[i].getPath());

                byte buffer[] = new byte[1024];
                int bytesRead = 0;
                while((bytesRead = in.read(buffer))>0){  //按照字节读取数据
                    System.out.println(buffer);
                    outStream.write(buffer,0,bytesRead);
                }

                in.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

