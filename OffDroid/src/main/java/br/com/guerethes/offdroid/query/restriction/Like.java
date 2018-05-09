package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;

/**
 * Created by jean on 26/12/16.
 */
public class Like extends ElementsRestrictionQuery {

    public static Like create(String field, Object value) {
        Like lk = new Like();
        lk.field = field;
        lk.value = value;
        return lk;
    }

    public static Like create(String field, Object value, EstrategiaPath estrategiaPath) {
        Like lk = new Like();
        lk.field = field;
        lk.value = value;
        lk.estrategiaPath = estrategiaPath;
        return lk;
    }

    public void toDDL(Query q) {
        q.descend(field).constrain(value).like();
    }

}