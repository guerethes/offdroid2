package br.com.guerethes.offdroid.utils;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;

import java.util.regex.Pattern;

public class RegexConstrain implements Evaluation {

    private final Pattern pattern;

    public RegexConstrain(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void evaluate(Candidate candidate) {
        String stringValue = (String) candidate.getObject();
        candidate.include(pattern.matcher(stringValue).matches());
    }

}
