package br.com.guerethes.offdroid.query.business;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.guerethes.offdroid.exception.OffDroidException;
import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;

/**
 * Created by jean on 03/01/17.
 */
abstract public class QueryOffDroidAbsLocal {

    abstract protected PersistDB insert(PersistDB persistDB, Context context);

    abstract protected void remove(PersistDB persistDB, Context context);

    abstract protected PersistDB update(PersistDB persistDB, Context context);

    abstract protected ArrayList<PersistDB> toList(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context);

    abstract protected PersistDB toUniqueResult(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context);

    abstract protected PersistDB login(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context);

    abstract protected Integer count(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) throws OffDroidException;

    abstract protected void cleanDB(Context context);

    abstract protected boolean isExistsDB(Context context);

}