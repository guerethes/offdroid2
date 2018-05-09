package br.com.guerethes.offdroid.query.order;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by jean on 26/12/16.
 */
public class OrderByAsc extends ElementsRestrictionQuery {

    public static OrderByAsc create(String field) {
        OrderByAsc orderByAsc = new OrderByAsc();
        orderByAsc.field = field;
        return orderByAsc;
    }

    public void toDDL(Query q) {
        q.descend(field).orderAscending();
    }

}