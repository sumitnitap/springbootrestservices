package com.foodaggregator.service;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IFoodCatalog {

	//Makes call to suppliers one by one, check for food item on given name, returns item if found else returns NOT_FOUND
	public String getFood(String name) throws JsonProcessingException;
	
	//Makes call to suppliers one by one, check for food item on given name,Quantity returns item if found else returns NOT_FOUND
	public String getFoodQuantity(String name, String Quantity) throws JsonProcessingException;
	
	//Makes call to suppliers one by one, check for food item on given name,Quantity and Price. Returns item and store it in in memory data structure(cache) if found else returns NOT_FOUND
	public String getFoodQuantityPrice(String name, String Quantity, String Price) throws JsonProcessingException;
	
	//Makes call to suppliers in parallel, check for food item on given name, returns item if found else returns NOT_FOUND
	public String getFoodAsync(String name) throws JsonProcessingException, InterruptedException, ExecutionException;
	
	//Checks if Food item is available in in-memory common data structure(cache), if yes returns the item from cache else makes call to supplier.
	public String getFoodQuantityPriceCache(String name, String Quantity, String Price) throws JsonProcessingException;
	
	//returns the current stock present in the cache data structure
	public String getSummaryCache() throws JsonProcessingException;
}
