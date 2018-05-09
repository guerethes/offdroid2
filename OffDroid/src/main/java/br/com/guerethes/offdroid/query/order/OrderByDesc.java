package br.com.guerethes.offdroid.query.order;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by jean on 26/12/16.
 */
public class OrderByDesc extends ElementsRestrictionQuery {

    public static OrderByDesc create(String field) {
        OrderByDesc orderByDesc = new OrderByDesc();
        orderByDesc.field = field;
        return orderByDesc;
    }

    @Override
    public void toDDL(Query q) {
        q.descend(field).orderDescending();
    }

}