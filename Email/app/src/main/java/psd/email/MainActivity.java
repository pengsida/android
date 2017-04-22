package psd.email;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    // 用于putExtra()
    // ==============
    private static final String mailboxKey = "MAILBOX";
    private static final String passwordKey = "PASSWD";

    // widget
    // ==============
    private Button sendEmailButton;
    private Button mailListButton;

    // data
    // ==============
    private String mailbox;
    private String passwd;
    private Map<String, String> smtpServerList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIntentData();

        // 定义widget
        // ==============
        sendEmailButton = (Button)findViewById(R.id.start_send_email_btn);
        mailListButton = (Button)findViewById(R.id.start_mail_list_btn);

        // 定义data
        // ==============
        smtpServerList = new HashMap<String, String>();
        smtpServerList.put("zju", "smtp.zju.edu.cn");

        // 定义widget的动作
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> keys = new HashMap<String, String>();
                putIntentData(keys);
                Intent i = SendEmailActivity.newIntent(MainActivity.this, keys);
                startActivity(i);
            }
        });

        mailListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> keys = new HashMap<String, String>();
                putIntentData(keys);
                Intent i = MailListActivity.newIntent(MainActivity.this, keys);
                startActivity(i);
            }
        });
    }

    public static Intent newIntent(Context context, Map<String,String> keys)
    {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(mailboxKey, keys.get("mailbox"));
        i.putExtra(passwordKey, keys.get("passwd"));
        return i;
    }

    private void getIntentData()
    {
        mailbox = getIntent().getStringExtra(mailboxKey);
        passwd = getIntent().getStringExtra(passwordKey);
    }

    private void putIntentData(Map<String, String> keys)
    {
        keys.put("mailbox", mailbox);
        keys.put("passwd", passwd);
        keys.put("smtp", smtpServerList.get(resolve(mailbox)));
    }

    private String resolve(String mailbox)
    {
        return mailbox.split("@")[1].split("\\.")[0];
    }
}
