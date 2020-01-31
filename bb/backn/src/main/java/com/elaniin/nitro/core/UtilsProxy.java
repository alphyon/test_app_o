package com.elaniin.nitro.core;

import static com.elaniin.nitro.util.Constans.INTEGRATOR_BASE_COMMAND;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UtilsProxy {
	private static Logger logger = Logger.getLogger(UtilsProxy.class.getName());

	private static String headerType = "\"Content-Type: application/json\"";

	public boolean connectUrl(String url, Integer port, Integer timeout) {
		int aPort = port != null ? port : 80;
		int aTimeOut = timeout != null ? timeout : 30;
		boolean result = false;
		try {
			HttpURLConnection connection = null;
			URL aUrl = new URL(url);
			connection = (HttpURLConnection) aUrl.openConnection();
//			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//				throw new IOException("ERROR CONNECT");
//			}
			result = true;
		} catch (IOException e) {
			logger.warning("NOT CONNECT".concat(e.getMessage()));
			e.printStackTrace();
		}
		return result;
	}

	public Object getResponseFromUrl(String testUrl, String method, JsonObject json) throws Exception {
		if (!connectUrl(testUrl, null, null)) {
			throw new Exception("NOT CONNECTED");
		}

		String aMethod = method != null ? method : "GET";
		StringBuilder command = new StringBuilder();
		command.append(INTEGRATOR_BASE_COMMAND);
		command.append(" ").append(aMethod).append(" ").append(testUrl).append(" ");
		command.append("-H ").append(headerType);
		
		if (json != null) {
			command.append(" -d ").append(json);
		}
//		String command = INTEGRATOR_BASE_COMMAND.concat(" ").concat(aMethod).concat(" ").concat(testUrl);
		try {
			Process process = Runtime.getRuntime().exec(command.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			System.err.println(sb.toString());
			JsonParser parser = new JsonParser();
			JsonObject jsonElement = parser.parse(sb.toString()).getAsJsonObject();
			if (jsonElement == null) {
				throw new Exception("NULL_JSON");
			}
			return jsonElement;
		} catch (Exception e) {
			e.printStackTrace();
			return new Object();
		}
	}

	public JSONObject generateObjectFromResponse(JsonElement jsonString) {
		try {
			return new JSONObject(jsonString.getAsString());
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
}
