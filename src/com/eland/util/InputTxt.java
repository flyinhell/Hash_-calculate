package com.eland.util;

import org.ini4j.Wini;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class InputTxt {

    public void inputTxt(String content,String hashTxtPath){
        String iniPath;        //ini檔路徑
        String log4jPath;      //log4j 路徑

        try {
            iniPath = "config.ini";
            Wini ini = new Wini(new File(iniPath));
            File writename = new File(hashTxtPath);
            BufferedWriter out = new BufferedWriter(new FileWriter(writename,true));
            out.write(content+"\r\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
