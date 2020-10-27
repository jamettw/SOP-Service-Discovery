package com.microservices.servicediscovery;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.ribbon.proxy.annotation.Var;

@SpringBootApplication
@EnableEurekaServer
@RestController
@Controller
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
    @RequestMapping(value="/service", method = RequestMethod.GET)
    @ResponseBody
    private String hello() {
    	 
    	final String uri = "http://localhost:8761/eureka/apps";
    	ArrayList<InstanceDetail> instanceDetailList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JsonObject resultJson = (JsonObject) JsonParser.parseString(result);
        HashMap<String, String> instanceHash= new HashMap<>();
        instanceHash.put("SERVICE1", null);
        instanceHash.put("SERVICE2", null);
        instanceHash.put("SERVICE3", null);
        instanceHash.put("SERVICE4", null);
        instanceHash.put("SERVICE5", null);

        
        JsonArray instances =  ((JsonObject) resultJson.get("applications")).getAsJsonArray("application");
		for (int i=0; i<instances.size(); i++) {
			JsonObject instance = (JsonObject) instances.get(i);
			//System.out.print(instance);
			JsonObject instanceDetail = (JsonObject) instance.getAsJsonArray("instance").get(0);
			//System.out.print(instanceDetail);
			//System.out.println("app : "+instanceDetail.get("app").toString());
			//System.out.println("status : "+instanceDetail.get("status").toString());
			//instanceDetailList.add(new InstanceDetail(instanceDetail.get("app").toString(), instanceDetail.get("status").toString()));
			instanceHash.put(instanceDetail.get("app").toString().replace("\"", ""), instanceDetail.get("status").toString());
			
			
		}
		//System.out.println(instanceHash.get("SERVICE1"));
		System.out.println(instanceHash.toString());
		String html = "<html>\n" + 
				"<header>"
				
				+	"    <meta charset=\"UTF-8\">\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">"+
				"    <title>Service Instance</title>\n" + 
				"    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" + 
				"    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n" + 
				"    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>"
				+"<style>\n" + 
						".center {\n" + 
						"  margin: auto;\n" + 
						"  width: 60%;\n" + 
						"  border: 3px solid #73AD21;\n" + 
						"  padding: 10px;\n" +
						"}\n" + 
						"</style>"+"</header>\n"
				+ "<body>\n"+
				"<div style=\"margin-top: 55px;\">"+"<div class=\"center\">"+
				"<table class=\"table\"style=\"paddingRight:50%\">\n" + 
				"  <thead class=\"thead-dark\">\n" + 
				"    <tr>\n" + 
				"      <th scope=\"col\">No.</th>\n" + 
				"      <th scope=\"col\">Service</th>\n" + 
				"      <th scope=\"col\">Status</th>\n" + 
				"    </tr>\n" + 
				"  </thead>\n" + 
				"  <tbody>\n" + 
				"    <tr>\n" + 
				"      <th scope=\"row\">1</th>\n" + 
				"      <td>Service1</td>\n" + 
				"      <td style=\"background-color: "+(instanceHash.get("SERVICE1") != null ?"green":"red")+"\">"+(instanceHash.get("SERVICE1") != null ?"UP":"DOWN")+"</td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <th scope=\"row\">2</th>\n" + 
				"      <td>Service2</td>\n" + 
				"      <td style=\"background-color: "+(instanceHash.get("SERVICE2") != null ?"green":"red")+"\">"+(instanceHash.get("SERVICE2") != null ?"UP":"DOWN")+"</td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <th scope=\"row\">3</th>\n" + 
				"      <td>Service3</td>\n" + 
				"      <td style=\"background-color: "+(instanceHash.get("SERVICE3") != null ?"green":"red")+"\">"+(instanceHash.get("SERVICE3") != null ?"UP":"DOWN")+"</td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <th scope=\"row\">4</th>\n" + 
				"      <td>Service4</td>\n" + 
				"      <td style=\"background-color: "+(instanceHash.get("SERVICE4") != null ?"green":"red")+"\">"+(instanceHash.get("SERVICE4") != null ?"UP":"DOWN")+"</td>\n" + 
				"    </tr>\n" + 
				"    <tr>\n" + 
				"      <th scope=\"row\">5</th>\n" + 
				"      <td>Service5</td>\n" + 
				"      <td style=\"background-color: "+(instanceHash.get("SERVICE5") != null ?"green":"red")+"\">"+(instanceHash.get("SERVICE5") != null ?"UP":"DOWN")+"</td>\n" + 
				"    </tr>\n" + 
				"  </tbody>\n" + 
				"</table>\n" + 
				"\n" +
				"</div>"+
				"</div>"+
				""+
				"    </div>"
				+ "</body>\n" 
			+ "</html>";
		
		return html;
		
        
    }
}