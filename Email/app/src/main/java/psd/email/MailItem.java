package psd.email;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.mail.Message;

/**
 * Created by pengsida on 2017/4/9.
 */

public class MailItem implements Comparable<MailItem>, Serializable
{
    private String subject;
    private Date date;
    private Message message;

    public MailItem(Message message, String subject, Date date)
    {
        this.message = message;
        this.subject = subject;
        this.date = date;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    @Override
    public int compareTo(MailItem arg0)
    {
        return -this.getDate().compareTo(arg0.getDate());
    }
}
