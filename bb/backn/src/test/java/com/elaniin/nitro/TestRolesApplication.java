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

import com.elaniin.nitro.entity.Role;
import com.elaniin.nitro.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
public class TestRolesApplication {
	
	private RestTemplate restTemplate;
	private JSONObject token;
	
	public TestRolesApplication() {
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
	public void testGetRoleSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/roles/read/1";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Role</Error>");

		try {
			List<Role> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Role.class));
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
	public void testCreateRoleSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/roles/create";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		System.out.println("<Create>Token: " + getToken().getString("token"));
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "ROLE_TESTING");
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
		Role roleTemp = this.searchRoleNameTest("ROLE_TESTING");
		Assert.isTrue(!roleTemp.getName().isEmpty(), "<Error>No existe usuario</Error>");
	}
	
	@Test
	@Order(4)
	public void testRoleUpdateSuccess() throws URISyntaxException, JSONException {
		Role role;
		role = this.searchRoleNameTest("ROLE_TESTING");
		
		System.out.println("<Update>Role Get:" + role.toString());
		final String baseUrl = "http://localhost:8080/api/v1/roles/update/" + role.getId();
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();
		
		map.put("id", role.getId());
		map.put("name", "ROLE_ELANIIN");
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo Dato del Role</Error>");

		try {
			Role foundRole = new ObjectMapper().readValue(result.getBody(), Role.class);
			System.out.println("Se encontro el role: " + foundRole.getName());
			Assert.isTrue(foundRole.getName().equals("ROLE_ELANIIN"), "<Error>No se logro obtener el Nombre</Error>");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}	
	
	@Test
	@Order(5)
	public void testRoleDeleteSuccess() throws URISyntaxException, JSONException {
		Role role = this.searchRoleNameTest("ROLE_ELANIIN");
		if (role == null) {
			System.out.println("<Error>No exists Data</Error>");
			return;
		}
		System.out.println("<Delete>Role Get:" + role.toString());
		final String baseUrl = "http://localhost:8080/api/v1/roles/delete/" + role.getId();
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
	 * Get All Roles Testing
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	@Test
	@Order(6)
	public void testGetRolesAllListSuccess() throws URISyntaxException, JSONException {
		final String baseUrl = "http://localhost:8080/api/v1/roles/all";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + getToken().get("token"));
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		
		System.out.println("Code Status: " + result.getStatusCodeValue());
		Assert.hasText(String.valueOf(200), String.valueOf(result.getStatusCodeValue()));
		Assert.isTrue(result.getBody().length() > 0, "<Error>No se obtuvo datos del Role</Error>");

		try {
			List<User> list = new ObjectMapper().readValue(result.getBody(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, User.class));
			System.out.println("List Roles size: " +  list.size());
			Assert.isTrue(list.size() > 0, "<Error>No se logro obtener Json</Error>");
			Assert.hasText("token", result.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
	}
	
	public Role searchRoleNameTest(String name) throws JSONException, URISyntaxException {
		Role roleTemp = new Role();
		final String baseUrl = "http://localhost:8080/api/v1/roles/search/role/" + name;
		URI uri = new URI(baseUrl);
		System.out.println("<Search-Role> Uri: " + uri);
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
			Role foundRole = new ObjectMapper().readValue(object.getString("details"), Role.class);
			roleTemp = foundRole;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Data: " + result.getBody());
		System.out.println("Longitud: " + result.getBody().length());
		
		return roleTemp;		
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
