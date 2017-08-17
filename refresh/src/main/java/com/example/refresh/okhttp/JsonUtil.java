package com.example.refresh.okhttp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;



public class JsonUtil {

	private static ObjectMapper mapper;



	static{
		mapper = new ObjectMapper();
		configureFailOnUnknownProperties(mapper, false);

	}

	public static void configureFailOnUnknownProperties(ObjectMapper mapper, boolean enabled) {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, enabled);
		//允许出现特殊字符和转义符
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
		//允许值没有双引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}



	public static ObjectMapper getObjectMapper() {
		return mapper;
	}


	public static JsonNode createJsonNode(String json) {
		try {
			json = check(json);
			return mapper.readTree(json);
		} catch (Exception e) {
			System.err.println("create json node failed : " + e);
		}
		return null;
	}

	public static String check(String jsonString) {
		if (jsonString != null && jsonString.startsWith("\ufeff")) {
			jsonString = jsonString.substring(1);
		}
		return jsonString;
	}


	/**
	 * 可用于类型转换
	 * @param json
	 * @param pojo
	 * @param path
	 * @return
	 */

	public static <T> T read(String json, Class<T> pojo, String... path) {
		try {
			json = check(json);
			JsonNode jsonNode = mapper.readTree(json);
			return read(jsonNode, pojo, path);
		} catch (Exception e) {
			e.printStackTrace();
			// JsonParseException, JsonMappingException, IOException
			System.err.println("read json failed : " + e);
		}
		return null;
	}



	public static <T> T read(JsonNode jsonNode, Class<T> pojo, String... path) {
		try {
			for (String node : path) {
				jsonNode = jsonNode.path(node);
			}
			String jsonNodeString = jsonNode.toString();
			if (jsonNode.getNodeType() == JsonNodeType.ARRAY) {
				JavaType jt = getCollectionType(List.class, pojo);
				return mapper.readValue(jsonNodeString, jt);
			} else {
				return mapper.readValue(jsonNodeString, pojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("read json failed : " + e);
		}
		return null;
	}


	/**
	 * @param collectionClass
	 * @param elementClasses
	 * @return JavaType
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		TypeFactory typeFactory = TypeFactory.defaultInstance();
		return typeFactory.constructParametricType(collectionClass, elementClasses);
	}




	@SuppressWarnings("unchecked")
	public static <T> List<T> readArray(JsonNode jsonNode, Class<T> pojo, String... path) {
		return (List<T>) read(jsonNode, pojo, path);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readArray(String json, Class<T> pojo, String... path) {
		return (List<T>) read(json, pojo, path);
	}


	//--------------------------------对应基本类型转换-------------------------------------------//
	public static Boolean readBoolean(JsonNode jsonNode, String... path) {
		return read(jsonNode, Boolean.class, path);
	}
	public static Boolean readBoolean(String json, String... path) {
		return read(json, Boolean.class, path);
	}

	public static Integer readInt(String json, String... path) {
		return read(json, Integer.class, path);
	}
	public static Integer readInt(JsonNode jsonNode, String... path) {
		return read(jsonNode, Integer.class, path);
	}

	public static Double readDouble(JsonNode jsonNode, String... path) {
		return read(jsonNode, Double.class, path);
	}
	public static Double readDouble(String json, String... path) {
		return read(json, Double.class, path);
	}

	public static String readString(String json, String... path) {
		return read(json, String.class, path);
	}
	public static String readString(JsonNode jsonNode, String... path) {
		return read(jsonNode, String.class, path);
	}

}
