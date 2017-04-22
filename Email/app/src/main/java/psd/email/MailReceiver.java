package psd.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by pengsida on 2017/4/8.
 */

public class MailReceiver
{
    private MailReceiverInfo receiverInfo;
    private Store store;
    private Folder folder;

    public MailReceiver(MailReceiverInfo receiverInfo)
    {
        this.receiverInfo = receiverInfo;
    }

    public void receiveNewestMail()
    {
        try
        {
            this.connectToServer();
            this.openInBoxFolder();
            this.getNewestMail();
            this.closeConnection();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    private void getNewestMail() throws MessagingException
    {
        Message[] messages = this.folder.getMessages();

        if (messages.length == 0)
        {
            throw new MessagingException("邮箱里没有邮件");
        }

        Message message = messages[0];
        Date date = ((MimeMessage)message).getSentDate();
        for(int index = 1; index < messages.length; ++index)
        {
            Date temp = ((MimeMessage)messages[index]).getSentDate();
            if (temp == null)
                continue;

            if (date.before(temp))
            {
                message = messages[index];
                date = temp;
            }
        }

        try
        {
            MessageResolver messageResolver = new MessageResolver(message);
            messageResolver.showMailBasicInfo();
            messageResolver.saveMessageAsFile(receiverInfo.getEmailDir());
            messageResolver.saveEveryPartOfMessage(receiverInfo.getEmailDir());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Message[] receiveAllMail()
    {
        try {
            Message[] messages = this.folder.getMessages();
            return messages;
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void saveAllMail() throws MessagingException
    {
        Message[] messages = this.folder.getMessages();
        System.out.println("总的邮件数目： " + messages.length);
        System.out.println("新邮件数目： " + this.folder.getNewMessageCount());
        System.out.println("未读邮件数目： " + this.folder.getUnreadMessageCount());

        int mailArrayLength = this.folder.getMessageCount();
        System.out.println("一共有邮件" + mailArrayLength + "封");
        int errorCounter = 0;
        int successCounter = 0;

        for (int index = 0; index < messages.length; ++index)
        {
            try {
                MessageResolver messageResolver = new MessageResolver(messages[index]);
                System.out.println("正在获取第" + index + "封邮件");
                messageResolver.showMailBasicInfo();
                messageResolver.saveMessageAsFile(this.receiverInfo.getEmailDir());
                System.out.println("成功获取第" + index + "封邮件");
                ++successCounter;
            }
            catch (Throwable e)
            {
                ++errorCounter;
                System.out.println("下载第" + index + "封邮件时出错");
                e.printStackTrace();
            }
        }
        System.out.println("------------------");
        System.out.println("成功下载了" + successCounter + "封邮件");
        System.out.println("失败下载了" + errorCounter + "封邮件");
        System.out.println("------------------");
    }

    public void openInBoxFolder() throws MessagingException
    {
        if(this.receiverInfo == null)
        {
            throw new MessagingException("必须提供接收邮件的参数！");
        }

        try {
            this.folder = this.store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
        }
        catch (MessagingException e)
        {
            throw new MessagingException("打开收件箱失败！");
        }
    }

    public void connectToServer() throws MessagingException
    {
        MyAuthenticator authenticator = null;
        if(this.receiverInfo.isValidate())
            authenticator = new MyAuthenticator(this.receiverInfo.getUserName(), this.receiverInfo.getPassword());

        Session session = Session.getInstance(this.receiverInfo.getProperties(), authenticator);

        try
        {
            this.store = session.getStore(this.receiverInfo.getProtocal());
        }
        catch (NoSuchProviderException e)
        {
            e.printStackTrace();
            throw new MessagingException("连接服务器失败！");
        }

        System.out.println("connecting");

        try
        {
            this.store.connect();
        }
        catch (MessagingException e)
        {
            throw new MessagingException("连接服务器失败！");
        }

        System.out.println("连接服务器成功");
    }

    public void closeConnection() throws MessagingException
    {
        try {
            if(this.folder.isOpen())
                this.folder.close(true);
            this.store.close();
            System.out.println("成功关闭与邮件服务器的连接！");
        }
        catch (Exception e)
        {
            System.out.println("关闭和邮件服务器之间的连接时出错！");
            throw new MessagingException("关闭和邮件服务器之间的连接时出错！");
        }
    }

    public static void main(String[] args) throws Exception
    {
        MailReceiverInfo receiverInfo = new MailReceiverInfo();
        receiverInfo.setMailServerHost("pop3.zju.edu.cn");
//        receiverInfo.setMailServerHost("imap.zju.edu.cn");
//        receiverInfo.setProtocal("imap");
//        receiverInfo.setMailServerPort(143);
        receiverInfo.setUserName("pengsida@zju.edu.cn");
        receiverInfo.setPassword("zdpsd19961201");
        MailReceiver receiver = new MailReceiver(receiverInfo);
        receiver.receiveNewestMail();
    }
}
