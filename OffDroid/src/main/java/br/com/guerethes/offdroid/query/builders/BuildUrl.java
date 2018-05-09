package br.com.guerethes.offdroid.query.builders;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import br.com.guerethes.offdroid.annotation.server.Headers;
import br.com.guerethes.offdroid.annotation.server.Path;
import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.enun.EstrategiaPath;
import br.com.guerethes.offdroid.query.order.OrderByAsc;
import br.com.guerethes.offdroid.query.order.OrderByDesc;
import br.com.guerethes.offdroid.query.pagination.Limit;
import br.com.guerethes.offdroid.query.pagination.OffSet;
import br.com.guerethes.offdroid.reflection.EntityReflection;
import br.com.guerethes.offdroid.utils.PropertiesUtils;

/**
 * Created by victor on 25/08/17.
 */

public class BuildUrl {

    public String montarURL(BuildOperation buildOperation, Class<?> classEntity, List<ElementsRestrictionQuery> restrictions, Context context, PropertiesUtils propertiesUtils) {
        String url = null;

        if (buildOperation.ordinal() == BuildOperation.GET.ordinal()) {
            url = getGetURL((Class<? extends PersistDB>) classEntity, context, propertiesUtils);
        }
        if (buildOperation.ordinal() == BuildOperation.POST.ordinal()) {
            url = getPostURL((Class<? extends PersistDB>) classEntity, context, propertiesUtils);
        }
        if (buildOperation.ordinal() == BuildOperation.PUT.ordinal()) {
            url = getPutURL((Class<? extends PersistDB>) classEntity, context, propertiesUtils);
        }
        if (buildOperation.ordinal() == BuildOperation.DELETE.ordinal()) {
            url = getDeleteURL((Class<? extends PersistDB>) classEntity, context, propertiesUtils);
        }

        url += montarPath(url, classEntity);

        if (restrictions != null) {

            boolean firstQuery = true;
            Vector<String> mapOrderAsc = new Vector<>();
            Vector<String> mapOrderDesc = new Vector<>();

            for (int index = 0; index < restrictions.size(); index++) {
                if (restrictions.get(index).getEstrategiaPath() != null) {
                    if (restrictions.get(index).getEstrategiaPath().ordinal() == EstrategiaPath.PATH_PARAM.ordinal()) {
                        url += "/" + restrictions.get(index).getValue();
                    } else if (restrictions.get(index).getEstrategiaPath().ordinal() == EstrategiaPath.QUERY_PARAM.ordinal()) {
                        if (firstQuery) {
                            url += "?" + convertFieldToJsonProperty(classEntity, restrictions.get(index).getField()) + "=" + restrictions.get(index).getValue();
                            firstQuery = false;
                        } else {
                            Object value = restrictions.get(index).getValue();

                            if (value instanceof List) {
                                List<Object> list = (List<Object>) value;
                                Object object = list.get(0);
                                for (int i = 1; i < list.size(); i++) {
                                    object += "," + list.get(i);
                                }
                                value = object;
                            }

                            url += "&" + convertFieldToJsonProperty(classEntity, restrictions.get(index).getField()) + "=" + value;
                        }
                    }
                }

                if (restrictions.get(index) instanceof OrderByAsc) {
                    mapOrderAsc.add(convertFieldToJsonProperty(classEntity, restrictions.get(index).getField()));
                }

                if (restrictions.get(index) instanceof OrderByDesc) {
                    mapOrderDesc.add(convertFieldToJsonProperty(classEntity, restrictions.get(index).getField()));
                }

                if (restrictions.get(index) instanceof Limit) {
                    if (firstQuery) {
                        url += "?limit=" + restrictions.get(index).getValue();
                        firstQuery = false;
                    } else {
                        url += "&limit=" + restrictions.get(index).getValue();
                    }
                }

                if (restrictions.get(index) instanceof OffSet) {
                    if (firstQuery) {
                        url += "?offset=" + restrictions.get(index).getValue();
                        firstQuery = false;
                    } else {
                        url += "&offset=" + restrictions.get(index).getValue();
                    }
                }
            }

            if (!mapOrderAsc.isEmpty()) {
                if (firstQuery) {
                    url += "?order-asc=";
                    firstQuery = false;
                } else {
                    url += "&order-asc=";
                }

                for (int i = 0; i < mapOrderAsc.size(); i++) {
                    url += mapOrderAsc.get(i);
                    if (!(mapOrderAsc.size() == (i + 1)))
                        url += ",";
                }
            }

            if (!mapOrderDesc.isEmpty()) {
                if (firstQuery) {
                    url += "?order-desc=";
                    firstQuery = false;
                } else {
                    url += "&order-desc=";
                }

                for (int i = 0; i < mapOrderDesc.size(); i++) {
                    url += mapOrderDesc.get(i);
                    if (!(mapOrderDesc.size() == (i + 1)))
                        url += ",";
                }
            }
        }

        return url;
    }

    private String montarPath(String url, Class<?> classEntity) {

        if (EntityReflection.haAnnotation(classEntity, Path.class)) {
            url = url.charAt(url.length() - 1) == '/' ? EntityReflection.getPathName(classEntity) : "/" + EntityReflection.getPathName(classEntity);
        } else {
            url = url.charAt(url.length() - 1) == '/' ? classEntity.getSimpleName() : "/" + classEntity.getSimpleName();
        }
        return url;
    }

    private String getPostURL(Class<? extends PersistDB> clazz, Context context, PropertiesUtils propertiesUtils) {
        return propertiesUtils.getProperty(EntityReflection.getPOST(clazz), context);
    }

    private String getPutURL(Class<? extends PersistDB> clazz, Context context, PropertiesUtils propertiesUtils) {
        return propertiesUtils.getProperty(EntityReflection.getPUT(clazz), context);
    }

    private String getGetURL(Class<? extends PersistDB> clazz, Context context, PropertiesUtils propertiesUtils) {
        return propertiesUtils.getProperty(EntityReflection.getGET(clazz), context);
    }

    private String getDeleteURL(Class<? extends PersistDB> clazz, Context context, PropertiesUtils propertiesUtils) {
        return propertiesUtils.getProperty(EntityReflection.getDELETE(clazz), context);
    }

    public String getEncoding(Class<? extends PersistDB> clazz) {
        return "charset=" + EntityReflection.getEncoding(clazz);
    }

    public void montarHeaders(Class clazz, HttpHeaders headers, Context context, PropertiesUtils propertiesUtils) {
        if (EntityReflection.haAnnotation(clazz, Headers.class)) {
            String[] values = EntityReflection.getHeader(clazz).split(",");
            for (int i = 0; i < values.length; i++) {
                headers.add(values[i].trim(), propertiesUtils.getProperty(values[i].trim(), context));
            }
        }
    }

    private String convertFieldToJsonProperty(Class<?> clazz, String atributo) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(atributo);
            field.setAccessible(true);
            JsonProperty column = field.getAnnotation(JsonProperty.class);
            if (column != null) {
                return column.value();
            }
        } catch (NoSuchFieldException e) {
            return atributo;
        }

        return atributo;
    }

}
