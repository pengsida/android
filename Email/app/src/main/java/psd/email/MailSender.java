package psd.email;

import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by pengsida on 2017/4/4.
 */


public class MailSender
{
    public static boolean sendTextMail(MailSenderInfo mailInfo)
    {
        MyAuthenticator authenticator = null;
        if(mailInfo.isValidate())
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());

        Session sendMailSession = Session.getInstance(mailInfo.getProperties(), authenticator);

        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);

            Transport.send(mailMessage);
            return true;
        }
        catch (MessagingException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args)
    {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.zju.edu.cn");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("pengsida@zju.edu.cn");
        mailInfo.setPassword("***");
        mailInfo.setFromAddress("pengsida@zju.edu.cn");
        mailInfo.setToAddress("291277604@qq.com");
        mailInfo.setSubject("Hello world");
        mailInfo.setContent("Psd, Hello world");
        sendTextMail(mailInfo);
    }
}
