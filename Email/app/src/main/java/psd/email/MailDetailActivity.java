package psd.email;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Created by pengsida on 2017/4/10.
 */

public class MailDetailActivity extends AppCompatActivity
{
    // 用于putExtra()
    // ==============
    private static final String messageIdKey = "MESSAGEID";

    // widget
    // ===============
    private TextView fromAddressTextView;
    private TextView subjectTextView;
    private TextView contentView;
//    private Button testButton;

    // data
    // ===============
    private String messageId;
    private MailReceiver mailReceiver;
    Message[] messages;
    private MessageResolver messageResolver;
    private String fromAddress;
    private String subject;
    private String content;
    private String contentType;
    private MyMessage myMessage;
    private String dirName;

    private class GetMessages extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {

            try
            {
                myMessage.openInBoxFolder();
                messageResolver = new MessageResolver(myMessage.getMessage());
                fromAddress = messageResolver.getFrom();
                subject = messageResolver.getSubject();
                contentType = messageResolver.getContentType();
                if (contentType.contains("multipart"))
                {
                    File externalFilesDir = MailDetailActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    if(externalFilesDir == null)
                    {
                        Toast.makeText(MailDetailActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    Log.d("OPEN", externalFilesDir.toString());
                    dirName = externalFilesDir.toString();

                    content = messageResolver.getHtmlContent(dirName);
                    if (content.contains("<style>"))
                    {
                        int start = content.indexOf("<style>");
                        int end = content.indexOf("</style>");
                        content = content.substring(0, start) + content.substring(end+8);
                    }

//                    if (contentType.contains("multipart/related"))
//                    {
//                        messageResolver.saveDisposition(dirName);
//                    }
                }
                if (content == null || content.equals(""))
                    content = messageResolver.getPlainContent();
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params)
        {
            fromAddressTextView.setText(fromAddress);
            subjectTextView.setText(subject);

            String temp = "<img src=\"/storage/emulated/0/Android/data/psd.email/files/Pictures/test.jpg\" />";
            if (contentType.contains("multipart") && content != null)
            {
                if (contentType.contains("multipart/related"))
                {
                    content = FormatHtml.stringFormat(content);
                    contentView.setText(Html.fromHtml(content, imageGetter, null));
                }
                else
                    contentView.setText(Html.fromHtml(content));
            }
            else
                contentView.setText(content);
        }
    }

    private class GetImage extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... params)
        {
            Log.d("OPEN", params[0]);
            try {
                messageResolver.saveDisposition(params[0]);
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void params)
        {
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceSave)
    {
        super.onCreate(savedInstanceSave);
        setContentView(R.layout.activity_mail_detail);

        // widget
        // ===============
        fromAddressTextView = (TextView)findViewById(R.id.from_address_text);
        subjectTextView = (TextView)findViewById(R.id.detail_subject_text);
        contentView = (TextView)findViewById(R.id.mail_content_text);
        contentView.setMovementMethod(new ScrollingMovementMethod());
        contentView.setMovementMethod(LinkMovementMethod.getInstance());

        // data
        // ===============
        myMessage = MyMessage.get(new MailReceiverInfo());
        new GetMessages().execute();


        // 定义widget的动作
        // ===============
        fromAddressTextView.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;
            @Override
            public void onClick(View v) {
                if (flag)
                {
                    flag = false;
                    fromAddressTextView.setEllipsize(null);
                    fromAddressTextView.setSingleLine(flag);
                }
                else
                {
                    flag = true;
                    fromAddressTextView.setEllipsize(TextUtils.TruncateAt.END);
                    fromAddressTextView.setSingleLine(flag);
                }
            }
        });
    }

    public static Intent newIntent(Context context)
    {
        return new Intent(context, MailDetailActivity.class);
    }

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            Drawable drawable;
            drawable=Drawable.createFromPath(source);
            if (drawable == null)
                return null;
            Point size = new Point();
            MailDetailActivity.this.getWindowManager().getDefaultDisplay().getSize(size);

            drawable.setBounds(0, 0, size.x, size.y/2);
            return drawable;
        }
    };
}
