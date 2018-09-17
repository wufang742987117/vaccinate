package com.thinkgem.jeesite.modules.sys.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * Json 操作
 * 
 * @author ngh
 *
 */
@SuppressWarnings("rawtypes")
public final class JsonUtils {

	private static ObjectMapper om;

	static {
		om = new ObjectMapper();
		// 转json忽略空或NULL字段
		om.setSerializationInclusion(Include.NON_NULL);
		// 转换时忽略bean中不存在的字段
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	private JsonUtils() {
	}

	public static String toJson(Object value) {
		try {
			return om.writeValueAsString(value);
		} catch (JsonProcessingException e) {
		}
		return StringUtils.EMPTY;
	}

	public static JsonNode toJsonNode(String json) {
		try {
			return om.readTree(json);
		} catch (IOException e) {
		}
		return null;
	}

	public static <T> T toBean(String json, Class<T> clazz) {
		try {
			return om.readValue(json, clazz);
		} catch (IOException e) {
		}
		return null;
	}

	public static <T> T toCollection(String content, Class<? extends Collection> collectionClass,
			Class<?> elementClass) {
		try {
			return om.readValue(content, collectionType(collectionClass, elementClass));
		} catch (Exception e) {
		}
		return null;
	}

	public static <T> T toMap(String content, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
		try {
			return om.readValue(content, om.getTypeFactory().constructMapType(mapClass, keyClass, valueClass));
		} catch (Exception e) {
		}
		return null;
	}

	public static <T> T toMap(String content, Class<? extends Map> mapClass, JavaType keyType, JavaType valueType) {
		try {
			return om.readValue(content, om.getTypeFactory().constructMapType(mapClass, keyType, valueType));
		} catch (Exception e) {
		}
		return null;
	}

	public static CollectionType collectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
		return om.getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}

	public static JavaType simpleType(Class<?> rawType, JavaType[] parameterTypes) {
		return om.getTypeFactory().constructType(new TypeReference<String>() {
		});
	}

	public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
		return om.convertValue(fromValue, toValueType);
	}

	public static <T> T convertValue(Object fromValue, JavaType toValueType) {
		return om.convertValue(fromValue, toValueType);
	}

	public static <T> T convertValue(Object fromValue, TypeReference<T> toValueType) {
		return om.convertValue(fromValue, toValueType);
	}

}
