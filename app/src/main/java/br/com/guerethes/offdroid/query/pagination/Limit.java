package br.com.guerethes.offdroid.query.pagination;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by victor on 01/09/17.
 */

public class Limit extends ElementsRestrictionQuery {

    public static Limit create(Object value) {
        Limit limit = new Limit();
        limit.value = value;
        return limit;
    }

    public void toDDL(Query q) {
    }

}
