package com.walmart.frontoffice.exceptions;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class IntegrationException extends HttpClientErrorException {

	private static final long serialVersionUID = -7987749968484523267L;
	
	private String url;
	private String body;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBody() {
		return body;
	}

	public IntegrationException(HttpStatus statusCode, String statusText,
			byte[] responseBody, Charset responseCharset) {
		super(statusCode, statusText, responseBody, responseCharset);
	}

	public IntegrationException(HttpStatus statusCode, String statusText,
			HttpHeaders responseHeaders, byte[] responseBody,
			Charset responseCharset) {
		super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
	}

	public IntegrationException(HttpStatus statusCode, String statusText) {
		super(statusCode, statusText);
	}

	public IntegrationException(HttpStatus statusCode) {
		super(statusCode);
	}

}
