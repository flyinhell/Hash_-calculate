package com.eland.tools;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
public class ConnectDB {

    private String driverName, sourceName, account, password;
    private static Logger log = Logger.getLogger("Log");

    public ConnectDB(String driverName, String sourceName, String account, String password) {
        try {
            this.driverName = driverName;
            this.sourceName = sourceName;
            this.account = account;
            this.password = password;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public Connection loginConnect() {
        Driver driver = null;
        Connection con = null;
        try {
            driver = (Driver) Class.forName(this.driverName).newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(this.sourceName, this.account, this.password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return con;
    }

}
