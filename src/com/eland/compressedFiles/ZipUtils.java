package com.eland.compressedFiles;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static final String EXT = ".zip";
    private static final String BASE_DIR = "";

    // 符號"/"用来作為目錄標示判斷符號
    private static final String PATH = "/";
    private static final int BUFFER = 1024;

    /**
     * 壓縮
     *
     * @param srcFile
     * @throws Exception
     */
    public static void compress(File srcFile) throws Exception {
        String name = srcFile.getName();
        String basePath = srcFile.getParent();
        System.out.println();
        String destPath = basePath + name + EXT;
        compress(srcFile, destPath);
    }

    /**
     * 壓縮
     *
     * @param srcFile  來源路徑
     * @param destPath 目標路徑
     * @throws Exception
     */
    public static void compress(File srcFile, File destFile) throws Exception {

        // 對輸出文件做CRC32檢驗
        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                destFile), new CRC32());

        ZipOutputStream zos = new ZipOutputStream(cos);

        compress(srcFile, zos, BASE_DIR);

        zos.flush();
        zos.close();
    }

    /**
     * 壓縮文件
     *
     * @param srcFile
     * @param destPath
     * @throws Exception
     */
    public static void compress(File srcFile, String destPath) throws Exception {
        compress(srcFile, new File(destPath));
    }

    /**
     * 壓縮
     *
     * @param srcFile  來源路徑
     * @param zos      ZipOutputStream
     * @param basePath 壓縮包内相對路徑
     * @throws Exception
     */
    private static void compress(File srcFile, ZipOutputStream zos,
                                 String basePath) throws Exception {
        if (srcFile.isDirectory()) {
            compressDir(srcFile, zos, basePath);
        } else {
            compressFile(srcFile, zos, basePath);
        }
    }

    /**
     * 壓縮
     *
     * @param srcPath
     * @throws Exception
     */
    public static void compress(String srcPath) throws Exception {
        File srcFile = new File(srcPath);

        compress(srcFile);
    }

    /**
     * 文件壓縮
     *
     * @param srcPath  來源文件路徑
     * @param destPath 目標文件路徑
     */
    public static void compress(String srcPath, String destPath)
            throws Exception {
        File srcFile = new File(srcPath);

        compress(srcFile, destPath);
    }

    /**
     * 壓縮目錄
     *
     * @param dir
     * @param zos
     * @param basePath
     * @throws Exception
     */
    private static void compressDir(File dir, ZipOutputStream zos,
                                    String basePath) throws Exception {

        File[] files = dir.listFiles();

        // 建立空目錄
        if (files.length < 1) {
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);

            zos.putNextEntry(entry);
            zos.closeEntry();
        }

        for (File file : files) {

            // 遞迴壓縮
            compress(file, zos, basePath + dir.getName() + PATH);

        }
    }

    /**
     * 文件壓縮
     *
     * @param file 待壓縮文件
     * @param zos  ZipOutputStream
     * @param dir  壓縮文件中的當前路徑
     * @throws Exception
     */
    private static void compressFile(File file, ZipOutputStream zos, String dir)
            throws Exception {

        /**
         * 壓縮包内文件名定義
         *
         * <pre>
         * 如果有多級目錄，那這裡就需要给出包含目錄的文件名
         * 如果用WinRAR打开壓縮包，中文名将顯示為亂碼
         * </pre>
         */
        ZipEntry entry = new ZipEntry(dir + file.getName());

        zos.putNextEntry(entry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                file));

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = bis.read(data, 0, BUFFER)) != -1) {
            zos.write(data, 0, count);
        }
        bis.close();

        zos.closeEntry();
    }


}
