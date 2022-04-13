package com.eland.tools;

import org.apache.log4j.Logger;

import java.io.File;

public class DeleteFiles {

    private static Logger log = Logger.getLogger("Log");

    public void delete(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            file.delete();
        } else {
            log.info("delete fail, file not exist");
        }
    }

    //根據設定檔來決定刪除 method
    public void deleteByType(File targetFile) {

        try {
            delete(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
