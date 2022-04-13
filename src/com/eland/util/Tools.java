package com.eland.util;

import java.io.File;

public class Tools {
    /**
     * 遞迴刪除檔案或資料夾
     *
     * @param file  檔案或資料夾
     */
    public void delFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()){
            file.delete();
        } else if (file.isDirectory()) {
            for (File f: file.listFiles()){
                delFile(f);
            }
            file.delete();
        }
    }
    /**
     * 重置時間
     */
    public long[] resetTime(long totalTime[]){
        for(int i = 0; i < totalTime.length; i++) {
            totalTime[i] = 0;
        }
        return totalTime;
    }

}
