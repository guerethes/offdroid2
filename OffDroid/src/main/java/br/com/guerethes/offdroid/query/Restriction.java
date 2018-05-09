package br.com.guerethes.offdroid.query;


import br.com.guerethes.offdroid.query.enun.EstrategiaPath;
import br.com.guerethes.offdroid.query.order.OrderByAsc;
import br.com.guerethes.offdroid.query.order.OrderByDesc;
import br.com.guerethes.offdroid.query.pagination.Limit;
import br.com.guerethes.offdroid.query.pagination.OffSet;
import br.com.guerethes.offdroid.query.restriction.Eq;
import br.com.guerethes.offdroid.query.restriction.Gt;
import br.com.guerethes.offdroid.query.restriction.Like;
import br.com.guerethes.offdroid.query.restriction.Ilike;
import br.com.guerethes.offdroid.query.restriction.Lt;
import br.com.guerethes.offdroid.query.restriction.NotNull;
import br.com.guerethes.offdroid.query.restriction.Null;

/**
 * Created by jean on 26/12/16.
 */
public class Restriction {

    /**
     * Restrição condicional IGUAL (<field> = <value>).
     *
     * @param field
     * @param value
     * @return
     */
    public static Eq eq(String field, Object value, EstrategiaPath estrategiaPath) {
        return Eq.create(field, value, estrategiaPath);
    }

    public static Eq eq(String field, Object value) {
        return Eq.create(field, value);
    }

    /**
     * Restrição condicional MAIOR QUE (<field> > <value>)
     *
     * @param field
     * @param value
     * @return
     */
    public static Gt gt(String field, Object value) {
        return Gt.create(field, value);
    }

    public static Gt gt(String field, Object value, EstrategiaPath estrategiaPath) {
        return Gt.create(field, value, estrategiaPath);
    }

    /**
     * Restrição condicional MENOR QUE (<field> < <value>).
     *
     * @param field
     * @param value
     * @return
     */
    public static Lt lt(String field, Object value) {
        return Lt.create(field, value);
    }

    public static Lt lt(String field, Object value, EstrategiaPath estrategiaPath) {
        return Lt.create(field, value, estrategiaPath);
    }

    /**
     * Restrição condicional IS NULL (<field> IS NULL).
     *
     * @param field
     * @return
     */
    public static Null isNull(String field) {
        return Null.create(field);
    }

    /**
     * Restrição condicional IS NOT NULL (<field> IS NOT NULL).
     *
     * @param field
     * @return
     */
    public static NotNull isNotNull(String field) {
        return NotNull.create(field);
    }

    /**
     * Restrição condicional LIKE (<field> LIKE (<value>)).
     *
     * @param field
     * @param value
     * @return
     */
    public static Like like(String field, Object value) {
        return Like.create(field, value);
    }

    public static Like like(String field, Object value, EstrategiaPath estrategiaPath) {
        return Like.create(field, value, estrategiaPath);
    }

    /**
     * Restrição condicional ILIKE (<field> LIKE (<value>)).
     *
     * @param field
     * @param value
     * @return
     */
    public static Ilike ilike(String field, Object value) {
        return Ilike.create(field, value);
    }

    public static Ilike ilike(String field, Object value, EstrategiaPath estrategiaPath) {
        return Ilike.create(field, value, estrategiaPath);
    }

    /**
     * Restrição de quantidade LIMIT (LIMIT <field> asc)
     *
     * @param field
     * @return
     */
    public static OrderByAsc orderByAsc(String field) {
        return OrderByAsc.create(field);
    }

    /**
     * Restrição de quantidade LIMIT (LIMIT <field> desc)
     *
     * @param field
     * @return
     */
    public static OrderByDesc orderByDesc(String field) {
        return OrderByDesc.create(field);
    }

    public static Limit limit(Object value) {
        return Limit.create(value);
    }

    public static OffSet offSet(Object value) {
        return OffSet.create(value);
    }

}