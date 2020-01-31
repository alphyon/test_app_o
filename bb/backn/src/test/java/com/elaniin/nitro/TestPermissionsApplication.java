package com.elaniin.nitro;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import com.elaniin.nitro.entity.Permission;
import com.elaniin.nitro.entity.Role;
import com.elaniin.nitro.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
public class TestPermissionsApplication {
	
	private RestTemplate restTemplate;
	private JSONObject token;
	
	public TestPermissionsApplication() {
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
	public void testGetPermissionSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/permissions/read/1";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Permiso</Error>");

		try {
			List<Permission> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Permission.class));
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
	public void testCreatePermissionSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/permissions/create";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		System.out.println("<Create>Token: " + getToken().getString("token"));
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "PERM_TESTING");
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
		Permission permissionTemp = this.searchPermissionNameTest("PERM_TESTING");
		Assert.isTrue(!permissionTemp.getName().isEmpty(), "<Error>No existe usuario</Error>");
	}	
	
	@Test
	@Order(4)
	public void testPermissionUpdateSuccess() throws URISyntaxException, JSONException {
		Permission permission;
		permission = this.searchPermissionNameTest("PERM_TESTING");
		
		System.out.println("<Update>Permission Get:" + permission.toString());
		final String baseUrl = "http://localhost:8080/api/v1/permissions/update/" + permission.getId();
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();
		
		map.put("id", permission.getId());
		map.put("name", "PERM_ELANIIN");
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Role</Error>");

		try {
			Permission foundPermission = new ObjectMapper().readValue(result.getBody(), Permission.class);
			System.out.println("Se encontro el role: " + foundPermission.getName());
			Assert.isTrue(foundPermission.getName().equals("PERM_ELANIIN"), "<Error>No se logro obtener el Nombre</Error>");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}
	
	@Test
	@Order(5)
	public void testPermissionDeleteSuccess() throws URISyntaxException, JSONException {
		Permission permission = this.searchPermissionNameTest("PERM_ELANIIN");
		if (permission == null) {
			System.out.println("<Error>No exists Data</Error>");
			return;
		}
		System.out.println("<Delete>Permission Get:" + permission.toString());
		final String baseUrl = "http://localhost:8080/api/v1/permissions/delete/" + permission.getId();
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
	 * Get All Permissions Testing
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	@Test
	@Order(6)
	public void testGetPermissionsAllListSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/permissions/all";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo datos del Permiso</Error>");

		try {
			List<User> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, User.class));
			System.out.println("List Permissions size: " +  list.size());
			Assert.isTrue(list.size() > 0, "<Error>No se logro obtener Json</Error>");
			Assert.hasText("token", result.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}	
	
	public Permission searchPermissionNameTest(String name) throws JSONException, URISyntaxException {
		Permission permissionTemp = new Permission();
		final String baseUrl = "http://localhost:8080/api/v1/permissions/search/" + name;
		URI uri = new URI(baseUrl);
		System.out.println("<Search-Permission> Uri: " + uri);
		System.out.println("URL: " + uri);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		// Body empty
		Map<String, Object> map = new HashMap<>();
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
	
		System.out.println("Code Status: " + result.getStatusCodeValue());
		try {
			Permission foundPermission = new ObjectMapper().readValue(result.getBody(), Permission.class);
			permissionTemp = foundPermission;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
		
		return permissionTemp;		
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

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public JSONObject getToken() {
		return token;
	}

	public void setToken(JSONObject token) {
		this.token = token;
	}
}
