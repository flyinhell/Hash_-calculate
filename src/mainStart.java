

import java.io.File;


import com.eland.compressedFiles.*;
import com.eland.entities.IndexHashInformationEntity;
import com.eland.hashtest.CheckSum;
import com.eland.model.ImportConfig;
import com.eland.util.*;
import com.eland.tools.*;
import org.apache.log4j.Logger;


public class mainStart {
    private static Logger logger = Logger.getLogger(mainStart.class);


    public static void main(String[] args) {

        long[] time = new long[5];      //時間紀錄
        long[] totalTime = new long[5]; //合計時間紀錄
        Tools tools = new Tools();
        CheckSum checkSum = new CheckSum();
        IndexHashInformationEntity hashInformationEntity =new IndexHashInformationEntity();


        try {
            ImportConfig config = ImportConfig.Config(args[0]);
            int intTestingNum = Integer.valueOf(config.testingNum);
          //  ConnectDB connectDB = new ConnectDB(config.d_driverName,config.d_sourceName,config.d_account,config.d_password);

            //測試Hash
            if (config.indexHashSwitch.equalsIgnoreCase("on")) {
                totalTime = tools.resetTime(totalTime);

                    totalTime = tools.resetTime(totalTime);

                    for (int i = 1; i <= intTestingNum; i++) {
                        logger.info("測試Md5第" + i + "次");
                        time[0] = System.currentTimeMillis();

                        checkSum.checkSumMD5Guava(config.filePath, true,config);

                        time[1] = System.currentTimeMillis();

                        totalTime[0] = totalTime[0] + ((time[1] - time[0]) / 1000);
                        logger.info("測試第" + i + "次" + "MD5耗時:" + (time[1] - time[0]) / 1000 + "秒");
                    }
                    logger.info("測試MD5" + config.testingNum + "次" + "," + "平均耗時:" + totalTime[0] / intTestingNum + "秒");
                    logger.info("-------------------------------");
            }


            //MD5 Compare
            if (config.checkFileSwitch.equalsIgnoreCase("on")) {
                FileCheck fileCheck = new FileCheck();
                boolean booleanFileCheck;
                booleanFileCheck = fileCheck.isSameFile(config.hashTxtPathBefore, config.hashTxtPathAfter);
                if (booleanFileCheck) {
                    System.out.println("文件相同");
                } else {
                    System.out.println("文件不同");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }
}
