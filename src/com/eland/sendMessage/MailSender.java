package com.eland.sendMessage;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MailSender {
    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_PROTOCOL = "mail.transport.protocol";
    private static final String DEFAULT_FROM_MAIL = "default.from.mail";
    private static final String DEFAULT_FROM_NAME = "default.from.name";
    private static String defaultMailServer;
    private static String defaultMailProtocol;
    private static String defaultFromMail;
    private static String defaultFromName;
    private MimeMessage mimeMessage;
    private static String charset;

    static {
        FileProperties mailSendConfig = new FileProperties("mail.properties");
        defaultMailServer = mailSendConfig.getProperty(MAIL_HOST);
        defaultMailProtocol = mailSendConfig.getProperty(MAIL_PROTOCOL);
        defaultFromMail = mailSendConfig.getProperty(DEFAULT_FROM_MAIL);
        defaultFromName = mailSendConfig.getProperty(DEFAULT_FROM_NAME);
        charset = mailSendConfig.getProperty("charset");
    }

    /**
     *  寄送多封信件
     *
     * @param mailInfoList 信件集合
     */
    public void sendMails(List<MailInfo> mailInfoList) {
        for (MailInfo aMailInfo : mailInfoList) {
            sendMail(aMailInfo);
        }
    }

    /**
     * 寄信
     *
     * @param mailInfo 信件資訊物件
     * @return 寄信結果
     */
    public boolean sendMail(MailInfo mailInfo) {
        Map<String, String> toEmails;
        if (mailInfo == null || mailInfo.getToEmails().size() <= 0) {
            System.out.println("無收件人");
            return true;
        } else {
            toEmails = mailInfo.getToEmails();
        }

        Properties props = System.getProperties();

        boolean successful = true;
        try {
            //設定Mail 伺服器
            String mailHost = mailInfo.getMailServer();
            if (mailHost == null || mailHost.equals("")) {
                mailHost = defaultMailServer;
            }
            props.put(MAIL_HOST, mailHost);

            //設定傳送協定
            String mailProtocol = mailInfo.getMailProtocol();
            if (mailProtocol == null || mailProtocol.equals("")) {
                mailProtocol = defaultMailProtocol;
            }
            props.put(MAIL_PROTOCOL, mailProtocol);

            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);
            mimeMessage = new MimeMessage(mailSession);


            // 設定傳送郵件的發信人
            String fromMailAddress = mailInfo.getFromMailAddress();
            if (fromMailAddress == null || fromMailAddress.equals("")) {
                fromMailAddress = defaultFromMail;
            }

            String fromName = mailInfo.getFromName();
            if (fromName == null || fromName.equals("")) {
                fromName = defaultFromName;
            }
            mimeMessage.setFrom(new InternetAddress(fromMailAddress, fromName, charset));

            //設定收件人
            setRecipients(Message.RecipientType.TO, toEmails);

            //設定副本收件人
            setRecipients(Message.RecipientType.CC, mailInfo.getCcEmails());

            //設定密件副本收件人
            setRecipients(Message.RecipientType.BCC, mailInfo.getBccEmails());

            // 設定信件主旨
            mimeMessage.setSubject(mailInfo.getSubject(), charset);

            //設定信件內容、MIME Type和附件
            setContentAndAttachment(mailInfo);

            //設定發送時間
            setSendDate(mailInfo.getRunTime());

            // 送信
            Transport.send(mimeMessage);
        } catch (UnsupportedEncodingException uee) {
            successful = false;
            System.out.println("發信人名稱編碼不支援：" + charset);
            uee.printStackTrace();
        } catch (Exception e) {
            successful = false;
            System.out.println("寄信發生錯誤");
            e.printStackTrace();
        }
        return successful;
    }

    /**
     * 設定內文與附件
     *
     * @param mailInfo  郵件資訊
     */
    private void setContentAndAttachment(MailInfo mailInfo) {
        MimeMultipart mp = new MimeMultipart();
        MimeBodyPart mContent = new MimeBodyPart();
        try {
            mContent.setDataHandler(
                    new DataHandler(new ByteArrayDataSource(mailInfo.getContent(), "text/html; charset=" + charset)));
            mContent.setHeader("Content-Type", "text/html; charset=" + charset);
            mp.addBodyPart(mContent);
            setAttachment(mailInfo.getAttachmentFilePaths(), mp);
            mimeMessage.setContent(mp);
        } catch (IOException ioe) {
            System.out.println("內文處理異常");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("設定內文異常");
            e.printStackTrace();
        }
    }

    /**
     * 設定附件
     *
     * @param attachFilePaths 附件檔案路徑列表
     * @param mp 郵件內容
     */
    private void setAttachment(List<String> attachFilePaths, MimeMultipart mp) {
        if (attachFilePaths.size() > 0) {
            for (String anAttachFilePath : attachFilePaths) {
                File attachmentFile = new File(anAttachFilePath);
                if (attachmentFile.isFile()) {
                    MimeBodyPart mAttach = new MimeBodyPart();
                    try {
                        mAttach.setDataHandler(new DataHandler(new FileDataSource(attachmentFile)));
                        mAttach.setFileName(MimeUtility.encodeText(attachmentFile.getName(), charset, "B"));
                        mp.addBodyPart(mAttach);
                    } catch (UnsupportedEncodingException uee) {
                        System.out.println("附件檔案名稱編碼不支援：" + charset);
                        uee.printStackTrace();
                    } catch (Exception e) {
                        System.out.println("設定附件異常");
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * 設定寄送時間
     *
     * @throws java.text.ParseException
     * @throws InterruptedException
     * @throws MessagingException
     */
    private void setSendDate(String runTime) throws ParseException, InterruptedException, MessagingException {
        if (runTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd " + runTime);
            Date dateOfRun = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(sdf.format(new Date()));

            //設定寄信時間
            Date currentDate = new Date();
            long dateRange = dateOfRun.getTime() - currentDate.getTime();

            //檢查時間
            if (dateRange > 0) {
                //如果現在時間未到執行時間，就暫停到執行時間再執行
                Thread.sleep(dateRange);

                // 設定送信的時間
                mimeMessage.setSentDate(dateOfRun);
            } else {
                //如果現在時間已經過了執行時間，就直接執行
                // 設定送信的時間
                mimeMessage.setSentDate(currentDate);
            }
        }
    }

    /**
     * 設定收件人
     *
     * @param recipientType 收件人型態：一般、副本、密件副本
     * @param emails    email位置及名子
     */
    private void setRecipients(Message.RecipientType recipientType, Map<String, String> emails) {
        if (emails.size() > 0) {
            Iterator<String> emailAddresses = emails.keySet().iterator();
            InternetAddress[] internetAddresses = new InternetAddress[emails.size()];
            int count = 0;
            while (emailAddresses.hasNext()) {
                String aEmailAddresses = emailAddresses.next();
                try {
                    internetAddresses[count] = new InternetAddress(aEmailAddresses);
                    internetAddresses[count].setPersonal(emails.get(aEmailAddresses), charset);
                } catch (AddressException ae) {
                    System.out.println("郵件位置異常");
                    ae.printStackTrace();
                } catch (UnsupportedEncodingException uee) {
                    System.out.println("收件人名稱編碼不支援：" + charset);
                    uee.printStackTrace();
                } finally {
                    count++;
                }
            }
            try {
                mimeMessage.setRecipients(recipientType, internetAddresses);
            } catch (MessagingException me) {
                System.out.println("設定" + recipientType.toString() + "收件人異常：");
                me.printStackTrace();
            }
        }
    }
}
