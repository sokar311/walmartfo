package com.walmart.frontoffice.services;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.walmart.frontoffice.dto.ProductDTO;
import com.walmart.frontoffice.dto.ProductsEnvelope;
import com.walmart.frontoffice.exceptions.IntegrationException;

@Service
public class ProductService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private @Value("${api.url}") String url;

	/**
	 * 
	 * Method to call integration
	 * 
	 * @param url
	 * @param method
	 * @param body
	 * @return
	 * @throws IntegrationException 
	 */
	private String callAPI( String url, String method, String body ) throws IntegrationException  {
		
		logger.info("Api url:" + url + " metodo: " + method);
		
		ResponseEntity<String> response = null;
		
		try {
			
		    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		    requestFactory.setConnectTimeout(5000);
		    requestFactory.setReadTimeout(5000);
		    
		    RestTemplate restTemplate = new RestTemplate(requestFactory);
		    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

			if( body == null ) {
				body = "";
			}
			
			// Set request content type to application/json
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<String>(body,headers);
			
			// Set request method
			HttpMethod metodoHttp = null;
			if( "GET".equals(method) ) {
				metodoHttp = HttpMethod.GET;	
			} else if( "POST".equals(method) ) {
				metodoHttp = HttpMethod.POST;
			} else if( "PUT".equals(method) ) {
				metodoHttp = HttpMethod.PUT;
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);			
			response = restTemplate.exchange(url, metodoHttp, requestEntity, String.class);

		} catch (HttpClientErrorException e) {
			logger.error("Error code and description : " + e.getRawStatusCode() + " " +  e.getStatusText());
			logger.error("Error message; " + e.getMessage());
			logger.error("Error body: "+e.getResponseBodyAsString());
		
		// Validar cuando haya error reportado como HTTP
		if( e.getRawStatusCode() == 404 ) {
			logger.warn("Integration error " + e.getRawStatusCode() + " " +  e.getStatusText());
			IntegrationException iexception = new IntegrationException(e.getStatusCode(), "Integration error " + e.getRawStatusCode() + " " +  e.getStatusText(), e.getResponseBodyAsByteArray(), null);
			throw iexception;			
		} else if( e.getRawStatusCode() == 400 ) {
			logger.warn("Integration error " + e.getRawStatusCode() + " " +  e.getStatusText());
			IntegrationException iexception = new IntegrationException( e.getStatusCode(), "Integration error " + e.getRawStatusCode() + " " +  e.getStatusText(), e.getResponseBodyAsByteArray(), null);
			throw iexception;
		} else if( e.getRawStatusCode() == 405 ) {
			logger.warn("Integration error " + e.getRawStatusCode() + " " +  e.getStatusText());
			IntegrationException iexception = new IntegrationException( e.getStatusCode(), "Integration error " + e.getRawStatusCode() + " " +  e.getStatusText(), e.getResponseBodyAsByteArray(), null);
			throw iexception;
		} else if( e.getRawStatusCode() == 500 ) {
			logger.warn("Integration error " + e.getRawStatusCode() + " " +  e.getStatusText());
			IntegrationException iexception = new IntegrationException( e.getStatusCode(), "Integration error " + e.getRawStatusCode() + " " +  e.getStatusText(), e.getResponseBodyAsByteArray(), null);
			throw iexception;				
		} else {
			logger.warn("Integration error " + e.getRawStatusCode());
			IntegrationException iexception = new IntegrationException( e.getStatusCode(), "Integration error " + e.getRawStatusCode() + " " +  e.getStatusText(), e.getResponseBodyAsByteArray(), null);
			throw iexception;
		}	
		} catch (Exception e) {
			logger.error("Interface Error. " + e.getMessage());
			throw new IntegrationException(HttpStatus.INTERNAL_SERVER_ERROR, "Interface Error. " + e.getMessage());
		}		

		logger.info("Integration OK : " + response.getStatusCode());
		
		String retorno = response.getBody();
		
		return retorno;
	}
	
	public List<ProductDTO> getProducts(String phrase) {
		logger.debug("Call products Api with search phrase : " + phrase);
		
		List<ProductDTO> products = new ArrayList<>();

		String response = this.callAPI(url + phrase,"GET",null);

		logger.debug("Products Api response : " + response);

		Gson gson = new Gson();
		
		ProductsEnvelope envelope = null;
		try {
			envelope = gson.fromJson(response, ProductsEnvelope.class);
		} catch (JsonSyntaxException e) {
			logger.error("Problem trying to de-serialize response.");
		}
		
		// If envelope has nay products return the array
		if (envelope!=null && envelope.getProducts()!=null) {
			return envelope.getProducts();
		}
		
		return products;
	}
}
