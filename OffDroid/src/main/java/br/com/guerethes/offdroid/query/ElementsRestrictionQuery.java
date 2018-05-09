package br.com.guerethes.offdroid.query;

import br.com.guerethes.offdroid.query.enun.EstrategiaPath;

/**
 * Created by jean on 26/12/16.
 */
public abstract class ElementsRestrictionQuery implements ElementsQuery {

    protected String field;
    protected Object value;
    protected EstrategiaPath estrategiaPath;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public EstrategiaPath getEstrategiaPath() {
        return estrategiaPath;
    }

    public void setEstrategiaPath(EstrategiaPath estrategiaPath) {
        this.estrategiaPath = estrategiaPath;
    }

}