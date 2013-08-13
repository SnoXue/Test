package com.onyx.android.startup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.onyx.android.startsetting.R;

import android.app.Application;
import android.content.res.XmlResourceParser;

public class StartTutorialApplicatin extends Application
{
    public static final int EXIT_REQUEST = 100;
    
    public static List<Country> mCountryList = new ArrayList<Country>();
    public static List<Language> mLanguageList = new ArrayList<Language>();

    @Override
    public void onCreate()
    {
        super.onCreate();

        parseLocaleXml();
        parseCountryXml();
    }

    private void parseLocaleXml()
    {
        XmlResourceParser parser = getResources().getXml(R.xml.locales);
        int eventType = 0;
        try {
            eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("locale".equals(parser.getName())) {
                        String language = null;
                        String value = null;
                        eventType = parser.next();
                        while (!(eventType == XmlPullParser.END_TAG && "locale".equals(parser.getName()))) {
                            if (eventType == XmlPullParser.START_TAG) {
                                String nodeName = parser.getName();
                                eventType = parser.next();
                                if ("language".equals(nodeName)) {
                                    language = parser.getText().trim();
                                } else if ("value".equals(nodeName)) {
                                    value = parser.getText().trim();
                                } else {
                                    throw new XmlPullParserException("Xml parse error " + nodeName);
                                }
                            }
                            eventType = parser.next();

                        }
                        if (language != null && value != null)
                            mLanguageList.add(new Language(language, value));
                    }
                    break;

                default:
                    break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void parseCountryXml()
    {
        XmlResourceParser parser = getResources().getXml(R.xml.countries);
        int eventType = 0;
        try {
            eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("country".equals(parser.getName())) {
                        List<Language> languages = new ArrayList<Language>();
                        String name = null;
                        Locale locale = null;
                        eventType = parser.next();
                        while (!(eventType == XmlPullParser.END_TAG && "country".equals(parser.getName()))) {
                            if (eventType == XmlPullParser.START_TAG) {
                                String nodeName = parser.getName();
                                eventType = parser.next();
                                if ("name".equals(nodeName)) {
                                    name = parser.getText().trim();
                                } else if ("language".equals(nodeName)) {
                                    if ("Español(Brasil)".equals(parser.getText().trim())){
                                        System.out.println();
                                    }
                                    for (int i = 0; i < mLanguageList.size(); i++) {
                                        if (mLanguageList.get(i).name.equalsIgnoreCase(parser.getText().trim())) {
                                            locale = mLanguageList.get(i).locale;
                                            break;
                                        }
                                    }
                                    languages.add(new Language(parser.getText().trim(), locale));
                                } else {
                                    throw new XmlPullParserException("Xml parse error " + nodeName);
                                }
                            }
                            eventType = parser.next();
                        }
                        if (languages != null && name != null && locale != null)
                            mCountryList.add(new Country(name, languages));
                    }
                    break;

                default:
                    break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<String> getCountryName()
    {
        List<String> countries = new ArrayList<String>();
        for (int i = 0; i < mCountryList.size(); i++) {
            countries.add(mCountryList.get(i).name);
        }
        return countries;
    }
}

