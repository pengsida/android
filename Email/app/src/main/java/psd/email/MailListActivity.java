package psd.email;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * Created by pengsida on 2017/4/9.
 */

public class MailListActivity extends AppCompatActivity
{
    // 用于putExtra()
    // ==============
    Map<String, String> keys = new HashMap<String, String>();
    private static final String mailboxKey = "MAILBOX";
    private static final String passwordKey = "PASSWD";
    private static final String smtpServerKey = "SMTP";

    // widget
    // ===============
    private RecyclerView recyclerView;
    private MailItemAdapter mailItemAdapter;

    // data
    // ===============
    Message[] messages;
    private String mailbox;
    private String passwd;
    private MyMessage myMessage;

    private class OpenMailReceiver extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            myMessage.openInBoxFolder();

            return null;
        }

        @Override
        protected void onPostExecute(Void params)
        {
            new GetMessages().execute();
        }
    }

    private class GetMessages extends AsyncTask<Void, Void, List<MailItem>>
    {
        @Override
        protected List<MailItem> doInBackground(Void... params)
        {
            messages = myMessage.getMessages();
            ArrayList<MailItem> mailItemList = new ArrayList<MailItem>();

            for (int index = 0; index < messages.length; ++index)
            {
                try {
                    MessageResolver messageResolver = new MessageResolver(messages[index]);

                    mailItemList.add(new MailItem(messages[index], messageResolver.getSubject(), messageResolver.getSentDate()));
                }
                catch (MessagingException e)
                {
                    e.printStackTrace();
                }
            }
            Collections.sort(mailItemList);
            return mailItemList;
        }

        @Override
        protected void onPostExecute(List<MailItem> mailItemList)
        {
            mailItemAdapter = new MailItemAdapter(mailItemList);
            recyclerView.setAdapter(mailItemAdapter);
        }
    }

    private class CloseMailReceiver extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            myMessage.closeInBoxFolder();

            return null;
        }
    }

    private class MailItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView subjectTextView;
        public TextView dateTextView;
        public Message message;

        public MailItemHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            subjectTextView = (TextView)itemView.findViewById(R.id.from_and_subject_info);
            dateTextView = (TextView)itemView.findViewById(R.id.date_info);
        }

        @Override
        public void onClick(View v)
        {
            myMessage.setMessage(this.message);
            Intent i = MailDetailActivity.newIntent(MailListActivity.this);
            startActivity(i);
        }
    }

    private class MailItemAdapter extends RecyclerView.Adapter<MailItemHolder>
    {
        private List<MailItem> mailItemList;
        private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public MailItemAdapter(List<MailItem> mailItemList)
        {
            this.mailItemList = mailItemList;
        }

        @Override
        public MailItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(MailListActivity.this);
            View view = layoutInflater.inflate(R.layout.received_mail_item, parent, false);
            return new MailItemHolder(view);
        }

        @Override
        public void onBindViewHolder(MailItemHolder holder, int position)
        {
            if (mailItemList == null)
                return;
            holder.subjectTextView.setText(mailItemList.get(position).getSubject());
            holder.dateTextView.setText(dateFormat.format(mailItemList.get(position).getDate()));
            holder.message = mailItemList.get(position).getMessage();
        }

        @Override
        public int getItemCount()
        {
            if (mailItemList == null)
                return 0;
            return this.mailItemList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_list);
        getIntentData();

        // widget
        // ===============
        recyclerView = (RecyclerView)findViewById(R.id.mail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MailListActivity.this));
        mailItemAdapter = new MailItemAdapter(null);
        recyclerView.setAdapter(mailItemAdapter);

        // data
        // ===============
        MailReceiverInfo receiverInfo = new MailReceiverInfo();
        receiverInfo.setMailServerHost("pop3.zju.edu.cn");
        receiverInfo.setUserName(mailbox);
        receiverInfo.setPassword(passwd);
        myMessage = MyMessage.get(receiverInfo);

        new OpenMailReceiver().execute();
    }

    @Override
    public void onDestroy()
    {
        new CloseMailReceiver().execute();
        super.onDestroy();
    }

    public static Intent newIntent(Context context, Map<String,String> keys)
    {
        Intent i = new Intent(context, MailListActivity.class);
        i.putExtra(mailboxKey, keys.get("mailbox"));
        i.putExtra(passwordKey, keys.get("passwd"));
        return i;
    }

    private void getIntentData()
    {
        mailbox = getIntent().getStringExtra(mailboxKey);
        passwd = getIntent().getStringExtra(passwordKey);
    }
}
