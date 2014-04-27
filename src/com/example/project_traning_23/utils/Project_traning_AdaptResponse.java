package com.example.project_traning_23.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.project_traning_23.utils.Project_traning_Model;


public class Project_traning_AdaptResponse<T extends Project_traning_Model> {

	private final static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public List<T> adaptToList(final String response, final Class<T> className) {
		try {
			final JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, className);
			return objectMapper.readValue(response, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public T adaptToModel(final String response, final Class<T> className) {
		try {
			return objectMapper.readValue(response, className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toJson(final Project_traning_Model model) throws JsonProcessingException
	{
		return objectMapper.writeValueAsString(model);
	}
}
