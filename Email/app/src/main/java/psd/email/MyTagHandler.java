package psd.email;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * Created by pengsida on 2017/4/18.
 */

public class MyTagHandler implements Html.TagHandler
{
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("style")) {

        }

    }
}
