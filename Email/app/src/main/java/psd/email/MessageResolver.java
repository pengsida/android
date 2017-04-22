package psd.email;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by pengsida on 2017/4/9.
 */

public class MessageResolver
{
    private Message message;
    private String dirName;

    public MessageResolver(Message message)
    {
        this.message = message;
    }

    public void showMailBasicInfo() throws Exception
    {
        System.out.println("-------邮件ID: " + this.getMessageId() + "----------");
        System.out.println("From: " + this.getFrom());
        System.out.println("To: " + this.getToAddress());
        System.out.println("CC: " + this.getCCAddress());
        System.out.println("BCC: " + this.getBCCAddress());
        System.out.println("Subject: " + this.getSubject());
        System.out.println("发送时间: " + this.getSentDate());
        System.out.println("是新邮件？ " + this.isNew());
        System.out.println("要求回执？" + this.getReplySign());
        System.out.println("包含附件？" + this.isContainAttach());
        System.out.println("----------------------------------");
    }

    public String getContentType() throws MessagingException
    {
        return message.getContentType();
    }

    public String getHtmlContent(String dirName) throws IOException, MessagingException
    {
        this.dirName = dirName;
        return getContent("HTML");
    }

    public String getPlainContent() throws IOException, MessagingException
    {
        return getContent("PLAIN");
    }

    public String getContent(String wantedContentType) throws IOException, MessagingException
    {
        return getContent(message, wantedContentType);
    }

    public String getContent(Part part, String wantedContentType) throws IOException, MessagingException
    {
        String disposition = part.getDisposition();
        String contentType = part.getContentType();

        if (disposition != null && wantedContentType.equals("HTML"))
        {
            String filename = getAttachFileName(dirName, part);
            filename = dirName + "/test.jpg";
            saveFile(part, filename);
            return "";
        }


        if (contentType.contains("multipart"))
        {
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart mp = new MimeMultipart(source);

            String all = "";

            for (int index = 0; index < mp.getCount(); ++index)
                all = all + getContent(mp.getBodyPart(index), wantedContentType);

            return all;
        }
        else
        {
            if (contentType.contains("text/plain"))
            {
                String content = part.getContent().toString();
                if (wantedContentType.equals("PLAIN"))
                    return content;
                else
                    return "";
            }
            else if(contentType.contains("text/html"))
            {
                if (wantedContentType.equals("HTML"))
                    return part.getContent().toString();
                else
                    return "";
            }
        }

        return "";
    }

    public void saveMessageAsFile(String dirName)
    {
        try
        {
            String filename = getFileName(dirName, ".eml");
            System.out.println("邮件消息的存储路径:" + filename);
            saveEmailFile(message, filename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveDisposition(String dirName) throws IOException, MessagingException
    {
        saveDisposition(dirName, this.message);
    }

    private void saveDisposition(String dirName, Part part) throws IOException, MessagingException
    {
        String disposition = part.getDisposition();
        String contentType = part.getContentType();

        if (disposition != null) {
            String filename = getAttachFileName(dirName, part);
            filename = dirName + "/test.jpg";
            saveFile(part, filename);
            return;
        }

        if(contentType.contains("multipart"))
        {
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart mp = new MimeMultipart(source);

            for (int index = 0; index < mp.getCount(); ++index)
            {
                Log.d("SAVE", "saveImage");
                saveDisposition(dirName, mp.getBodyPart(index));
            }
        }
    }

    public void saveEveryPartOfMessage(String dirName) throws IOException, MessagingException
    {
        saveEveryPartOfMessage(dirName, this.message);
    }

    private void saveEveryPartOfMessage(String dirName, Part part) throws IOException, MessagingException
    {
        String disposition = part.getDisposition();
        String contentType = part.getContentType();

        if (disposition != null)
        {
            String filename = getFileName(dirName, part);
            System.out.println("邮件附件的存储路径:" + filename);
            saveFile(part, filename);
            return;
        }


        if (contentType.contains("multipart"))
        {
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart mp = new MimeMultipart(source);

            for (int index = 0; index < mp.getCount(); ++index)
                saveEveryPartOfMessage(dirName, mp.getBodyPart(index));
        }
        else
        {
            if (contentType.contains("text/plain"))
            {
                String filename = getFileName(dirName, part);
                System.out.println("邮件txt文件的存储路径:" + filename);
                saveFile(part, filename);
            }
            else if(contentType.contains("text/html"))
            {
                String filename = getFileName(dirName, part);
                System.out.println("邮件html文件的存储路径:" + filename);
                saveFile(part, filename);
            }
        }
    }

    private String getFileName(String dirName, Part part) throws MessagingException
    {
        String filename = getAttachFileName(dirName, part);
        if(filename.equals(dirName))
        {
            String contentType = part.getContentType();
            if (contentType.contains("text/plain"))
                return getFileName(dirName, ".txt");

            if (contentType.contains("text/html"))
                return getFileName(dirName, ".html");

            if (contentType.contains("image/gif"))
                return getFileName(dirName, ".gif");
        }
        return filename;
    }

    private String getAttachFileName(String dirName, Part part) throws MessagingException
    {
        String fileName = part.getFileName();

        if (fileName == null)
            return dirName;

        try
        {
            fileName = MimeUtility.decodeText(fileName);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String name = fileName;
        int index = fileName.lastIndexOf("/");
        if(index != -1)
            name = fileName.substring(index + 1);
        return (dirName + name);
    }

    private String getFileName(String dirName, String suffix) throws MessagingException
    {
        String oriFileName = getInfoBetweenBrackets(this.getMessageId());
        return (dirName + oriFileName + suffix);
    }

    private void saveFile(Part part, String filename) throws IOException, MessagingException
    {
        InputStream in = part.getInputStream();
        OutputStream os = new FileOutputStream(filename);
        byte[] bytes = new byte[1024];
        int len = 0;

        while ((len=in.read(bytes)) != -1)
            os.write(bytes, 0, len);

        os.close();
        in.close();
    }

    private void saveEmailFile(Part part, String filename) throws IOException, MessagingException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        part.writeTo(baos);
        StringReader in = new StringReader(baos.toString());

        File file = new File(filename);

        FileWriter fos = new FileWriter(file);
        BufferedWriter bos = new BufferedWriter(fos);
        BufferedReader bis = new BufferedReader(in);

        int len;
        while((len = bis.read()) != -1)
            bos.write(len);

        bos.flush();
        bos.close();
        bis.close();
    }

    private String getInfoBetweenBrackets(String str)
    {
        int i, j;
        if (str == null)
        {
            str = "error";
            return str;
        }
        i = str.lastIndexOf("<");
        j = str.lastIndexOf(">");
        if(i != -1 && j != -1)
            str = str.substring(i+1, j);

        return str;
    }

    public String getMessageId() throws MessagingException
    {
        return ((MimeMessage)this.message).getMessageID();
    }

    public String getFrom() throws MessagingException
    {
        InternetAddress[] addresses = (InternetAddress[])this.message.getFrom();
        String from = addresses[0].getAddress();
        if(from == null)
            from = "";

        String personal = addresses[0].getPersonal();
        if(personal == null)
        {
            return from;
        }

        return personal + "<" + from + ">";
    }

    private String getToAddress() throws Exception
    {
        return getMailAddress("TO");
    }

    private String getCCAddress() throws Exception
    {
        return getMailAddress("CC");
    }

    private String getBCCAddress() throws Exception
    {
        return getMailAddress("BCC");
    }

    private String getMailAddress(String type) throws Exception
    {
        String mailaddr = "";
        String addtype = type.toUpperCase();
        InternetAddress[] addresses;
        if(addtype.equals("TO") || addtype.equals("CC") || addtype.equals("TO"))
        {
            if(addtype.equals("TO"))
                addresses = (InternetAddress[])this.message.getRecipients(Message.RecipientType.TO);
            else if(addtype.equals("CC"))
                addresses = (InternetAddress[])this.message.getRecipients(Message.RecipientType.CC);
            else if(addtype.equals("BCC"))
                addresses = (InternetAddress[])this.message.getRecipients(Message.RecipientType.BCC);
            else
                throw new Exception("错误的地址类型！！");

            if (addresses != null)
            {
                for (int i = 0; i < addresses.length; ++i)
                {
                    String email = addresses[i].getAddress();
                    if(email == null)
                        email = "";
                    else
                        email = MimeUtility.decodeText(email);

                    String personal = addresses[i].getPersonal();
                    if(personal == null)
                        personal = "";
                    else
                        personal = MimeUtility.decodeText(personal);

                    String compositeTo = personal + "<" + email + ">";
                    mailaddr = mailaddr + ", " + compositeTo;
                }
                mailaddr = mailaddr.substring(2);
            }

        }
        return mailaddr;
    }

    public String getSubject() throws MessagingException
    {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(this.message.getSubject());
            if(subject == null)
                subject = "";
        }
        catch (Exception exce)
        {
            subject = "";
        }

        return subject;
    }

    public Date getSentDate() throws MessagingException
    {
        return this.message.getSentDate();
    }

    private boolean isNew() throws MessagingException
    {
        boolean isnew = false;
        Flags flags = this.message.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for(int i = 0; i < flag.length; ++i)
        {
            if(flag[i] == Flags.Flag.SEEN)
            {
                isnew = true;
                break;
            }
        }
        return isnew;
    }

    private boolean getReplySign() throws Exception
    {
        boolean replysign = false;
        String needreply[] = this.message.getHeader("Disposition-Notification-To");
        if (needreply != null)
            replysign = true;
        return replysign;
    }

    private boolean isContainAttach() throws Exception
    {
        return isContainAttach(this.message);
    }

    private boolean isContainAttach(Part part) throws Exception
    {
        boolean attachflag = false;
        if(part.isMimeType("multipart/*"))
        {
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart mp = new MimeMultipart(source);
            for (int i = 0; i < mp.getCount(); ++i)
            {
                BodyPart bodyPart = mp.getBodyPart(i);
                String disposition = bodyPart.getDisposition();
                if((disposition != null) && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE)))
                    attachflag = true;
                else if(bodyPart.isMimeType("multipart/*"))
                    attachflag = isContainAttach((Part)bodyPart);
                else
                {
                    String contype = bodyPart.getContentType();
                    if(contype.toLowerCase().contains("application"))
                        attachflag = true;
                    if(contype.toLowerCase().contains("name"))
                        attachflag = true;
                }
            }
        }
        else if(part.isMimeType("message/rfc822"))
            attachflag = isContainAttach((Part)part.getContent());

        return attachflag;
    }
}
