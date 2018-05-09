package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by jean on 26/12/16.
 */
public class Null extends ElementsRestrictionQuery {

    public static Null create(String field) {
        Null eq = new Null();
        eq.field = field;
        return eq;
    }

    public void toDDL(Query q) {
        q.descend(field).constrain(null).equal();
    }

}