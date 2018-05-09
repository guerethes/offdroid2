package br.com.guerethes.offdroid.query.business;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.guerethes.offdroid.exception.OffDroidException;
import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.utils.PropertiesUtils;

/**
 * Created by jean on 03/01/17.
 */
abstract public class QueryOffDroidAbsWeb {

    abstract protected PersistDB insert(PersistDB persistDB, List<ElementsRestrictionQuery> restrictions, PropertiesUtils propertiesUtils, Context context) throws Exception;

    abstract protected void remove(PersistDB persistDB, PropertiesUtils propertiesUtils, Context context) throws Exception;

    abstract protected PersistDB update(PersistDB persistDB, List<ElementsRestrictionQuery> restrictions, PropertiesUtils propertiesUtils, Context context) throws Exception;

    abstract protected ArrayList<PersistDB> toList(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context, PropertiesUtils propertiesUtils) throws Exception;

    abstract protected PersistDB toUniqueResult(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context, PropertiesUtils propertiesUtils) throws Exception;

    abstract protected Integer count(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) throws OffDroidException;

}