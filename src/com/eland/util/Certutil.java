package com.eland.util;

import com.google.common.base.Utf8;
import org.ini4j.Wini;

import java.io.*;

public class Certutil {
    InputTxt inputTxt = new InputTxt();
    Tools delFile =new Tools();
    String iniPath;        //ini檔路徑
    String log4jPath;      //log4j 路徑
    String hashTxtPath;

    /**
     * WINDOWS 元件測試，有BUG(不使用)
     * @param indexDir
     * @param resetTxt
     */
    public void certutil(String indexDir,boolean resetTxt) {
        String md5Certutil,md5String;
        try {
            iniPath = "config.ini";
            Wini ini = new Wini(new File(iniPath));
            File file = new File(indexDir);
            hashTxtPath = ini.get("System","HashTxtPath3");
            File hashTxtPathFile = new File(hashTxtPath);

            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(resetTxt){
                delFile.delFile(hashTxtPathFile);
            }

            if(file.isFile()){
                //System.out.println("文件名：" + file.getAbsolutePath());
                md5Certutil = certutilMd5(file.getAbsolutePath());
                md5String =md5Certutil+"  "+file.getAbsolutePath();
                System.out.println(md5String);
                inputTxt.inputTxt(md5String,hashTxtPath);

                // System.out.println("MD5Guava:" + md5Guava);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    certutil(f.getAbsolutePath(),false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String certutilMd5(String inputFile){
        String hash="";
        try {
            String[] cmd = new String[5];
            cmd[0]="cmd ";
            cmd[1]="/c ";
            cmd[2]="certutil.exe -hashfile ";
            cmd[3]=inputFile;
            cmd[4]=" SHA1";

            String cmdStr=cmd[0]+cmd[1]+cmd[2]+cmd[3]+cmd[4];
            Process pro = Runtime.getRuntime().exec(cmdStr);
            InputStreamReader isr = new InputStreamReader(pro.getInputStream(), "BIG5");
            BufferedReader read = new BufferedReader(isr);
           // BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            int i=0;

            String msg = null;
            while ((msg = read.readLine()) != null) {
                i++;
                //   System.out.println(msg);
                if(i==2){
                    hash =msg;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hash;
    }
}


