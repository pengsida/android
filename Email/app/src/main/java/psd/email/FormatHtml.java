package psd.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pengsida on 2017/4/18.
 */

public class FormatHtml
{
    public static String stringFormat(String html)
    {
        Pattern pattern = Pattern.compile("<img src=\"cid.*?>");
        Matcher matcher = pattern.matcher(html);
        System.out.println(matcher.replaceFirst("<img src=\"/storage/emulated/0/Android/data/psd.email/files/Pictures/test.jpg\" />"));
        String s = matcher.replaceFirst("<img src=\"/storage/emulated/0/Android/data/psd.email/files/Pictures/test.jpg\" />");
        pattern = Pattern.compile("<img src=\"http.*?>");
        matcher = pattern.matcher(s);
        return matcher.replaceFirst("");
    }

    public static void main(String[] args)
    {
        String html = "Please view this mail in HTML format.\n" +
                "------=_Part_536_1686665159.1492133879936\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "Content-Transfer-Encoding: quoted-printable\n" +
                "\n" +
                "<img src=3D\"cid:5da18d27_cd15b6a1b000b_Coremail_coremail$localhost\"><div>=\n<br></div><div><p class=3D\"MsoNormal\" style=3D\"text-indent:32.0pt;mso-cha=\n" +
                "r-indent-count:2.0\">";
        stringFormat(html);
    }
}
