package psd.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by pengsida on 2017/4/4.
 */

public class MyAuthenticator extends Authenticator
{
    private String userName;
    private String password;

    public MyAuthenticator(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication()
    {


        return new PasswordAuthentication(userName, password);
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
