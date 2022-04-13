package com.eland.compressedFiles;



import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.*;

import static com.mongodb.util.MyAsserts.assertEquals;


public class GZipUtilsTest {
    private String inputStr = "zlex@zlex.org,snowolf@zlex.org,zlex.snowolf@zlex.org";
    public static final String EXT = ".tar.gz";
    public final void testDataCompress() throws Exception {

        System.err.println("原文:\t" + inputStr);

        byte[] input = inputStr.getBytes();
        System.err.println("長度:\t" + input.length);

        byte[] data = GZipUtils.compress(input);
        System.err.println("壓縮後:\t");
        System.err.println("長度:\t" + data.length);

        byte[] output = GZipUtils.decompress(data);
        String outputStr = new String(output);
        System.err.println("解壓縮後:\t" + outputStr);
        System.err.println("長度:\t" + output.length);

        assertEquals(inputStr, outputStr);

    }
    public final void testFileCompress(String FileName) throws Exception {

        FileOutputStream fos = new FileOutputStream(FileName);

        fos.write(inputStr.getBytes());
        fos.flush();
        fos.close();

        GZipUtils.compress(FileName, false);
        GZipUtils.decompress(FileName+".gz", false);
        File file = new File(FileName);
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        byte[] data = new byte[(int) file.length()];
        dis.readFully(data);
        fis.close();

        String outputStr = new String(data);
        assertEquals(inputStr, outputStr);
    }
    public void gipUtilstest(String FileName) {
        try {
            File file = new File(FileName);
            if (!file.exists()) {
                System.out.println("此路徑無效:"+FileName);
                return;
            }

            if(file.isFile()){

                GZipUtils.compress(FileName);
            }else if (file.isDirectory()){

                GZipUtils.compress(FileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tarAndGzipTest(String FileName) throws Exception{

        File dir = new File(FileName);
        String name = dir.getName();
        String basePath = dir.getParent();
        String destPath = basePath + name + EXT;
        File outFile = new File(destPath);

        FileOutputStream fOut=null;
        BufferedOutputStream bOut=null;
        GzipCompressorOutputStream gzOut=null;
        TarArchiveOutputStream tOut=null;
        try{
            fOut = new FileOutputStream(outFile);
            bOut = new BufferedOutputStream(fOut);
            gzOut = new GzipCompressorOutputStream(bOut);
            tOut = new TarArchiveOutputStream(gzOut);
            TarDirectory.AddFileToTarGz(tOut, dir , "");
        } finally {
            if(tOut!=null) {
                tOut.finish();
                tOut.close();
                gzOut.close();
                bOut.close();
                fOut.close();
            }
        }
    }
    /**
     * 把tar包壓縮成gz
     * @param path
     * @throws IOException
     */
    public static String compressArchive(String path) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

        GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(path + ".gz")));

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = bis.read(buffer)) != -1){
            gcos.write(buffer, 0, read);
        }
        gcos.close();
        bis.close();
        return path + ".gz";
    }
    /**
     * 解压
     * @param archive
     * @author yutao
     * @throws IOException
     */
    private static void unCompressArchiveGz(String archive) throws IOException {

        File file = new File(archive);

        BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(file));

        String fileName =
                file.getName().substring(0, file.getName().lastIndexOf("."));

        String finalName = file.getParent() + File.separator + fileName;

        BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(finalName));

        GzipCompressorInputStream gcis =
                new GzipCompressorInputStream(bis);

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = gcis.read(buffer)) != -1){
            bos.write(buffer, 0, read);
        }
        gcis.close();
        bos.close();
        TarDirectory.unCompressTar(finalName);

    }


}

