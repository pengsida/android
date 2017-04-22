package psd.email;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.net.URL;


/**
 * Created by pengsida on 2017/4/17.
 */

public class TestActivity extends AppCompatActivity
{
    private TextView textView;
    private WebView tv;
    private String html;

    private class OpenMailReceiver extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {

            return null;
        }

        @Override
        protected void onPostExecute(Void params)
        {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView=(TextView)findViewById(R.id.test_content);
//        tv=(WebView)findViewById(R.id.test_content);
        html="<html xmlns:v=3D\"urn:schemas-microsoft-com:vml\" =\n" +
                "xmlns:o=3D\"urn:schemas-microsoft-com:office:office\" =\n" +
                "xmlns:w=3D\"urn:schemas-microsoft-com:office:word\" =\n" +
                "xmlns:m=3D\"http://schemas.microsoft.com/office/2004/12/omml\" =\n" +
                "xmlns=3D\"http://www.w3.org/TR/REC-html40\"><head><meta =\n" +
                "http-equiv=3DContent-Type content=3D\"text/html; charset=3Dgb2312\"><meta =\n" +
                "name=3DGenerator content=3D\"Microsoft Word 14 (filtered =\n" +
                "medium)\"><style><!--\n" +
                "/* Font Definitions */\n" +
                "@font-face\n" +
                "\t{font-family:=CB=CE=CC=E5;\n" +
                "\tpanose-1:2 1 6 0 3 1 1 1 1 1;}\n" +
                "@font-face\n" +
                "\t{font-family:\"Cambria Math\";\n" +
                "\tpanose-1:2 4 5 3 5 4 6 3 2 4;}\n" +
                "@font-face\n" +
                "\t{font-family:Calibri;\n" +
                "\tpanose-1:2 15 5 2 2 2 4 3 2 4;}\n" +
                "@font-face\n" +
                "\t{font-family:\"\\@=CB=CE=CC=E5\";\n" +
                "\tpanose-1:2 1 6 0 3 1 1 1 1 1;}\n" +
                "/* Style Definitions */\n" +
                "p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
                "\t{margin:0cm;\n" +
                "\tmargin-bottom:.0001pt;\n" +
                "\tfont-size:12.0pt;\n" +
                "\tfont-family:=CB=CE=CC=E5;}\n" +
                "a:link, span.MsoHyperlink\n" +
                "\t{mso-style-priority:99;\n" +
                "\tcolor:blue;\n" +
                "\ttext-decoration:underline;}\n" +
                "a:visited, span.MsoHyperlinkFollowed\n" +
                "\t{mso-style-priority:99;\n" +
                "\tcolor:purple;\n" +
                "\ttext-decoration:underline;}\n" +
                "span.EmailStyle17\n" +
                "\t{mso-style-type:personal-reply;\n" +
                "\tfont-family:\"Calibri\",\"sans-serif\";\n" +
                "\tcolor:#1F497D;}\n" +
                ".MsoChpDefault\n" +
                "\t{mso-style-type:export-only;\n" +
                "\tfont-family:\"Calibri\",\"sans-serif\";}\n" +
                "@page WordSection1\n" +
                "\t{size:612.0pt 792.0pt;\n" +
                "\tmargin:72.0pt 90.0pt 72.0pt 90.0pt;}\n" +
                "div.WordSection1\n" +
                "\t{page:WordSection1;}\n" +
                "--></style><!--[if gte mso 9]><xml>\n" +
                "<o:shapedefaults v:ext=3D\"edit\" spidmax=3D\"1026\" />\n" +
                "</xml><![endif]--><!--[if gte mso 9]><xml>\n" +
                "<o:shapelayout v:ext=3D\"edit\">\n" +
                "<o:idmap v:ext=3D\"edit\" data=3D\"1\" />\n" +
                "</o:shapelayout></xml><![endif]--></head><body lang=3DZH-CN link=3Dblue =\n" +
                "vlink=3Dpurple><div class=3DWordSection1><p class=3DMsoNormal><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=B6=C1=C1=CB=B8=BD=BC=FE=A3=AC=BB=\n" +
                "=B9=B2=BB=CA=C7=BA=DC=B6=AE=A1=B0</span>=C2=E3=BD=F0=CA=F4<span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=A1=B1=A1=A3=D3=D0=C3=BB=D3=D0=CD=\n" +
                "=C6=BC=F6=B5=C4=B2=CE=BF=BC=CE=C4=CF=D7</span><span =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'> </span><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=A3=BF</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p>&nbsp;</o:p></span></p><p class=3DMsoNormal><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=CE=D2=CC=D8=B1=F0=CF=EB=C5=AA=C7=\n" +
                "=E5=B3=FE=A3=BA</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=CE=AA=CA=B2=C3=B4=D0=E8=D2=AA=C2=\n" +
                "=E3=BD=F0=CA=F4</span><span =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'> </span><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=A3=BF</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=C2=E3=BD=F0=CA=F4=D3=D0=C4=C4=D0=\n" +
                "=A9=B9=A6=C4=DC</span><span =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'> </span><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=A3=BF</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'>OpenStack</span><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=D4=F5=C3=B4=D2=FD=BD=F8=B5=C4=C2=\n" +
                "=E3=BD=F0=CA=F4=A3=AC=D3=D6=CA=C7=D4=F5=C3=B4=BC=AF=B3=C9=B5=C4=C2=E3=BD=F0=\n" +
                "=CA=F4</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'>&nbsp; </span><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=A3=BF</span><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p>&nbsp;</o:p></span></p><p class=3DMsoNormal><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p>&nbsp;</o:p></span></p><p class=3DMsoNormal><span =\n" +
                "style=3D'font-size:10.5pt;color:#1F497D'>=C0=EE=C9=C6=C6=BD</span><span =\n" +
                "lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p></o:p></span></p><p class=3DMsoNormal><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.5pt;font-family:\"Calibri\",\"sans-serif\";color:#1F497=\n" +
                "D'><o:p>&nbsp;</o:p></span></p><p class=3DMsoNormal><b><span =\n" +
                "style=3D'font-size:10.0pt'>=B7=A2=BC=FE=C8=CB<span =\n" +
                "lang=3DEN-US>:</span></span></b><span lang=3DEN-US =\n" +
                "style=3D'font-size:10.0pt'> </span><span =\n" +
                "style=3D'font-size:10.0pt'>=C5=ED=CB=BC=B4=EF<span lang=3DEN-US> =\n" +
                "[mailto:pengsida@zju.edu.cn] <br></span><b>=B7=A2=CB=CD=CA=B1=BC=E4<span =\n" +
                "lang=3DEN-US>:</span></b><span lang=3DEN-US> Sunday, March 19, 2017 =\n" +
                "11:44 PM<br></span><b>=CA=D5=BC=FE=C8=CB<span =\n" +
                "lang=3DEN-US>:</span></b><span lang=3DEN-US> =\n" +
                "shan@zju.edu.cn<br></span><b>=D6=F7=CC=E2<span =\n" +
                "lang=3DEN-US>:</span></b><span lang=3DEN-US> =\n" +
                "</span>=C5=ED=CB=BC=B4=EF=B5=C4=B5=DA=CA=AE=C6=DF=B4=CE=D1=A7=CF=B0=B1=A8=\n" +
                "=B8=E6<span lang=3DEN-US><o:p></o:p></span></span></p><p =\n" +
                "class=3DMsoNormal><span lang=3DEN-US><o:p>&nbsp;</o:p></span></p><p =\n" +
                "class=3DMsoNormal>=C0=CF=CA=A6=A3=AC=C4=E3=BA=C3=A3=BA<span =\n" +
                "lang=3DEN-US><o:p></o:p></span></p><div><p class=3DMsoNormal><span =\n" +
                "lang=3DEN-US><o:p>&nbsp;</o:p></span></p></div><div><p =\n" +
                "class=3DMsoNormal><span lang=3DEN-US>&nbsp; &nbsp; =\n" +
                "</span>=CE=D2=D5=E2=D0=C7=C6=DA=D4=DA=B9=AB=CB=BE=D6=F7=D2=AA=CA=C7=D1=A7=\n" +
                "=CF=B0=C2=E3=BD=F0=CA=F4=B5=C4=D2=BB=D0=A9=C4=DA=C8=DD=A3=AC=C8=BB=BA=F3=BF=\n" +
                "=B4=C1=CB<span lang=3DEN-US>ironic</span>=D7=E9=BC=FE=D2=D4=BC=B0<span =\n" +
                "lang=3DEN-US>linux</span>=D6=D0=B5=C4=CF=E0=B9=D8=D6=AA=CA=B6=A1=A3=D2=F2=\n" +
                "=CE=AA=CF=EB=B5=BD=B2=BF=CA=F0=C2=E3=BD=F0=CA=F4=BF=C9=C4=DC=BB=E1=D0=E8=D2=\n" +
                "=AA=D2=BB=D0=A9=CD=F8=C2=E7=D6=AA=CA=B6=A3=AC=D7=D4=BC=BA=D2=B2=BF=AA=CA=BC=\n" +
                "=D1=A7=BC=C6=CB=E3=BB=FA=CD=F8=C2=E7=C1=CB=A1=A3=D4=DA=D1=A7=D0=A3=C0=EF=B3=\n" +
                "=FD=C1=CB=BF=CE=C4=DA=B5=C4=D1=A7=CF=B0=A3=AC=D6=F7=D2=AA=CA=C7=D4=DA=BF=B4=\n" +
                "=B0=B2=D7=BF=B1=E0=B3=CC=B5=C4=B6=AB=CE=F7=A3=AC=B8=D0=BE=F5<span =\n" +
                "lang=3DEN-US>SRTP</span>=BB=B9=CA=C7=C4=DC=B6=CD=C1=B6=D2=BB=D0=A9=C4=DC=C1=\n" +
                "=A6=B5=C4=A3=AC=B5=B1=C8=BB=D0=A7=C2=CA=D4=B6=D4=B6=C3=BB=D3=D0=D4=DA=B9=AB=\n" +
                "=CB=BE=C4=C7=C3=B4=B8=DF=A1=A3<span =\n" +
                "lang=3DEN-US><o:p></o:p></span></p></div><div><p class=3DMsoNormal><span =\n" +
                "lang=3DEN-US>&nbsp; &nbsp; =\n" +
                "</span>=D2=D4=C9=CF=BE=CD=CA=C7=D5=E2=D6=DC=B5=C4=D1=A7=CF=B0=C4=DA=C8=DD=\n" +
                "=A3=AC=D7=A3=C0=CF=CA=A6=D0=C2=B5=C4=D2=BB=D6=DC=D0=C4=C7=E9=D3=E4=BF=EC<=\n" +
                "span lang=3DEN-US> :)<o:p></o:p></span></p></div><div><p =\n" +
                "class=3DMsoNormal><span =\n" +
                "lang=3DEN-US><o:p>&nbsp;</o:p></span></p></div><div><p =\n" +
                "class=3DMsoNormal><span =\n" +
                "lang=3DEN-US><o:p>&nbsp;</o:p></span></p></div><div><p =\n" +
                "class=3DMsoNormal><span lang=3DEN-US>best =\n" +
                "regards,<o:p></o:p></span></p></div><div><p =\n" +
                "class=3DMsoNormal>=C5=ED=CB=BC=B4=EF<span =\n" +
                "lang=3DEN-US><o:p></o:p></span></p></div></div></body></html>";

        int start_position = html.indexOf("<style>");
        int end_position = html.indexOf("</style>");
        String temp = html;
        html = temp.substring(0, start_position) + temp.substring(end_position+8);

//        tv.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//        tv.setText(Html.fromHtml(html));
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        textView.setText(Html.fromHtml(html, null, new MyTagHandler()));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        tv.loadData(html, "text/html; charset=UTF-8", null);
//        tv.getSettings().setDomStorageEnabled(true);
//        tv.getSettings().setBlockNetworkImage(false);
    }
}
