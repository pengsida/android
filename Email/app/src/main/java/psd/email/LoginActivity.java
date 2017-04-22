package psd.email;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

/**
 * Created by pengsida on 2017/4/4.
 */

public class LoginActivity extends AppCompatActivity
{
    // widget
    // ===========
    private EditText mailboxEditText;
    private EditText passwdEditText;
    private Button loginButton;
    private Button quitButton;

    // data
    // ===========
    private String mailbox;
    private String passwd;
    private static Map<String, String> portList;
    private static Map<String, String> smtpServerList;

    private class VerifyPasswd extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            Session session = Session.getDefaultInstance(new Properties());

            try
            {
                Transport transport = session.getTransport("smtp");
                String mailboxKey = resolve(mailbox);
                transport.connect(smtpServerList.get(mailboxKey), Integer.parseInt(portList.get(mailboxKey)), mailbox, passwd);
                return "right";
            }
            catch (AuthenticationFailedException ea)
            {
                return "authenticate failed";
            }
            catch (MessagingException em)
            {
                return "wrong";
            }
        }

        @Override
        protected void onPostExecute(String text)
        {
            if (text.equals("right"))
            {
                Map<String, String> keys = new HashMap<String, String>();
                putIntentData(keys);
                Intent i = MainActivity.newIntent(LoginActivity.this, keys);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 定义widget
        // ===========
        mailboxEditText = (EditText)findViewById(R.id.mailbox_text);
        passwdEditText = (EditText)findViewById(R.id.passwd_text);
        loginButton = (Button)findViewById(R.id.login_btn);
        quitButton = (Button)findViewById(R.id.quit_btn);

        // data
        // ===========
        portList = new HashMap<String, String>();
        portList.put("qq", "587");
        portList.put("zju", "25");

        smtpServerList = new HashMap<String, String>();
        smtpServerList.put("qq", "smtp.qq.com");
        smtpServerList.put("zju", "smtp.zju.edu.cn");

        // widget的各个功能
        // ================
        mailboxEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mailbox = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                passwd = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!mailbox.contains("@"))
                {
                    Toast.makeText(LoginActivity.this, "mailbox is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mailbox.contains("."))
                {
                    Toast.makeText(LoginActivity.this, "mailbox is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

//                new Thread(verifyPasswd).start();
                new VerifyPasswd().execute();

//                Map<String, String> keys = new HashMap<String, String>();
//                putIntentData(keys);
//                Intent i = MainActivity.newIntent(LoginActivity.this, keys);
//                startActivity(i);
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void putIntentData(Map<String, String> keys)
    {
        keys.put("mailbox", mailbox);
        keys.put("passwd", passwd);
    }

    Runnable verifyPasswd = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();

            Session session = Session.getDefaultInstance(new Properties());

            try
            {
                Store store = session.getStore("pop3");
                store.connect("smtp.zju.edu.cn", mailbox, passwd);
                Log.d("AUTHEN", "right");
            }
            catch (AuthenticationFailedException ea)
            {
                Log.d("AUTHEN", "authenticate failed");
            }
            catch (MessagingException em)
            {
                Log.d("AUTHEN", "wrong");
            }
        }
    };

    private String resolve(String mailbox)
    {
        return mailbox.split("@")[1].split("\\.")[0];
    }
}
