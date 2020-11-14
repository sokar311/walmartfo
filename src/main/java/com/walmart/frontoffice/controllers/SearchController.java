package com.walmart.frontoffice.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.GsonBuilder;
import com.walmart.frontoffice.dto.ProductDTO;
import com.walmart.frontoffice.services.ProductService;

@Controller
public class SearchController {

	@Autowired
	ProductService productService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/search", "/home", "/"}, method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response , ModelMap model,
			@RequestParam(value="phrase", required=false) String phrase) throws Exception {

		logger.debug("---- Product search controller start ----");
		
		List<ProductDTO> products = new ArrayList<>();
		
		// Call product service to get the products that match the criteria
		if (phrase!=null && !phrase.isEmpty()) {
			products = productService.getProducts(phrase);
		}
		
		// Set results in modelmap
		model.put("titleURL", "Walmart");
		model.put("TotalProductos",products.size());
		model.put("productosBusqueda",new GsonBuilder().create().toJson(products));
		model.put("viewOferta", false);
		model.put("cantidadXPagina", 5);
		model.put("search", phrase);
		
		logger.debug("---- Product search controller end ----");
		
		return new ModelAndView("search", model);
	}
}
