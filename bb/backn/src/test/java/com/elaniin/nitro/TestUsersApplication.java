package com.elaniin.nitro;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.elaniin.nitro.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
public class TestUsersApplication {
	
	// No validate
	@Value("${server.port}")
	private int portServer;
	private RestTemplate restTemplate;
	private JSONObject token;
	
	/**
	 * Initializer Data
	 */
	public TestUsersApplication() {
		System.out.println("Port Server: " + portServer);
		restTemplate = new RestTemplate();
		try {
			token = getProcessToken();
		} catch (JSONException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get Data Access Testing
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	@Test
	@Order(1)
	public void testLogInSucces() throws URISyntaxException, JSONException {
		JSONObject object = token;
		System.out.println("Objecto JSON: " + object.toString());
		Assert.isTrue(!object.getString("token").isEmpty(), "<Error>No se obtuvo datos de Acceso</Error>");
		Assert.hasLength(object.getString("token"), "<Error>No se Obtuvo Token</Error>");
	}	
	
	@Test
	@Order(2)
	public void testGetUserSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/users/read/1";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Usuario</Error>");

		try {
			List<User> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, User.class));
			Assert.isTrue(list.size() > 0, "<Error>No se logro obtener Json</Error>");
			Assert.hasText("token", result.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}
	
	@Test
	@Order(3)
	public void testCreateUserSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/users/create";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		System.out.println("<Create>Token: " + getToken().getString("token"));
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("username", "testing");
		map.put("password", "12345");
		map.put("enabled", "true");
		map.put("name", "testing");
		map.put("lastname", "");
		map.put("email", "testing@elaniin.com");
		map.put("attempts", "null");
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = null;
		try {
			result = restTemplate.postForEntity(uri, request, String.class);
		} catch (HttpClientErrorException hce) {
			result = null;
		}
		System.out.println("body Response: " + result.getBody());
		System.out.println("Status: " + result.getStatusCodeValue());
		// Convert 
		try {
			List<String> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, String.class));
			System.out.println("Lista: " + list.size());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		JSONObject object = new JSONObject(result.getBody());
		
		// Test
		//Assert.state(result.getStatusCodeValue() != 404, "<Error>Dato duplicado</Error>");
		Assert.isTrue(object.length()>0, "<Error>No se Obtuvo ningun dato</Error>");
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		// Search user
		User userTemp = this.SearchUsernameTest("testing");
		Assert.isTrue(!userTemp.getUsername().isEmpty(), "<Error>No existe usuario</Error>");
	}
	
	@Test
	@Order(4)
	public void testUserUpdateSuccess() throws URISyntaxException, JSONException {
		User user = this.SearchUsernameTest("testing");
		System.out.println("<Update>User Get:" + user.toString());
		final String baseUrl = "http://localhost:8080/api/v1/users/update/" + user.getId();
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();
		
		map.put("id", user.getId());
		map.put("username", user.getUsername());
		map.put("enabled", user.getEnabled());
		map.put("name", "elaniin");
		map.put("lastname", "testing");
		map.put("email", user.getEmail());
		map.put("attempts", user.getAttempts());
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Usuario</Error>");

		try {
			User foundUser = new ObjectMapper().readValue(result.getBody(), User.class);
			System.out.println("Se encontro el usuario: " + foundUser.getUsername());
			Assert.isTrue(foundUser.getName().equals("elaniin"), "<Error>No se logro obtener actualizar el Nombre</Error>");
			Assert.isTrue(foundUser.getLastname().equals("testing"), "<Error>No se logro obtener actualizar el Apellido</Error>");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}
	
	@Test
	@Order(5)
	public void testUserDeleteSuccess() throws URISyntaxException, JSONException {
		User user = this.SearchUsernameTest("testing");
		if (user == null) {
			System.out.println("<Error>No exists Data</Error>");
			return;
		}
		System.out.println("<Delete>User Get:" + user.toString());
		final String baseUrl = "http://localhost:8080/api/v1/users/delete/" + user.getId();
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(204), String.valueOf(result.getStatusCodeValue()));
		//Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Usuario</Error>");
	}	
	
	/**
	 * Get All Users Testing
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	@Test
	@Order(6)
	public void testGetUsersAllListSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/users/all";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo datos del Usuario</Error>");

		try {
			List<User> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, User.class));
			System.out.println("List Users size: " +  list.size());
			Assert.isTrue(list.size() > 0, "<Error>No se logro obtener Json</Error>");
			Assert.hasText("token", result.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}
	
	public JSONObject getProcessToken() throws JSONException, URISyntaxException {
		final String baseUrl = "http://localhost:8080/api/v1/login";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Map<String, Object> map = new HashMap<>();
		map.put("username", "admin");
		map.put("password", "12345");
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
		System.out.println("body Response: " + result.getBody());
		// Convert 
		try {
			List<String> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, String.class));
			System.out.println("Lista: " + list.size());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		JSONObject object = new JSONObject(result.getBody());
		System.out.println(object.get("token"));
		return object;
	}
	
	public User SearchUsernameTest(String username) throws URISyntaxException, JSONException {
		User userTemp = new User();
		final String baseUrl = "http://localhost:8080/api/v1/users/search/username/" + username;
		URI uri = new URI(baseUrl);
		System.out.println("URL: " + uri);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		// Body empty
		Map<String, Object> map = new HashMap<>();
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
	
		System.out.println("Code Status: " + result.getStatusCodeValue());
		try {
			JSONObject object = new JSONObject(result.getBody());
			User foundUser = new ObjectMapper().readValue(object.getString("details"), User.class);
			userTemp = foundUser;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
		
		return userTemp;
	}
	
	public JSONObject getToken() {
		return token;
	}

	public void setToken(JSONObject token) {
		this.token = token;
	}
}