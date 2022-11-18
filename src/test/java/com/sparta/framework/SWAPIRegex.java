package com.sparta.framework;

public interface SWAPIRegex {

    String URL_PATTERN = "(https?:\\/{2}swapi\\.dev\\/api\\/[a-zA-Z\\d.\\-_~:\\/?#\\[\\]@!$&'()*,+;%=]*)?";
    String BIRTH_YEAR_PATTERN = "\\d+ ?[AB]BY";
    String GENDER_PATTERN = "((fe)?male|unknown|n\\/a)";
    String POSITIVE_INTEGER_PATTERN = "\\d+";
    String STARTS_WITH_UPPERCASE_PATTERN = "[A-Z][a-z]+";

}
