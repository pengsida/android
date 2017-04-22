package psd.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by pengsida on 2017/4/6.
 */

public class HelloWorld {
    private static void sendTextMail()
    {
        Session session = Session.getDefaultInstance(new Properties());

        try {
//            Store store = session.getStore("imap");
//            Transport store = session.getTransport("smtp");
//            store.connect("imap.zju.edu.cn", 110, "pengsida@zju.edu.cn", "***");
//            store.connect("smtp.qq.com", 587, "291277604@qq.com", "***");
//            store.connect("imap.qq.com", 143, "291277604@qq.com", "***");
            Store store = session.getStore("pop3");
            store.connect("pop.qq.com", 995, "291277604@qq.com", "***");
            System.out.printf("right");
        }
        catch (AuthenticationFailedException ea)
        {
            System.out.printf("authenticate failed");
        }
        catch (MessagingException em)
        {
            System.out.printf("wrong");
        }
    }

    public static void main(String[] args)
    {
        sendTextMail();
    }
}
