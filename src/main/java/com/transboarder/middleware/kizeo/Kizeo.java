package com.transboarder.middleware.kizeo;

import java.util.Collections;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.transboarder.middleware.datamodel.InterventionSheet;

@Component
public class Kizeo {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Kizeo.class);
	private String url = "https://kizeoforms.com:443/rest/v3/forms/200256/data/readnew";
	private InterventionSheet interventionSheet;
	
	public Kizeo() {
	}
	
	public void getInterventionSheet(DelegateExecution execution) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		String authorization = execution.getVariable("kizeo_token").toString();
		headers.set("Authorization", authorization );
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<InterventionSheet> response = restTemplate.exchange(url, HttpMethod.GET, entity, InterventionSheet.class);
        interventionSheet = response.getBody();
        execution.setVariable("interventionSheet", interventionSheet.toString());
        log.info(interventionSheet.toString());
	}
	
	public void putOnFtp(DelegateExecution execution) {
		
	}
}
