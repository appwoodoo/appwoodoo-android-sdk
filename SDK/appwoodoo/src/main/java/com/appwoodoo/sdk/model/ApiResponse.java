package com.appwoodoo.sdk.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiResponse {

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static ApiResponse parseJSON(String jsonString) {
		if (jsonString == null) {
			return null;
		}

		ApiResponse ar = new ApiResponse();
		try {
			JSONObject json = new JSONObject(jsonString);
			ar.setStatus(json.optInt("status"));
            return ar;

		} catch(Exception e) {
			return null;
		}
    }
	
}
