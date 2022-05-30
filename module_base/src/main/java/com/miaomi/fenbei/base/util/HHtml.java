package com.miaomi.fenbei.base.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;

/**
 * Created by
 * on 2019-11-04.
 */
public class HHtml {

    private HHtml() {
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Spanned fromHtml(String source, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        return fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler);
    }

    public static Spanned fromHtml(String source, int flags, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HtmlParser.schema);
        } catch (org.xml.sax.SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (org.xml.sax.SAXNotSupportedException e) {
            throw new RuntimeException(e);
        }
        HtmlToSpannedConverter converter = new HtmlToSpannedConverter(source, imageGetter, tagHandler, parser, flags);
        return converter.convert();
    }
}
