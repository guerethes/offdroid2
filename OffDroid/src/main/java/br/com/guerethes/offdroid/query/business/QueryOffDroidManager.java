package br.com.guerethes.offdroid.query.business;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

import br.com.guerethes.offdroid.annotation.estrategy.OnlyLocalStorage;
import br.com.guerethes.offdroid.annotation.estrategy.OnlyOnLine;
import br.com.guerethes.offdroid.exception.OffDroidException;
import br.com.guerethes.offdroid.network.NetWorkUtils;
import br.com.guerethes.offdroid.network.NetworkChangeReceiver;
import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.persist.model.GenericPersist;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.builders.BuildOperation;
import br.com.guerethes.offdroid.query.builders.BuildUrl;
import br.com.guerethes.offdroid.reflection.EntityReflection;
import br.com.guerethes.offdroid.utils.PropertiesUtils;

/**
 * Created by jean on 26/12/16.
 */
public class QueryOffDroidManager {

    private long cache;
    private boolean local;
    private static Context context;
    private Class<PersistDB> classEntity;
    private static List<ElementsRestrictionQuery> restrictions;
    private static List<BroadcastReceiver> receivers = new ArrayList<BroadcastReceiver>();
    private static QueryOffDroidAbsLocal queryOffDroidAbsLocal = null;
    private static QueryOffDroidAbsWeb queryOffDroidAbsWeb = null;
    private static PropertiesUtils propertiesUtils = null;
    private static NetworkChangeReceiver receiver = new NetworkChangeReceiver();
    private BuildUrl buildUrl;


    private QueryOffDroidManager(Class e, long cache, boolean local, Context context,
                                 QueryOffDroidAbsLocal queryOffDroidAbsLocalTemp, QueryOffDroidAbsWeb queryOffDroidAbsWebTemp) {
        super();
        this.classEntity = e;
        this.cache = cache;
        this.local = local;
        this.context = context;

        if (queryOffDroidAbsLocalTemp != null)
            queryOffDroidAbsLocal = queryOffDroidAbsLocalTemp;
        if (queryOffDroidAbsWebTemp != null)
            queryOffDroidAbsWeb = queryOffDroidAbsWebTemp;

        if (context != null && receivers.isEmpty()) {
            unregisterReceiver();
            initializeBroadcast(context);
        }
    }

    public synchronized static QueryOffDroidManager from(Context context) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(null, 0, false, context, null, null);
    }

    public synchronized static QueryOffDroidManager from(Class<?> clazz, Context context) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(clazz, 0, false, context, null, null);
    }

    public synchronized static QueryOffDroidManager from(Class<?> clazz, long cache, Context context) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(clazz, cache, false, context, null, null);
    }

    public synchronized static QueryOffDroidManager from(Class<?> clazz, boolean local, Context context) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(clazz, 0, local, context, null, null);
    }

    public synchronized static QueryOffDroidManager from(Class<?> clazz, Context context, QueryOffDroidAbsLocal queryOffDroidAbsLocal) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(clazz, 0, false, context, queryOffDroidAbsLocal, null);
    }

    public synchronized static QueryOffDroidManager from(Class<?> clazz, Context context, QueryOffDroidAbsWeb queryOffDroidAbsWeb) {
        restrictions = new ArrayList<ElementsRestrictionQuery>();
        return new QueryOffDroidManager(clazz, 0, false, context, null, queryOffDroidAbsWeb);
    }

    /**
     * Adição das Restrinções possíveis
     *
     * @param restriction
     */
    public void add(ElementsRestrictionQuery restriction) {
        restrictions.add(restriction);
    }

    /**
     * Consulta ao banco ou serviço
     *
     * @return
     * @throws Exception
     */
    public synchronized ArrayList<?> toList() throws Exception {
        return toList(true);
    }

    public synchronized ArrayList<?> toList(boolean update) throws Exception {
        if (context != null && hasCache(context)) {
            return getInstanceQueryOffDroidAbsLocal().toList(classEntity, restrictions, context);
        } else if (local) {
            return getInstanceQueryOffDroidAbsLocal().toList(classEntity, restrictions, context);
        } else {
            if (EntityReflection.haAnnotation(classEntity, OnlyLocalStorage.class)) {
                return getInstanceQueryOffDroidAbsLocal().toList(classEntity, restrictions, context);
            }

            if (EntityReflection.haAnnotation(classEntity, OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline()) {
                    return getInstanceQueryOffDroidAbsWeb().toList(classEntity, restrictions, context, getPropertiesUtils(context));
                } else
                    return null;
            }

            if (NetWorkUtils.isOnline()) {
                ArrayList<PersistDB> result = getInstanceQueryOffDroidAbsWeb().toList(classEntity, restrictions, context, getPropertiesUtils(context));
                for (PersistDB persistDB : result) {
                    if (persistDB instanceof GenericPersist) {
                        GenericPersist genericPersist = (GenericPersist) persistDB;
                        genericPersist.beforeInsert();
                    }

                    getInstanceQueryOffDroidAbsLocal().insert(persistDB, update, context);
                }
                saveCache();
                return result;
            } else {
                return getInstanceQueryOffDroidAbsLocal().toList(classEntity, restrictions, context);
            }
        }
    }

    public synchronized PersistDB toUniqueResult() throws Exception {
        return toUniqueResult(true);
    }

    public synchronized PersistDB toUniqueResult(boolean update) throws Exception {
        if (context != null && hasCache(context)) {
            return getInstanceQueryOffDroidAbsLocal().toUniqueResult(classEntity, restrictions, context);
        } else if (local) {
            return getInstanceQueryOffDroidAbsLocal().toUniqueResult(classEntity, restrictions, context);
        } else {
            if (EntityReflection.haAnnotation(classEntity, OnlyLocalStorage.class)) {
                return getInstanceQueryOffDroidAbsLocal().toUniqueResult(classEntity, restrictions, context);
            }

            if (EntityReflection.haAnnotation(classEntity, OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline()) {
                    return getInstanceQueryOffDroidAbsWeb().toUniqueResult(classEntity, restrictions, context, getPropertiesUtils(context));
                } else {
                    return null;
                }
            }

            if (NetWorkUtils.isOnline()) {
                PersistDB persistDB = getInstanceQueryOffDroidAbsWeb().toUniqueResult(classEntity, restrictions, context, getPropertiesUtils(context));
                if (persistDB instanceof GenericPersist) {
                    GenericPersist genericPersist = (GenericPersist) persistDB;
                    genericPersist.beforeInsert();
                }
                getInstanceQueryOffDroidAbsLocal().insert(persistDB, update, context);
                saveCache();
                return persistDB;
            } else {
                return getInstanceQueryOffDroidAbsLocal().toUniqueResult(classEntity, restrictions, context);
            }
        }
    }

    public synchronized Integer count() throws OffDroidException {
        if (context != null && hasCache(context)) {
            return getInstanceQueryOffDroidAbsLocal().count(classEntity, restrictions, context);
        } else if (local) {
            return getInstanceQueryOffDroidAbsLocal().count(classEntity, restrictions, context);
        } else {
            if (EntityReflection.haAnnotation(classEntity, OnlyLocalStorage.class)) {
                return getInstanceQueryOffDroidAbsLocal().count(classEntity, restrictions, context);
            }

            if (EntityReflection.haAnnotation(classEntity, OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline())
                    return getInstanceQueryOffDroidAbsWeb().count(classEntity, restrictions, context);
                else
                    return null;
            }

            if (NetWorkUtils.isOnline()) {
                return getInstanceQueryOffDroidAbsWeb().count(classEntity, restrictions, context);
            } else {
                return getInstanceQueryOffDroidAbsLocal().count(classEntity, restrictions, context);
            }

        }
    }

    /**
     * Inserção dos dados.
     *
     * @param persistDB
     */
    public synchronized PersistDB insert(PersistDB persistDB) throws Exception {
        return insert(persistDB, false);
    }

    public synchronized PersistDB insertLocal(PersistDB persistDB) throws Exception {
        return insert(persistDB, true);
    }

    private synchronized PersistDB insert(PersistDB persistDB, boolean local) throws Exception {
        return insert(persistDB, local, true);
    }

    private synchronized PersistDB insert(PersistDB persistDB, boolean local, boolean remove) throws Exception {
        if (local) {
            return getInstanceQueryOffDroidAbsLocal().insert(persistDB, remove, context);
        } else {
            if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyLocalStorage.class)) {
                return getInstanceQueryOffDroidAbsLocal().insert(persistDB, remove, context);
            } else if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline()) {
                    return getInstanceQueryOffDroidAbsWeb().insert(persistDB, restrictions, getPropertiesUtils(context), context);
                }
            } else {
                if (NetWorkUtils.isOnline()) {
                    PersistDB result = getInstanceQueryOffDroidAbsWeb().insert(persistDB, restrictions, getPropertiesUtils(context), context);
                    if (result != null) {

                        if (result instanceof GenericPersist) {
                            GenericPersist genericPersist = (GenericPersist) result;
                            genericPersist.beforeInsert();
                        }

                        getInstanceQueryOffDroidAbsLocal().insert(result, remove, context);
                    }

                    return result;
                } else {
                    return getInstanceQueryOffDroidAbsLocal().insert(persistDB, remove, context);
                }
            }
        }
        return null;
    }

    /**
     * Remoção de um dado.
     *
     * @param persistDB
     */
    public synchronized void remove(PersistDB persistDB) throws Exception {
        remove(persistDB, false);
    }

    public synchronized void removeLocal(PersistDB persistDB) throws Exception {
        remove(persistDB, true);
    }

    private static synchronized void remove(PersistDB persistDB, boolean local) throws Exception {
        if (local) {
            getInstanceQueryOffDroidAbsLocal().remove(persistDB, context);
        } else {
            if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyLocalStorage.class)) {
                getInstanceQueryOffDroidAbsLocal().remove(persistDB, context);
            } else if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline()) {
                    getInstanceQueryOffDroidAbsWeb().remove(persistDB, getPropertiesUtils(context), context);
                }
            } else {
                if (NetWorkUtils.isOnline()) {
                    getInstanceQueryOffDroidAbsWeb().remove(persistDB, getPropertiesUtils(context), context);
                    getInstanceQueryOffDroidAbsLocal().remove(persistDB, context);
                } else {
                    getInstanceQueryOffDroidAbsLocal().remove(persistDB, context);
                }
            }
        }
    }

    /**
     * Atualização de um dado.
     *
     * @param persistDB
     */
    public synchronized PersistDB update(PersistDB persistDB) throws Exception {
        return update(persistDB, false);
    }

    public synchronized PersistDB updateLocal(PersistDB persistDB) throws Exception {
        return update(persistDB, true);
    }

    private synchronized PersistDB update(PersistDB persistDB, boolean local) throws Exception {
        if (local) {
            return getInstanceQueryOffDroidAbsLocal().update(persistDB, context);
        } else {
            if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyLocalStorage.class)) {
                return getInstanceQueryOffDroidAbsLocal().update(persistDB, context);
            } else if (EntityReflection.haAnnotation(persistDB.getClass(), OnlyOnLine.class)) {
                if (NetWorkUtils.isOnline()) {
                    return getInstanceQueryOffDroidAbsWeb().update(persistDB, restrictions, getPropertiesUtils(context), context);
                }
            } else {
                if (NetWorkUtils.isOnline()) {
                    PersistDB result = getInstanceQueryOffDroidAbsWeb().update(persistDB, restrictions, getPropertiesUtils(context), context);
                    if (result != null) {

                        if (result instanceof GenericPersist) {
                            GenericPersist genericPersist = (GenericPersist) result;
                            genericPersist.beforeInsert();
                        }

                        getInstanceQueryOffDroidAbsLocal().update(result, context);
                    }

                    return result;
                } else {
                    return getInstanceQueryOffDroidAbsLocal().update(persistDB, context);
                }
            }
        }
        return null;
    }

    private synchronized String getKey() {
        if (buildUrl == null)
            buildUrl = new BuildUrl();
        return buildUrl.montarURL(BuildOperation.GET, classEntity, restrictions, context, getPropertiesUtils(context));
    }

    private synchronized void saveCache() {
        getPropertiesUtils(context).addProperty(getKey(), System.currentTimeMillis() + "", context);
    }

    public synchronized boolean hasCache(Context context) {
        if (cache > 0) {
            String key = getKey();
            String time = getPropertiesUtils(context).getProperty(key, context);

            if (time != null && !time.isEmpty()) {
                return (System.currentTimeMillis() - Long.parseLong(time)) < cache;
            }
        }
        return false;
    }

    public synchronized static void cleanBD(Context context) {
        propertiesUtils = null;
        getInstanceQueryOffDroidAbsLocal().cleanDB(context);
    }

    public synchronized static boolean isExistsDB(Context context) {
        return getInstanceQueryOffDroidAbsLocal().isExistsDB(context);
    }

    private synchronized static void initializeBroadcast(Context context) {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");

        receivers.add(receiver);

        context.registerReceiver(receiver, mIntentFilter);
        NetWorkUtils.setOnline(NetWorkUtils.isOnline(context));
    }

    public synchronized static boolean isReceiverRegistered(BroadcastReceiver receiver) {
        boolean registered = receivers.contains(receiver);
        return registered;
    }

    public synchronized static void unregisterReceiver() {
        if (isReceiverRegistered(receiver)) {
            receivers.remove(receiver);
            context.unregisterReceiver(receiver);
        }
    }


    private synchronized static QueryOffDroidAbsLocal getInstanceQueryOffDroidAbsLocal() {
        if (queryOffDroidAbsLocal == null) {
            queryOffDroidAbsLocal = new QueryOffDroidLocal();
        }
        return queryOffDroidAbsLocal;
    }

    private synchronized static QueryOffDroidAbsWeb getInstanceQueryOffDroidAbsWeb() {
        if (queryOffDroidAbsWeb == null) {
            queryOffDroidAbsWeb = new QueryOffDroidWebService();
        }
        return queryOffDroidAbsWeb;
    }

    public synchronized static PropertiesUtils getPropertiesUtils(Context context) {
        if (propertiesUtils == null) {
            propertiesUtils = new PropertiesUtils(context);
            propertiesUtils.importPropertyApp(context);
        }
        return propertiesUtils;
    }

}