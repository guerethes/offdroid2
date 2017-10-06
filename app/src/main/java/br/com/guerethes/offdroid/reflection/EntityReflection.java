package br.com.guerethes.offdroid.reflection;

import java.lang.annotation.Annotation;

import br.com.guerethes.offdroid.annotation.server.Delete;
import br.com.guerethes.offdroid.annotation.server.Encoding;
import br.com.guerethes.offdroid.annotation.server.GET;
import br.com.guerethes.offdroid.annotation.server.Headers;
import br.com.guerethes.offdroid.annotation.server.POST;
import br.com.guerethes.offdroid.annotation.server.PUT;
import br.com.guerethes.offdroid.annotation.server.Path;

public final class EntityReflection {

	public static boolean haAnnotation(Class<?> targetClass, Class annotation) {
		return targetClass.isAnnotationPresent(annotation);
	}

	public static Annotation getAnnotation(Class<?> targetClass, Class annotation) {
		return targetClass.getAnnotation(annotation);
	}

	public static String getPathName(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(Path.class) ? targetClass
			.getAnnotation(Path.class).value() : targetClass.getSimpleName().toLowerCase());
	}

	public static String getHeader(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(Headers.class) ? targetClass
				.getAnnotation(Headers.class).value() : "");
	}

	public static String getPOST(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(POST.class) ? targetClass
				.getAnnotation(POST.class).value() : "url_base");
	}

	public static String getPUT(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(PUT.class) ? targetClass
				.getAnnotation(PUT.class).value() : "url_base");
	}

	public static String getDELETE(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(Delete.class) ? targetClass
				.getAnnotation(Delete.class).value() : "url_base");
	}

	public static String getGET(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(GET.class) ? targetClass
				.getAnnotation(GET.class).value() : "url_base");
	}

	public static String getEncoding(Class<?> targetClass) {
		return (targetClass.isAnnotationPresent(Encoding.class) ? targetClass
				.getAnnotation(Encoding.class).value() : "utf-8");
	}

}