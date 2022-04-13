package com.eland.model;

import com.eland.tools.INI;
import org.apache.log4j.Logger;

import java.util.*;

public class ImportConfig {
    private static Logger log = Logger.getLogger("Log");
    public List<String> p_pathList = new ArrayList<String>();
    public HashMap<String, String> p_pathTable = new HashMap<String, String>();
    public String s_pathSymbol, s_pathHeadSymbol;
    public String d_driverName, d_sourceName, d_account, d_password;
    public String iniPath;         //ini檔路徑
    public String indexHashSwitch; //Hash功能
    public String checkFileSwitch; //文件比較功能
    public String certutilSwitch;  //採用windows元件產生md5
    public String zipUtilsSwitch;  //壓縮功能
    public String tarSwitch;

    public String filePath;        //測試檔案路徑
    public String copyFilePath;    //複製後檔案路徑
    public String testingNum;      //測試次數

    public String hashTxtPathBefore, hashTxtPathAfter;  //MD5產生檔案路徑
    public String tarTestFile;

    public String insertSearchRecord,selectSearchRecordKsn,selectSearchRecordSrn;

    public static ImportConfig Config(String cfile) {
        ImportConfig config = null;
        try {
            config = new ImportConfig();
            Hashtable sys = new INI(cfile).load();


            //[System]
            config.iniPath = (String) sys.get("TspsdkIni");
            config.filePath = (String) sys.get("FilePath");
            config.copyFilePath = (String) sys.get("CopyFilePath");
            config.hashTxtPathAfter = (String) sys.get("HashTxtPathAfter");
            config.hashTxtPathBefore = (String) sys.get("HashTxtPathBefore");
            config.testingNum = (String) sys.get("TestingNum");

            //[DB]
            config.d_driverName = (String) sys.get("DBDriverName");
            config.d_sourceName = (String) sys.get("DBSourceName");
            config.d_account = (String) sys.get("DBAccount");
            config.d_password = (String) sys.get("DBPassword");

            //[Switch]
            config.indexHashSwitch = (String) sys.get("IndexHashSwitch");
            config.checkFileSwitch = (String) sys.get("CheckFileSwitch");
            config.certutilSwitch = (String) sys.get("CertutilSwitch");
            config.zipUtilsSwitch = (String) sys.get("ZipUtilsSwitch");
            config.tarSwitch = (String) sys.get("TarSwitch");


            //[Tar]
            config.tarTestFile = (String) sys.get("TarTestFile");

            //索引庫至路徑符號
            config.s_pathSymbol = (String) sys.get("PathSymbol");

            //[SQL]
            config.insertSearchRecord= (String) sys.get("InsertSearchRecord");
            config.selectSearchRecordKsn= (String) sys.get("SelectSearchRecordKsn");
            config.selectSearchRecordSrn= (String) sys.get("SelectSearchRecordSrn");

            //INI Info
            for (Object o : sys.keySet()) {
                //取得目標路徑list
                if (o.toString().matches("Target.Path\\d\\.[\\w-].*")) {
                    String path = (String) sys.get(o.toString());
                    String namelist[] = o.toString().split("\\.");//目標機器名稱
                    config.p_pathList.add((String) sys.get(o.toString()));//(String) sys.get(o.toString()) = path
                    config.p_pathTable.put(namelist[2], path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            config = null;
            log.error(e.getMessage());
        }
        return config;
    }

}
