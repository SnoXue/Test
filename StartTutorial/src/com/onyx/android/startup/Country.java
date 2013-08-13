package com.onyx.android.startup;

import java.util.ArrayList;
import java.util.List;

public class Country
{
    public Country(String name, List<Language> languageList)
    {
        this.name = name;
        this.languages = languageList;
    }

    String name;
    List<Language> languages = new ArrayList<Language>();
}
