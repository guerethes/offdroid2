package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;

/**
 * Created by jean on 26/12/16.
 */
public class Lt extends ElementsRestrictionQuery {

    public static Lt create(String field, Object value) {
        Lt lt = new Lt();
        lt.field = field;
        lt.value = value;
        return lt;
    }

    public static Lt create(String field, Object value, EstrategiaPath estrategiaPath) {
        Lt lt = new Lt();
        lt.field = field;
        lt.value = value;
        lt.estrategiaPath = estrategiaPath;
        return lt;
    }

    public void toDDL(Query q) {
        q.descend(field).constrain(value).smaller();
    }

}