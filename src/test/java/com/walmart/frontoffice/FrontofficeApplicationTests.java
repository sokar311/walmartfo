package com.walmart.frontoffice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FrontofficeApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void pageTest() throws Exception {
		logger.info("---------- Starting MVC Test ---------------");
		
		Map<String, Object> model = mockMvc.perform(get("https://localhost:8080/search?phrase=1")).andDo(print()).andExpect(status().isOk()).andReturn().getModelAndView().getModel();
		
		if (!model.get("TotalProductos").equals(1)) {
			logger.error("Expected a single result, got : " + model.get("TotalProductos"));
			throw new Exception("Expected a single result, got : " + model.get("TotalProductos"));
		} else {
			logger.info("Expected a single result, got a single result, OK!");			
		}
		
		logger.info("---------- MVC Test Finished ---------------");		
	}

}
