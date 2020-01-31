package com.elaniin.nitro.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"", "/"})
public class IndexController {

	@GetMapping(produces = "application/json")
	public Map<String, String> index() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("API", "Nitro");
		result.put("Version", "1.0");
		return result;
	}
}
