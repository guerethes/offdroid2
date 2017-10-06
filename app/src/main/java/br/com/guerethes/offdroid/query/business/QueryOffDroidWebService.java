package br.com.guerethes.offdroid.query.business;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import br.com.guerethes.offdroid.exception.OffDroidException;
import br.com.guerethes.offdroid.persist.interfaces.PersistDB;
import br.com.guerethes.offdroid.query.ElementsRestrictionQuery;
import br.com.guerethes.offdroid.query.builders.BuildOperation;
import br.com.guerethes.offdroid.query.builders.BuildUrl;
import br.com.guerethes.offdroid.utils.PropertiesUtils;

/**
 * Created by jean on 26/12/16.
 */
public class QueryOffDroidWebService extends QueryOffDroidAbsWeb {

    private RestTemplate restTemplate = null;
    private ObjectMapper objectMapper = null;
    private BuildUrl buildUrl;

    @Override
    protected PersistDB insert(PersistDB persistDB, PropertiesUtils propertiesUtils, Context context) throws Exception {
        ResponseEntity<String> responseEntity = null;
        try {
            String url = getBuildUrlInstance().montarURL(BuildOperation.POST, persistDB.getClass(), null, context, propertiesUtils);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON + ";" + getBuildUrlInstance().getEncoding(persistDB.getClass())));
            getBuildUrlInstance().montarHeaders(persistDB.getClass(), headers, context, propertiesUtils);

            String json = getObjectMapperInstance().writeValueAsString(persistDB);
            Log.i("POST URL", url);
            Log.i("JSON POST", json);

            HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
            responseEntity = getRestTemplateInstance().exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getBody() != null) {
                if (responseEntity.getStatusCode().value() == HttpStatus.CREATED.value() || responseEntity.getStatusCode().ordinal() == HttpStatus.OK.value())
                    return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), persistDB.getClass());
                else
                    throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
            }
        } catch (HttpClientErrorException e) {
            throw new Exception(e.getStatusCode().value() + "," + e.getStatusText());
        }

        return null;
    }

    @Override
    protected void remove(PersistDB persistDB, PropertiesUtils propertiesUtils, Context context) throws Exception {
        ResponseEntity<String> responseEntity = null;
        try {
            String url = getBuildUrlInstance().montarURL(BuildOperation.DELETE, persistDB.getClass(), null, context, propertiesUtils);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON + ";" + getBuildUrlInstance().getEncoding(persistDB.getClass())));
            getBuildUrlInstance().montarHeaders(persistDB.getClass(), headers, context, propertiesUtils);

            Log.i("REMOVE URL", url);

            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            responseEntity = getRestTemplateInstance().exchange(url + persistDB.getId(), HttpMethod.DELETE, requestEntity, String.class);
            if (responseEntity.getBody() != null) {
                if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                    Log.d("remove", responseEntity.getStatusCode().value() + " - " + persistDB.getId());
                } else {
                    throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
                }
            }
        } catch (HttpClientErrorException e) {
            throw new Exception(e.getStatusCode().value() + "," + e.getStatusText());
        }
    }

    @Override
    protected PersistDB update(PersistDB persistDB, PropertiesUtils propertiesUtils, Context context) throws Exception {
        ResponseEntity<String> responseEntity = null;
        try {
            String url = getBuildUrlInstance().montarURL(BuildOperation.PUT, persistDB.getClass(), null, context, propertiesUtils);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON + ";" + getBuildUrlInstance().getEncoding(persistDB.getClass())));
            getBuildUrlInstance().montarHeaders(persistDB.getClass(), headers, context, propertiesUtils);

            String json = getObjectMapperInstance().writeValueAsString(persistDB);
            Log.i("POST URL", url);
            Log.i("JSON PUT", json);

            HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
            responseEntity = getRestTemplateInstance().exchange(url, HttpMethod.PUT, requestEntity, String.class);

            if (responseEntity.getBody() != null) {
                if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                    return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), persistDB.getClass());
                } else {
                    throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
                }
            }
        } catch (HttpClientErrorException e) {
            throw new Exception(e.getStatusCode().value() + "," + e.getStatusText());
        }

        return null;
    }

    @Override
    protected ArrayList<PersistDB> toList(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context, PropertiesUtils propertiesUtils) throws Exception {
        ResponseEntity<String> responseEntity = null;
        ArrayList<PersistDB> list = null;
        try {
            String url = getBuildUrlInstance().montarURL(BuildOperation.GET, classEntity, restrictions, context, propertiesUtils);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON + ";" + getBuildUrlInstance().getEncoding(classEntity)));
            getBuildUrlInstance().montarHeaders(classEntity, headers, context, propertiesUtils);

            Log.i("URL toList()", url);

            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            responseEntity = getRestTemplateInstance().exchange(url, HttpMethod.GET, requestEntity, String.class);

            if (responseEntity.getBody() != null) {
                if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                    String result = responseEntity.getBody().toString();
                    list = new ArrayList<>();
                    if (result != null && !result.isEmpty()) {
                        //Is list??
                        if (result.charAt(0) == '[') {
                            JavaType type = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, classEntity);
                            list = getObjectMapperInstance().readValue(result, type);
                        } else {
                            list.add(getObjectMapperInstance().readValue(result, classEntity));
                        }
                    }
                } else {
                    throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
                }
            }
        } catch (HttpClientErrorException e) {
            throw new Exception(e.getStatusCode().value() + "," + e.getStatusText());
        }
        return list;
    }

    @Override
    protected PersistDB toUniqueResult(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context, PropertiesUtils propertiesUtils) throws Exception {
        List<PersistDB> list = toList(classEntity, restrictions, context, propertiesUtils);
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }

    @Override
    protected Integer count(Class<PersistDB> classEntity, List<ElementsRestrictionQuery> restrictions, Context context) throws OffDroidException {
        throw new OffDroidException("Não implementado");
    }

    private RestTemplate getRestTemplateInstance() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        }
        return restTemplate;
    }

    private ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    private BuildUrl getBuildUrlInstance() {
        if (buildUrl == null) {
            buildUrl = new BuildUrl();
        }
        return buildUrl;
    }


}