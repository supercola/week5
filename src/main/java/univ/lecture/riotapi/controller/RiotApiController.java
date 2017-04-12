package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import univ.lecture.riotapi.Calculator;
import univ.lecture.riotapi.model.Summoner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1/calc")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

//    @RequestMapping(value = "/summoner/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Summoner querySummoner(@PathVariable("name") String summonerName) throws UnsupportedEncodingException {
//        final String url = riotApiEndpoint + "/summoner/by-name/" +
//                summonerName +
//                "?api_key=" +
//                riotApiKey;
//
//        String response = restTemplate.getForObject(url, String.class);
//        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);
//
//        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));
//
//        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
//        String queriedName = (String)summonerDetail.get("name");
//        int queriedLevel = (Integer)summonerDetail.get("summonerLevel");
//        Summoner summoner = new Summoner(queriedName, queriedLevel);
//
//        return summoner;
//    }
    
//    @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public @ResponseBody Summoner querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
//        final String url = riotApiEndpoint;
//
//        Calculator cal = new Calculator();
//
//        Date dt = new Date();
//        
//        int teamId = 7;
//        long now = dt.getTime();
//        double result = cal.calculate(equation);
//        
//        Summoner summoner = new Summoner(teamId, now, result);
//
//        return summoner;
//    }
    
//    Calculator cal = new Calculator();
//    
//    class Data{
//    	private int teamId = 7;
//    	private long now = System.currentTimeMillis();
//    	private double result;
//    	
//    	public int getTeamId(){
//    		return teamId;
//    	}
//    	
//    	public long getNow(){
//    		return now;
//    	}
//    	
//    	public double getResult(){
//    		return result;
//    	}
//    	
//    	public void setResult(String exp){
//    		this.result = cal.calculate(exp);
//    	}
//    	
//    }
    
  @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody Summoner querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
      final String url = riotApiEndpoint;
      
//      Data d = new Data();
//      d.setResult(equation);
//      
//      ObjectMapper mapper = new ObjectMapper();
      
//      try{
//    	  String request = mapper.writeValueAsString(d);
//          //Data 객체를 JSON으로 파싱하는 부분  
//      }catch(IOException e){
//    	  System.out.println("pasing error!");
//      }
      
      Calculator cal = new Calculator();
      
	    String request = "{"
	    + "\"teamId\":\"7\","
	    + "\"now\":"+System.currentTimeMillis()
	    + "\"cal\":"+cal.calculate(equation)
	    + "}";

      
      String response = restTemplate.postForObject(url, request, String.class);
      Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);
      

      Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
      int teamId = (Integer)summonerDetail.get("teamId");
      int now = (Integer)summonerDetail.get("now");
      double result = (double)summonerDetail.get("result");
      summonerDetail=(Map<String,Object>)parsedMap.values().toArray()[0];
      parsedMap = new JacksonJsonParser().parseMap(request);
      
      
      
      Summoner summoner = new Summoner(teamId, now, result);

      return summoner;
  }

    
}
