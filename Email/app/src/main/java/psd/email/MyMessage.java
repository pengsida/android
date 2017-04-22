package psd.email;

import android.content.Context;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Created by pengsida on 2017/4/17.
 */

public class MyMessage
{
    private static MyMessage myMessage;
    private Message message;
    private MailReceiver mailReceiver;

    public static MyMessage get(MailReceiverInfo mailReceiverInfo)
    {
        if(myMessage == null)
            myMessage = new MyMessage(mailReceiverInfo);
        return myMessage;
    }

    private MyMessage(MailReceiverInfo mailReceiverInfo)
    {
        mailReceiver = new MailReceiver(mailReceiverInfo);
    }

    public Message[] getMessages()
    {
        return mailReceiver.receiveAllMail();
    }

    public void openInBoxFolder()
    {
        try {
            mailReceiver.connectToServer();
            mailReceiver.openInBoxFolder();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    public void closeInBoxFolder()
    {
        try {
            mailReceiver.closeConnection();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
