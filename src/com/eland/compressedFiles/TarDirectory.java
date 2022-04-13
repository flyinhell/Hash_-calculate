package com.eland.compressedFiles;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class TarDirectory {

    public static void AddFileToTarGz(TarArchiveOutputStream tOut, File f, String base) throws IOException {
        String entryName = base + f.getName();
        System.out.printf("\tAdd entry...%s\n", entryName);
        TarArchiveEntry tarEntry = new TarArchiveEntry(f, entryName);
        tOut.putArchiveEntry(tarEntry);

        if (f.isFile()) {
            IOUtils.copy(new FileInputStream(f), tOut);
            tOut.closeArchiveEntry();
        } else {
            tOut.closeArchiveEntry();
            File[] children = f.listFiles();
            if (children != null) {
                for (File child : children) {
                    AddFileToTarGz(tOut, child, entryName + "/");
                }
            }
        }


    }

    public static String archive(String entry) throws IOException {
        File file = new File(entry);

        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file.getAbsolutePath() + ".tar"));
        String base = file.getName();

        if(file.isDirectory()){
            archiveDir(file, tos, base);
        }else{
            archiveHandle(tos, file, base);

        }

        tos.close();
        return file.getAbsolutePath() + ".tar";
    }

    /**
     * 遞迴處理，準備好路徑
     * @param file
     * @param tos
     * @param base
     */
    public static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws IOException {
        File[] listFiles = file.listFiles();
        for(File fi : listFiles){
            if(fi.isDirectory()){
                archiveDir(fi, tos, basePath + File.separator + fi.getName());
            }else{
                archiveHandle(tos, fi, basePath);
                System.out.println(basePath);
            }
        }
    }

    /**
     * 具體歸檔處理（文件）
     * @param tos
     * @param fi
     * @param base
     */
    public static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws IOException {
        TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());

        tEntry.setSize(fi.length());
        //嘗試
        tos.setBigNumberMode(tos.BIGNUMBER_STAR);
        tos.putArchiveEntry(tEntry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = bis.read(buffer)) != -1){
            tos.write(buffer, 0 , read);
        }
        bis.close();
        tos.closeArchiveEntry();
    }
    /**
     * 解壓tar
     * @param finalName
     */
    public static void unCompressTar(String finalName) throws IOException {

        File file = new File(finalName);
        String parentPath = file.getParent();
        TarArchiveInputStream tais =
                new TarArchiveInputStream(new FileInputStream(file));

        TarArchiveEntry tarArchiveEntry = null;

        while((tarArchiveEntry = tais.getNextTarEntry()) != null){
            String name = tarArchiveEntry.getName();
            File tarFile = new File(parentPath, name);
            if(!tarFile.getParentFile().exists()){
                tarFile.getParentFile().mkdirs();
            }

            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(tarFile));

            int read = -1;
            byte[] buffer = new byte[1024];
            while((read = tais.read(buffer)) != -1){
                bos.write(buffer, 0, read);
            }
            bos.close();
        }
        tais.close();
        file.delete();//删除tar文件
    }


}