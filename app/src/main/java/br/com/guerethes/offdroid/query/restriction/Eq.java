package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;

/**
 * Created by jean on 26/12/16.
 */
public class Eq extends ElementsRestrictionQuery {

    public static Eq create(String field, Object value) {
        Eq eq = new Eq();
        eq.field = field;
        eq.value = value;
        return eq;
    }

    public static Eq create(String field, Object value, EstrategiaPath estrategiaPath) {
        Eq eq = new Eq();
        eq.field = field;
        eq.value = value;
        eq.estrategiaPath = estrategiaPath;
        return eq;
    }

    public void toDDL(Query q) {
        q.descend(field).constrain(value).equal();
    }

}