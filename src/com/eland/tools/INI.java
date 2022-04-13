package com.eland.tools;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Hashtable;

public class INI {
    private static Logger log = Logger.getLogger("Log");

    public INI() {
        filename = "";
    }

    public INI(String s) {
        filename = s;
    }

    public Hashtable load() {
        Hashtable hashtable = new Hashtable();
        File file = new File(filename);
        if (file.exists())
            try {
                FileInputStream fileinputstream = new FileInputStream(file);
                hashtable = load(((InputStream) (fileinputstream)));
                fileinputstream.close();
            } catch (IOException _ex) {
                _ex.printStackTrace();
                log.error(_ex.getMessage());
            }
        return hashtable;
    }

    public Hashtable load(InputStream inputstream) {
        Hashtable hashtable = new Hashtable();
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            String s;
            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();
                if (!s.startsWith("#") && !s.startsWith("//")) {
                    int i = s.indexOf('=');
                    if (i != -1)
                        try {
                            String s1 = s.substring(0, i).trim();
                            String s2 = "";
                            if (i + 1 < s.length())
                                s2 = s.substring(i + 1, s.length()).trim();
                            hashtable.put(s1, s2);
                        } catch (StringIndexOutOfBoundsException _ex) {
                            _ex.printStackTrace();
//                            log.error(_ex.getMessage());
                        } catch (NullPointerException _ex) {
                            _ex.printStackTrace();
//                            log.error(_ex.getMessage());
                        }
                }
            }
        } catch (IOException _ex) {
            _ex.printStackTrace();
            log.error(_ex.getMessage());
        }
        return hashtable;
    }


    private String filename;
}
