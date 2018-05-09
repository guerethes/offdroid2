package br.com.guerethes.offdroid.query.pagination;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by victor on 01/09/17.
 */

public class OffSet extends ElementsRestrictionQuery {

    public static OffSet create(Object value) {
        OffSet offSet = new OffSet();
        offSet.value = value;
        return offSet;
    }

    public void toDDL(Query q) {
    }

}

