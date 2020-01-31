package com.elaniin.nitro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Execute Orchestrator Controller", description = "Build workflow to execute all proxys request")
@RestController
@RequestMapping(value = "/response/collector")
class ExecuteOrchestratorController {

    public static final Logger logger = LoggerFactory.getLogger(ExecuteOrchestratorController.class);

    @ApiOperation(value = "Execute proxy request dinamically")
    @RequestMapping(value = "/{id}/{interpreterName}")
    public ResponseEntity<Object> execute(@ApiParam(value = "Id Interpreter", required = true, example = "1") @PathVariable int id,
    		@ApiParam(value = "Interpreter Name", required = true, example = "1") @PathVariable String interpreterName) {
        try {
            if (id > 0 && !interpreterName.isEmpty() && !interpreterName.isBlank()) {
                
            }
        } catch(Exception ex){
            logger.error("Error", ex.getMessage(), ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }

}