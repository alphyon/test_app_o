package com.elaniin.nitro.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.core.UtilsProxy;
import com.elaniin.nitro.dto.InterpretersDTO;
import com.elaniin.nitro.dto.OrchestratorDTO;
import com.elaniin.nitro.dto.ProxyDTO;
import com.elaniin.nitro.entity.Proxy;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.InterpretersService;
import com.elaniin.nitro.service.OrchestatorService;
import com.elaniin.nitro.service.ProxyService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(value = "TEST PROXY CONTROLLER", description = "CRUD for manage all request to External Proxy")
@RestController
@RequestMapping("/api/v1/testproxy")
public class ProxyTestController extends GenericBase {

	UtilsProxy utilsProxy = new UtilsProxy();

	@Autowired
	private ProxyService proxyService;

	@Autowired
	private InterpretersService interpreterService;
	
	@Autowired
	private OrchestatorService orchestratorService;

	@ApiOperation(value = "Get Interpreter Info to build dinamic url on frontend")
	@GetMapping("interpretername/{id}")
	public ResponseEntity<Object> getInterpreterInfo(@ApiParam(value = "Interpreter Id to get all info", required = true, example = "1") @PathVariable Long id, WebRequest request)
			throws JsonProcessingException {
		InterpretersDTO interpretersDTO;
		try {
			interpretersDTO = interpreterService.getInfoInterpreters(id);
			interpretersDTO.setName(interpretersDTO.getName().replace(" ", "").toLowerCase()); 
		} catch (Exception e) {
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, interpretersDTO);
	}
	
	@ApiOperation(value = "Delete Proxy by uid")
	@GetMapping("deleteProxy/{uid}")
	public ResponseEntity<Object> deleteProxyByUid(WebRequest request, @ApiParam(value = "Proxy uid to get object and delete", required = true, example = "1") @PathVariable String uid) throws JsonProcessingException{
		try {
			if (!uid.isEmpty()) {
				interpreterService.deleteProxy(uid);
			}else {
				throw new ResourceNotFoundException("El uid esta vacio");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, null);
	}
	
	@ApiOperation(value = "Update Orchestrator each time new is created")
	@PutMapping("updateOrchestrator/{id}")
	public ResponseEntity<Object> updateOrchestrator(WebRequest request, @ApiParam(value = "Params for update orchestrator", required = true, example = "1") @RequestBody final String params,
			@ApiParam(value = "Orchestrator id", required = true, example = "1") @PathVariable long id) throws JsonProcessingException{
		try {
			JsonParser parser = new JsonParser();
			JsonArray jsonObject = parser.parse(params).getAsJsonArray();
			OrchestratorDTO orchestrator = orchestratorService.getSingleOrchestratorById(id);
			if (orchestrator != null) {
				orchestrator.setJson(jsonObject.toString());
				orchestratorService.updateOrchestrator(orchestrator);
			} else {
				throw new ResourceNotFoundException("No se encontro el objeto");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, null);
	}
	

	@ApiOperation(value = "Test Api Url from exernal Webservice")
	@PostMapping("test")
	public ResponseEntity<Object> testApiUrl(@ApiParam(value = "JsonResponse mapping all proxy and orchestrator", required = true, example = "1") @RequestBody final String jsonResponse, WebRequest request)
			throws JsonProcessingException {
		String url = "";
		String nodo = "";
		String method = "";
		String uid = "";
		long step = 0;
		JsonParser parser = new JsonParser();
		JsonObject json = new JsonObject();
		Object response = null;
		JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();
		JsonObject ifNodoExist = new JsonObject();
		int idCollector = jsonObject.get("idCollector").getAsInt();
		long idInterpreter = jsonObject.get("idInterpreter").getAsLong();

		if (jsonObject != null) {
			JsonArray arrayParams = jsonObject.get("params").getAsJsonArray();
			url = getValueFromJSON(arrayParams, "url");
			nodo = getValueFromJSON(arrayParams, "nodo");
			method = getValueFromJSON(arrayParams, "method");
			step = Long.parseLong(getValueFromJSON(arrayParams, "step"));
			json = getJsonObject(arrayParams);
			uid = getUidFromObject(arrayParams);
			
			try {
				response = utilsProxy.getResponseFromUrl(url.isEmpty() ? "" : url, method, json);
				JsonObject data = parser.parse(response.toString()).getAsJsonObject();
				if (!nodo.isEmpty()) {
					ifNodoExist = data.getAsJsonObject(nodo).getAsJsonObject();
				} else {
					ifNodoExist = data;
				}
				if (response != null) {
					String inputParameters = getInputParams(json);
					updateOutputParamsProxy(inputParameters, idInterpreter);
					saveNewProxy(url, uid, inputParameters, "", idCollector, idInterpreter, step, method);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
			}
		}
		return genericCustomMessageResponse(ConstantMessages.CONEXION_REALIZADA, request, ifNodoExist.toString());

	}
	
	private String getUidFromObject(JsonArray array) {
		String value = "";
		 for (JsonElement jsonElement : array) {
			JsonObject data = jsonElement.getAsJsonObject();
			value = data.get("uid").getAsString();
		}
		 return value;
	}
	

	private String getValueFromJSON(JsonArray object, String prop) {
		String value = "";
		for (JsonElement jsonElement : object) {
			JsonObject data = jsonElement.getAsJsonObject();
			String name = data.get("name").getAsString();
			if (name.equalsIgnoreCase("Endpoint")) {
				JsonObject configParams = data.get("configparams").getAsJsonObject();
				if (data.get("step").getAsLong() == configParams.get("step").getAsLong()) {
					switch (prop) {
					case "url":
						value = configParams.get(prop).getAsString();
						break;
					case "nodo":
						if (configParams.has(prop)) {
							value = configParams.get(prop).getAsString();
						}
						break;
					case "method":
						value = configParams.get(prop).getAsString();
					case "step":
						value = configParams.get(prop).getAsString();
					default:
						break;
					}
				}
			}
		}
		return value;
	}

	private JsonObject getJsonObject(JsonArray object) {
		JsonObject json = new JsonObject();
		for (JsonElement jsonElement : object) {
			JsonObject data = jsonElement.getAsJsonObject();
			String name = data.get("name").getAsString();
			JsonObject customJson = new JsonObject();
			if (name.equalsIgnoreCase("Entrada") || name.equalsIgnoreCase("Variables")) {
				JsonArray paramVar = data.get("configparams").getAsJsonArray();
				for (JsonElement jsonElement2 : paramVar) {
					JsonObject dataVars = jsonElement2.getAsJsonObject();
					if (data.get("step").getAsLong() == dataVars.get("step").getAsLong()) {
						customJson.addProperty(dataVars.get("clave").getAsString(), dataVars.get("valor").getAsString());
					}
				}
				json = customJson;
			}
		}
		return json;
	}

	private void saveNewProxy(String url, String code, String inputParameters, String outParameters, int idCollector,
			long idInterpreter, long step, String method) {
		ProxyDTO proxyDTO = null;
		try {
			proxyDTO = new ProxyDTO("Proxy ", code, url, inputParameters, outParameters, idCollector, idInterpreter, step, method);
			proxyService.saveProxy(proxyDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateOutputParamsProxy(String outParameters, Long idInterpreter) {
		Proxy proxy = null;
		ProxyDTO proxyDTO = null;
		try {
			proxy = proxyService.getProxyByIdPageable(idInterpreter);
			if (proxy != null) {
				proxyDTO = new ProxyDTO(proxy.getId(), proxy.getName(), proxy.getCode(), proxy.getUrl(), proxy.getInputParameters(),
						outParameters, proxy.getIdCollector(), proxy.getInterpreters(), proxy.getStep(), proxy.getMethod());
				if (proxyDTO.getId() != null) {
					proxyService.updateProxy(proxyDTO);
				} else {
					System.err.println("El id para actualizar esta vacio");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getInputParams(JsonObject object) {
		String save = "";
		if (object != null) {
			Set<Map.Entry<String, JsonElement>> entry = object.entrySet();
//			save = "{" +  entry.stream().map(e ->   e.getKey() + ": " + e.getValue() )
//					.collect(Collectors.joining(", ")) + "}";
			save = entry.stream().map(e -> e.getKey()).collect(Collectors.joining(", "));
		}
		return save;
	}

}
