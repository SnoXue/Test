package com.onyx.android.startup;

import java.util.Locale;

class Language
{
    public Language(String name, String locale)
    {
        this.name = name;
        String languageAndCountry[] = locale.split("_"); 
        this.locale = new Locale(languageAndCountry[0], languageAndCountry[1]);
    }

    public Language(String name, Locale locale)
    {
        this.name = name;
        this.locale = locale;
    }

    String name;
    Locale locale;
}