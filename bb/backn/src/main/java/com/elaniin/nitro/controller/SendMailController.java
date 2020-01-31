package com.elaniin.nitro.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.ProxyDTO;
import com.elaniin.nitro.entity.Proxy;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.ProxyService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/api/v1/send")
public class SendMailController extends GenericBase {

    public static final Logger logger = LoggerFactory.getLogger(ProxyIntegratorController.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ProxyService proxyService;

    @PostMapping(value = "/mail")
    public ResponseEntity<Object> sendMail(@RequestBody final String jsonResponse, WebRequest request) {
        try {

            // System.out.print("Response: ");
            // System.out.println(jsonResponse);
            // System.out.print("Request: ");
            // System.out.println(request.toString());
            
            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            JsonParser parser = new JsonParser();
            JsonObject json = new JsonObject();
            // Convert
            JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();
            // Get Data Json
            int idCollector = jsonObject.get("idCollector").getAsInt();
            String mail = jsonObject.get("correo").getAsString();
            String message = jsonObject.get("mensaje").getAsString();
            String url = jsonObject.get("url").getAsString();
            JsonArray arrayVariables = jsonObject.get("variables").getAsJsonArray();
            
            if (json != null) {

                if (!mail.trim().isEmpty() && !message.trim().isEmpty()) {
                    // // SimpleMailMessage msg = new SimpleMailMessage();
                    // // msg.setTo(json.getString("correo"));
                    helper.setTo(mail.trim());

                    // // msg.setSubject("Test Editor de Interpretador");
                    helper.setSubject("Test Editor de Interpretador");
                    // // msg.setText(json.getString("mensaje"));
                    // System.out.println(json.toString());
                    
                    helper.setText(loadFromFile(message.trim(), arrayVariables), true);

                    javaMailSender.send(msg);
                    String inputParameters = "mail:" + mail.trim() ;
                    String outParameters = "";// json.toString()
                    // System.out.println(json.toString());     
                    // Save step
//                    saveNewProxy(url, inputParameters, outParameters, idCollector);

                } else {
                    throw new ResourceNotFoundException(ConstantMessages.CONEXION_RECHAZADA);    
                }

            } 

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.error("Error", ex.getMessage(), ex);
        }
        return ResponseEntity.ok().build();
        
    }

    private void saveNewProxy(String url, String inputParameters, String outParameters, int idCollector) {
		Proxy proxy = null;
		ProxyDTO proxyDTO = null;
		try {
			// proxyDTO = new ProxyDTO("Proxy ", url, inputParameters, outParameters, idCollector);
			// proxy = proxyService.saveProxy(proxyDTO);
		} catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadFromFile(String message, JsonArray variables) {
        String result = null;
        StringBuilder messageVar = new StringBuilder("");
        System.out.println(variables.toString());
        try {
            if (variables.size() > 0) {
                
                String name = new String();
                String lastName = new String();
                String invoiceCod = new String();

                for (JsonElement jsonElement : variables) {
                    JsonObject data = jsonElement.getAsJsonObject();
                    if (data.get("clave").getAsString().trim().equalsIgnoreCase("name")) {
                        name = data.get("valor").getAsString();
                    }
                    if (data.get("clave").getAsString().trim().equalsIgnoreCase("lastname")) {
                        lastName = data.get("valor").getAsString();
                    }

                    if (data.get("clave").getAsString().trim().equalsIgnoreCase("invoiceCod")) {
                        invoiceCod = data.get("valor").getAsString();
                    }
                }
                
                messageVar.append("<tr>");
                messageVar.append(" <td>");
                messageVar.append("    <h6>Notificación: </h6> ");
                messageVar.append("    <p><b>El usuario " +  "{name} {lastname}, has pago {invoiceCod}  </b></p> ");
                messageVar.append(" </td> ");
                messageVar.append("</tr>");

                messageVar = new StringBuilder(messageVar.toString().trim().replace("{name}", name));
                messageVar = new StringBuilder(messageVar.toString().trim().replace("{lastname}", lastName));
                messageVar = new StringBuilder(messageVar.toString().trim().replace("{invoiceCod}", invoiceCod));
            }

            result = new String(Files.readAllBytes(Paths.get("src/main/resources/views/email.html")));
            result = result.replace("{messageVar}", messageVar);
            result = result.replace("{message}", message);
            System.out.println(result);
        } catch(IOException ex) {
            ex.getStackTrace();
        }
        return result;
    }
}
