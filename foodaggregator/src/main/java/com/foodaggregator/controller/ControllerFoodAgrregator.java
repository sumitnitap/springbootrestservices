package com.foodaggregator.controller;


import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foodaggregator.service.IFoodCatalog;



@RestController
public class ControllerFoodAgrregator {

	@Autowired
	private IFoodCatalog service;

	@GetMapping(value = "/buy-item", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public String findByName(@RequestParam("name") String name) throws JsonProcessingException{
		return service.getFood(name);
	}

	@GetMapping(value = "/buy-item-qty", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public String findByNameqty(@RequestParam("name") String name, @RequestParam("Quantity") String Quantity) throws JsonProcessingException{
		return service.getFoodQuantity(name, Quantity);
	}

	@GetMapping(value = "/buy-item-qty-price", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public String findByNameqtyPrice(@RequestParam("name") String name, @RequestParam("Quantity") String Quantity,  @RequestParam("Price") String Price) throws JsonProcessingException{
		return service.getFoodQuantityPriceCache(name, Quantity, Price);
	}

	@GetMapping(value = "/fast-buy-item", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public String findByNameAsync(@RequestParam("name") String name) throws JsonProcessingException, InterruptedException, ExecutionException{
		return service.getFoodAsync(name);
	}
	@GetMapping(value = "/show-summary", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
	public String GetCacheSummary() throws JsonProcessingException, InterruptedException, ExecutionException{
		return service.getSummaryCache();
	}
}

