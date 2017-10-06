package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;

/**
 * Created by jean on 26/12/16.
 */
public class Gt extends ElementsRestrictionQuery {

    public static Gt create(String field, Object value) {
        Gt gt = new Gt();
        gt.field = field;
        gt.value = value;
        return gt;
    }

    public static Gt create(String field, Object value, EstrategiaPath estrategiaPath) {
        Gt gt = new Gt();
        gt.field = field;
        gt.value = value;
        gt.estrategiaPath = estrategiaPath;
        return gt;
    }

    public void toDDL(Query q) {
        q.descend(field).constrain(value).greater();
    }

}