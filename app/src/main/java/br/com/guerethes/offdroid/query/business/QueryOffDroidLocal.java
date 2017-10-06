package br.com.guerethes.offdroid.query.business;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by jean on 26/12/16.
 */
public class QueryOffDroidLocal extends QueryOffDroidAbsLocal {

    //Variável do arquivo do Banco de Dados
    protected static String bd = "/offdroid.db";
    private static ObjectContainer db = null;

    private static String getPath(Context context) {
        PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return p.applicationInfo.dataDir;
    }

    protected static ObjectContainer getDb(Context context) {
        if (db == null) {
            db = Db4o.openFile(getPath(context) + bd);
        }
        return db;
    }

    @Override
    protected boolean isExistsDB(Context context) {
        File file = new File(getPath(context) + bd);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }

    @Override
    protected void cleanDB(Context context) {
        getDb(context).close();
        File file = new File(getPath(context) + bd);
        boolean deleted = file.delete();
        if (deleted) {
            db = null;
            Log.i("DB", "DB Excluido");
        } else {
            Log.i("DB", "Erro na exclusão");
        }
    }

    /**
     * Inserção dos dados.
     *
     * @param object
     */
    @Override
    protected PersistDB insert(PersistDB object, Context context) {
        try {

            Query q = getDb(context).query();
            q.constrain(object.getClass());
            q.descend("id").constrain(object.getId()).equal();
            ObjectSet set = q.execute();

            if (!set.isEmpty()) {
                PersistDB persiste = (PersistDB) set.next();
                remove(persiste, context);
            }

            getDb(context).set(object);
            getDb(context).commit();

            Log.i("INSERT", object.getClass().getSimpleName());
            return object;
        } catch (Exception e) {
            getDb(context).rollback();
        }
        return null;
    }

    /**
     * Consulta ao banco ou serviço
     *
     * @return
     * @throws Exception
     */
    @Override
    protected ArrayList<PersistDB> toList(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) {
        Query q = null;
        q = getDb(context).query();
        q.constrain(classEntity);

        for (ElementsRestrictionQuery query : restrictions) {
            query.toDDL(q);
        }

        ObjectSet<PersistDB> result = q.execute();

        ArrayList<PersistDB> list = new ArrayList<>();

        for (PersistDB data : result)
            list.add(data);

        Log.i("toList()", classEntity.getSimpleName() + " - Size: " + list.size());
        return list;
    }

    @Override
    protected PersistDB toUniqueResult(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) {
        Query q = null;
        q = getDb(context).query();
        q.constrain(classEntity);

        for (ElementsRestrictionQuery query : restrictions) {
            query.toDDL(q);
        }

        ObjectSet set = q.execute();

        if (!set.isEmpty()) {
            PersistDB persiste = (PersistDB) set.next();
            Log.i("toUniqueResult()", persiste.getClass().getSimpleName());
            return persiste;
        }

        return null;
    }

    @Override
    protected PersistDB login(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) {
        return toUniqueResult(classEntity, restrictions, context);
    }

    @Override
    protected Integer count(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) {
        Query q = null;
        q = getDb(context).query();
        q.constrain(classEntity);

        for (ElementsRestrictionQuery query : restrictions) {
            query.toDDL(q);
        }

        ObjectSet set = q.execute();

        return set.size();
    }

    @Override
    protected void remove(PersistDB object, Context context) {
        try {
            getDb(context).delete(object);
            getDb(context).commit();
            Log.i("Delete", object.getClass().getSimpleName());
        } catch (Exception e) {
            getDb(context).rollback();
        }
    }

    @Override
    protected PersistDB update(PersistDB object, Context context) {
        try {
            Query q = getDb(context).query();
            q.constrain(object.getClass());
            q.descend("id").constrain(object.getId()).equal();
            ObjectSet set = q.execute();

            if (!set.isEmpty()) {
                PersistDB persiste = (PersistDB) set.next();
                remove(persiste, context);
            }
            getDb(context).set(object);
            getDb(context).commit();

            Log.i("UPDATE", object.getClass().getSimpleName());
            return object;
        } catch (Exception e) {
            getDb(context).rollback();
        }

        return null;
    }

}