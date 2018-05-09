package br.com.guerethes.offdroid.query.restriction;

import com.db4o.query.Query;

import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;
import br.com.guerethes.offdroid.utils.RegexConstrain;

/**
 * Created by jean on 26/12/16.
 */
public class Ilike extends ElementsRestrictionQuery {

    public static Ilike create(String field, Object value) {
        Ilike ilk = new Ilike();
        ilk.field = field;
        ilk.value = value;
        return ilk;
    }

    public static Ilike create(String field, Object value, EstrategiaPath estrategiaPath) {
        Ilike iLike = new Ilike();
        iLike.field = field;
        iLike.value = value;
        iLike.estrategiaPath = estrategiaPath;
        return iLike;
    }

    public void toDDL(Query q) {
        if (value instanceof String) {
            value = ((String) value).replaceAll(" ", "|");
            q.descend(field).constrain(new RegexConstrain(".*(" + value + ").*"));
        } else {
            q.descend(field).constrain(new RegexConstrain(".*" + value + ".*"));
        }
    }

}