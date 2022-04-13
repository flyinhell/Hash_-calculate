package com.eland.hashtest;

import com.eland.model.ImportConfig;
import com.eland.util.*;
import org.apache.log4j.Logger;
import org.ini4j.Wini;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public  class CheckSum {
    private static Logger logger = Logger.getLogger("Log");
    InputTxt inputTxt = new InputTxt();
    Tools delFile =new Tools();
    String iniPath;        //ini檔路徑
    String log4jPath;      //log4j 路徑
    String hashTxtPath;

    public void checkSumMD5(String indexDir) {
        String separator = File.separator;
        HashUtil hashUtil =new HashUtil();
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                String md5 = hashUtil.getMD5(file);
                System.out.println("MD5:" + md5);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumMD5(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param indexDir 要計算MD5的資料夾路徑
     * @param resetTxt 是否重製MD5文件檔
     */
    public void checkSumMD5BigFile(String indexDir,boolean resetTxt) {
        HashMessageDigest hashMessageDigest =new HashMessageDigest();
        String md5String;
        try {
            iniPath = "config.ini";
            Wini ini = new Wini(new File(iniPath));
            hashTxtPath = ini.get("System","HashTxtPath2");
            File hashTxtPathFile = new File(hashTxtPath);
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(resetTxt){
                delFile.delFile(hashTxtPathFile);
            }
            if(file.isFile()){
              //  System.out.println("文件名：" + file.getAbsolutePath());
                String md5 = hashMessageDigest.fileMD5(file.getAbsolutePath());
                md5String =md5+"  "+file.getAbsolutePath();
                System.out.println(md5String);
                inputTxt.inputTxt(md5String,hashTxtPath);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumMD5BigFile(f.getAbsolutePath(),false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkSumMD5Hex(String indexDir) {
        HashUtil hashUtil =new HashUtil();
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                String md5Hex = hashUtil.StringMD5(file.getAbsolutePath());

                System.out.println("MD5Hex:" + md5Hex);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumMD5Hex(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkSumSha1(String indexDir) {
        HashUtil hashUtil =new HashUtil();
        String Sha1;
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }

            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                Sha1 = hashUtil.getSha1(file);
                System.out.println("Sha1:" + Sha1);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumSha1(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void checkSumSha256(String indexDir) {

        HashUtil hashUtil =new HashUtil();
        String Sha256;
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                Sha256 = hashUtil.getSha256(file);
                System.out.println("Sha256:" + Sha256);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumSha256(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkSumMD5Guava(String indexDir, boolean resetTxt, ImportConfig config) {
        String md5Guava,md5String;
        Guava guava =new Guava();
        try {
            hashTxtPath = config.hashTxtPathBefore;
            File hashTxtPathFile = new File(hashTxtPath);
            if(!hashTxtPathFile.exists()){
                hashTxtPathFile.getParentFile().mkdirs();
                hashTxtPathFile.createNewFile();
            }

            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(resetTxt){
                delFile.delFile(hashTxtPathFile);
            }
            if(file.isFile()){
                //System.out.println("文件名：" + file.getAbsolutePath());
                md5Guava = guava.getMD5Guava(file);
                md5String =md5Guava+"  "+file.getAbsolutePath();
                System.out.println(md5String);
                inputTxt.inputTxt(md5String,hashTxtPath);

               // System.out.println("MD5Guava:" + md5Guava);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumMD5Guava(f.getAbsolutePath(),false,config);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
    public void checkSumSha1Guava(String indexDir) {
        String separator = File.separator;
        HashUtil hashUtil =new HashUtil();
        Guava guava =new Guava();
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                String Sha1Guava = guava.getSha1Guava(file);

                System.out.println("Sha1Guava:" + Sha1Guava);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumSha1Guava(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void checkSumSha256Guava(String indexDir) {
        String separator = File.separator;
        HashUtil hashUtil =new HashUtil();
        Guava guava =new Guava();
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                String Sha256Guava = guava.getSha256Guava(file);

                System.out.println("Sha256Guava:" + Sha256Guava);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumSha256Guava(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void checkSumCrc32Guava(String indexDir) {
        String separator = File.separator;
        HashUtil hashUtil =new HashUtil();
        Guava guava =new Guava();
        try {
            File file = new File(indexDir);
            if (!file.exists()) {
                System.out.println("沒有此索引庫:"+indexDir);
                return;
            }
            if(file.isFile()){
                System.out.println("文件名：" + file.getAbsolutePath());
                String Crc32Guava = guava.getCrc32Guava(file);

                System.out.println("Crc32Guava:" + Crc32Guava);
            }else if (file.isDirectory()) {
                for (File f: file.listFiles()){
                    checkSumCrc32Guava(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
