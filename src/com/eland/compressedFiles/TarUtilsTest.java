package com.eland.compressedFiles;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.eland.compressedFiles.GZipUtilsTest.compressArchive;
import static com.eland.compressedFiles.TarDirectory.archive;
import static com.mongodb.util.MyAsserts.assertEquals;


public class TarUtilsTest { private String inputStr;

    public String testArchiveDir(String entry) throws Exception {

        String archive = archive(entry);//生成tar包
        //String path = compressArchive(archive);//生成gz包
        return archive;

    }
}
