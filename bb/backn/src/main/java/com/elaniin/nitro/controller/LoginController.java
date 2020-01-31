package com.elaniin.nitro.controller;

import static com.elaniin.nitro.security.Constans.TOKEN_BEARER_PREFIX;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elaniin.nitro.security.JwtRequest;
import com.elaniin.nitro.security.JwtResponse;
import com.elaniin.nitro.security.JwtTokenUtil;
import com.elaniin.nitro.service.impl.UserServiceImpl;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/api/v1/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequests) throws Exception{
        authenticate(authenticationRequests.getUsername(), authenticationRequests.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequests.getUsername());
        final String token = tokenUtil.generateToken(userDetails);
        JSONObject object = new JSONObject();

        return ResponseEntity.ok(new JwtResponse(token,
                tokenUtil.getUserNameFromToken(token),
                tokenUtil.getExpirationDateFromToken(token),
                TOKEN_BEARER_PREFIX.replace(" ","")));
    }

    private void authenticate(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
    @GetMapping(value = "/api/v1/me")
    public ResponseEntity<Object> obtenerInfoUsuario(HttpServletRequest request){
    	Principal principal = request.getUserPrincipal();
    	Map<String, Object> model = new HashMap<String, Object>();
        model.put("data",principal.getName());
    	return ResponseEntity.ok(model) ;
    }


}
