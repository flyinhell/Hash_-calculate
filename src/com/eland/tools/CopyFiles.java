package com.eland.tools;

import com.eland.model.ImportConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class CopyFiles {
    private static Logger log = Logger.getLogger("Log");

    //複製檔案
    public boolean copyFile(String srFile, String dtFile) {
        boolean check;
        try {
            FileChannel srcChannel = new FileInputStream(srFile).getChannel();
            FileChannel dstChannel = new FileOutputStream(dtFile).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
            check = true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            check = false;
        }
        return check;
    }

    //複製資料夾
    public boolean copyDirectory(File source, File target, String pathSymbol) {
        boolean check = false;
        try {
            File[] file = source.listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    File sourceDemo = new File(source.getAbsolutePath() + pathSymbol
                            + file[i].getName());
                    File destDemo = new File(target.getAbsolutePath() + pathSymbol
                            + file[i].getName());
                    check = copyFile(sourceDemo.toString(), destDemo.toString());
                } else if (file[i].isDirectory()) {
                    File sourceDemo = new File(source.getAbsolutePath() + pathSymbol
                            + file[i].getName());
                    File destDemo = new File(target.getAbsolutePath() + pathSymbol
                            + file[i].getName());
                    destDemo.mkdir();
                    check = copyDirectory(sourceDemo, destDemo, pathSymbol);
                }
                if (!check) {
                    log.info("copy fail!!!");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return check;
    }

    public boolean copyDocument(File source_file, File target_file, ImportConfig config) {
        boolean check = true;
        String pathSymbol = config.s_pathSymbol;
        if (source_file.exists()) {
            if (source_file.isDirectory()) {
                target_file.mkdir();
                check = copyDirectory(source_file, target_file, pathSymbol);
            } else if (source_file.isFile()) {
                check = copyFile(source_file.toString(), target_file.toString());
            }
            if (!check) {
                return false;
            }
        } else {
            log.info("files not exists. " + source_file.toString());
        }
        return check;
    }

    //根據設定檔來決定複製 method
    public boolean copyByType(File sourceFile, File targetFile, ImportConfig config) {
        boolean check = false;
        try {
            check = copyDocument(sourceFile, targetFile, config);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return check;
    }

    //取得現在所使用的索引庫名稱(FTSearch)
    public String currentIndex(File source_file) {
        String name = "FTSearch";
        try {
            File[] file = source_file.listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].getName().equals("FTSearchNew")) {
                    name = "FTSearchNew";
                    break;
                } else if (file[i].getName().startsWith("_.FTSearch")) {
                    int startIndex = file[i].getName().lastIndexOf(".") + 1;
                    int endIndex = file[i].getName().length();
                    name = file[i].getName().substring(startIndex, endIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return name;
    }



}
