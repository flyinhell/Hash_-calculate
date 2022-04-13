package com.eland.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileCheck {

    /**
     *
     * @param fileName1
     * @param fileName2
     * @return
     */
    public boolean isSameFile(String fileName1,String fileName2){
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(fileName1);
            fis2 = new FileInputStream(fileName2);
            int len1 = fis1.available();
            int len2 = fis2.available();

            if (len1 == len2) {
                byte[] data1 = new byte[len1];
                byte[] data2 = new byte[len2];

                fis1.read(data1);

                fis2.read(data2);

                for (int i=0; i<len1; i++) {
                    if (data1[i] != data2[i]) {
                       // System.out.println("文件内容不同");
                        return false;
                    }
                }
                //System.out.println("文件相同");
                return true;
            } else {
                //長度不同，文件必定不同
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


}
