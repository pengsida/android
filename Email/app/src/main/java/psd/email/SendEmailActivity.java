package psd.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengsida on 2017/4/5.
 */

public class SendEmailActivity extends AppCompatActivity
{
    // 用于putExtra()
    // ==============
    private static final String mailboxKey = "MAILBOX";
    private static final String passwordKey = "PASSWD";
    private static final String smtpServerKey = "SMTP";

    // data
    // ===========
    private String mailbox;
    private String passwd;
    private String toAddress;
    private String subject;
    private String content;
    private String smtpServer;

    // widget
    // ===========
    private EditText toAddressEditText;
    private EditText subjectEditText;
    private EditText contentEditText;
    private Button sendEmailButton;

    private class SendEmail extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        getIntentData();

//        Toast.makeText(SendEmailActivity.this, smtpServer, Toast.LENGTH_SHORT).show();

        // 定义widget
        toAddressEditText = (EditText)findViewById(R.id.to_address_text);
        subjectEditText = (EditText)findViewById(R.id.subject_text);
        contentEditText = (EditText)findViewById(R.id.content_text);
        sendEmailButton = (Button)findViewById(R.id.send_email_btn);

        // 定义widget的动作

        toAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                toAddress = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        subjectEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                subject = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                content = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Thread(sendEmail).start();
                finish();
            }
        });
    }

    public static Intent newIntent(Context context, Map<String,String> keys)
    {
        Intent i = new Intent(context, SendEmailActivity.class);
        i.putExtra(mailboxKey, keys.get("mailbox"));
        i.putExtra(passwordKey, keys.get("passwd"));
        i.putExtra(smtpServerKey, keys.get("smtp"));
        return i;
    }

    private void getIntentData()
    {
        mailbox = getIntent().getStringExtra(mailboxKey);
        passwd = getIntent().getStringExtra(passwordKey);
        smtpServer = getIntent().getStringExtra(smtpServerKey);
    }

    Runnable sendEmail = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            MailSenderInfo mailSenderInfo = new MailSenderInfo();
            mailSenderInfo.setMailServerHost(smtpServer);
            mailSenderInfo.setMailServerPort("25");
            mailSenderInfo.setValidate(true);
            mailSenderInfo.setUserName(mailbox);
            mailSenderInfo.setPassword(passwd);
            mailSenderInfo.setFromAddress(mailbox);
            mailSenderInfo.setToAddress(toAddress);
            mailSenderInfo.setSubject(subject);
            mailSenderInfo.setContent(content);
            MailSender.sendTextMail(mailSenderInfo);
        }
    };
}
