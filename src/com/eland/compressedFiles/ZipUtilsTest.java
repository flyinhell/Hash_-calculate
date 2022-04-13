package com.eland.compressedFiles;

import java.io.File;

public class ZipUtilsTest {
    /**
     * @throws Exception
     */
    public void zipUtilstest(String FileName) {
        try {
            File file = new File(FileName);
            if (!file.exists()) {
                System.out.println("此路徑無效:"+FileName);
                return;
            }

            if(file.isFile()){

                ZipUtils.compress(FileName);
            }else if (file.isDirectory()){

                ZipUtils.compress(FileName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
