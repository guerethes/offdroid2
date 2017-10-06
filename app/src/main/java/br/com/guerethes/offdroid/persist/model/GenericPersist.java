package br.com.guerethes.offdroid.persist.model;

import br.com.guerethes.offdroid.persist.interfaces.PersistDB;

/**
 * Created by victor on 09/08/17.
 */

public class GenericPersist implements PersistDB {

    public void beforeInsert() {}

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public void setId(Object id) {
    }

}
