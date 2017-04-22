package psd.email;

import java.io.File;
import java.util.Properties;

/**
 * Created by pengsida on 2017/4/8.
 */

public class MailReceiverInfo
{
    private String mailServerHost;
    private int mailServerPort = 110;
    private String protocal = "pop3";
    private String UserName;
    private String password;
    private String attachmentDir = "/Users/pengsida/email/";
    private String emailDir = "/Users/pengsida/email/";
    private String emailFileSuffix = ".eml";
    private boolean validate = true;

    public Properties getProperties()
    {
        Properties p = new Properties();
        if (this.protocal.equals("pop3"))
        {
            p.put("mail.pop3.host", this.mailServerHost);
            p.put("mail.pop3.port", this.mailServerPort);
            p.put("mail.pop3.auth", validate);
        }
        else if (this.protocal.equals("imap"))
        {
            p.put("mail.imap.host", this.mailServerHost);
            p.put("mail.imap.port", this.mailServerPort);
            p.put("mail.imap.auth", validate);
        }
        return p;
    }

    public String getProtocal()
    {
        return protocal;
    }

    public void setProtocal(String protocal)
    {
        this.protocal = protocal;
    }

    public String getAttachmentDir()
    {
        return attachmentDir;
    }

    public void setAttachmentDir(String attachmentDir)
    {
        this.attachmentDir = attachmentDir;
    }

    public String getEmailDir()
    {
        return emailDir;
    }

    public void setEmailDir(String emailDir)
    {
        if(!emailDir.endsWith(File.separator))
            emailDir = emailDir + File.separator;
        this.emailDir = emailDir;
    }

    public String getEmailFileSuffix()
    {
        return emailFileSuffix;
    }

    public void setEmailFileSuffix(String emailFileSuffix)
    {
        if(!emailFileSuffix.startsWith("."))
            emailFileSuffix = "." + emailFileSuffix;
        this.emailFileSuffix = emailFileSuffix;
    }

    public String getMailServerHost()
    {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost)
    {
        this.mailServerHost = mailServerHost;
    }

    public int getMailServerPort()
    {
        return mailServerPort;
    }

    public void setMailServerPort(int mailServerPort)
    {
        this.mailServerPort = mailServerPort;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String userName)
    {
        this.UserName = userName;
    }

    public boolean isValidate()
    {
        return validate;
    }

    public void setValidate(boolean validate)
    {
        this.validate = validate;
    }
}
