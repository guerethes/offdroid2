package br.com.guerethes.offdroid.persist.model;


import br.com.guerethes.offdroid.persist.interfaces.PersistDB;

/**
 * Created by jean on 27/12/16.
 */
public class Cache implements PersistDB {

    private Object id;
    private long time;

    public Cache() {}

    public Cache(Object id, long time) {
        this.id = id;
        this.time = time;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "id " + id + " time " + time;
    }

}