package com.foodaggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodaggregator.cache.LRUCache;
import com.foodaggregator.model.FoodStock;

/*
 * FoodCatalog is a service class to perform the business logic for
 * specific GET operations. See the Interface ICLientFoodAggregators for methods that this 
 * class Implements.
 */

@Service
public class FoodCatalog implements IFoodCatalog {

	//Interface defining methods for API calls to get vegetables, fruits and grain stock one by one.
	@Autowired
	private ICLientFoodAggregators service;

	//class defining methods for API calls to get vegetables, fruits and grain stock in parallel
	@Autowired
	private CLientFoodAggregatorAsync serviceAsync;

	//object to create Json string from List
	ObjectMapper mapper = new ObjectMapper();
	LRUCache<String, List<FoodStock>> cache = new LRUCache<String, List<FoodStock>>(10);

	@Override
	public String getFood(String name) throws JsonProcessingException
	{
		String item=null;
		//Retrieving Food items from client by calling client API one by one
		List<FoodStock> vegetables = service.getVegetables();
		List<FoodStock> fruits =service.getFruits();
		List<FoodStock> grains =service.getGrains();

		List<FoodStock> listfinal = new ArrayList<FoodStock>();

		//from list of vegetable items, getting one item at a time and checking for item availability
		for (FoodStock e : vegetables) {
			if(e.getProductName().equalsIgnoreCase(name))
				listfinal.add(e);
		}

		//from list of fruits items, getting one item at a time and checking for item availability
		for (FoodStock e : fruits) {
			if(e.getName().equalsIgnoreCase(name))
				listfinal.add(e);
		}

		//from list of grains items, getting one item at a time and checking for item availability
		for (FoodStock e : grains) {
			if(e.getItemName().equalsIgnoreCase(name))
				listfinal.add(e);
		}

		item= mapper.writeValueAsString(listfinal);

		//checking if given food item is available or not
		if(listfinal.isEmpty())
			return "NOT_FOUND";
		else
			return item;
	}

	@Override
	public String getFoodQuantity(String name, String Quantity) throws JsonProcessingException
	{
		String item=null;
		//Retrieving Food items from client by calling client API one by one
		List<FoodStock> vegetables = service.getVegetables();
		List<FoodStock> fruits =service.getFruits();
		List<FoodStock> grains =service.getGrains();

		List<FoodStock> listfinal = new ArrayList<FoodStock>();

		//from list of vegetable items, getting one item at a time and checking for item availability
		for (FoodStock e : vegetables) {
			if(e.getProductName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity))
				listfinal.add(e);
		}

		//from list of fruits items, getting one item at a time and checking for item availability
		for (FoodStock e : fruits) {
			if(e.getName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity))
				listfinal.add(e);
		}

		//from list of grains items, getting one item at a time and checking for item availability
		for (FoodStock e : grains) {
			if(e.getItemName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity))
				listfinal.add(e);
		}

		item= mapper.writeValueAsString(listfinal);
		if(listfinal.isEmpty())
			return "NOT_FOUND";
		else
			return item;
	}

	@Override
	public String getFoodQuantityPrice(String name, String Quantity, String Price) throws JsonProcessingException
	{
		String item=null;
		//Retrieving Food items from client by calling client API one by one
		List<FoodStock> vegetables = service.getVegetables();
		List<FoodStock> fruits =service.getFruits();
		List<FoodStock> grains =service.getGrains();

		List<FoodStock> listfinal = new ArrayList<FoodStock>();

		//from list of fruits items, getting one item at a time and checking for item availability
		for (FoodStock e : vegetables) {

			//case insensitive, getting decimal value by removing dollar prefix
			if(e.getProductName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity) && Double.parseDouble(e.getPrice().substring(1))==Double.parseDouble(Price))
				listfinal.add(e);

			//putting vegetables item to cache with key as a combination of name, quantity and price. This will be unique and lowercase to support case insensitive 
			cache.put(e.getProductName().toLowerCase().concat(e.getQuantity().toLowerCase()).concat(e.getPrice().substring(1).toLowerCase()),e);
		}

		//from list of fruits items, getting one item at a time and checking for item availability
		for (FoodStock e : fruits) {

			//case insensitive, getting decimal value by removing dollar prefix
			if(e.getName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity) && Double.parseDouble(e.getPrice().substring(1))==Double.parseDouble(Price))
				listfinal.add(e);
			//putting fruits item to cache with key as a combination of name, quantity and price. This will be unique and lowercase to support case insensitive 
			cache.put(e.getName().toLowerCase().concat(e.getQuantity().toLowerCase()).concat(e.getPrice().substring(1).toLowerCase()),e);
		}

		//from list of grains items, getting one item at a time and checking for item availability
		for (FoodStock e : grains) {

			//case insensitive, getting decimal value by removing dollar prefix
			if(e.getItemName().equalsIgnoreCase(name) && Long.parseLong(e.getQuantity())==Long.parseLong(Quantity) && Double.parseDouble(e.getPrice().substring(1))==Double.parseDouble(Price))
				listfinal.add(e);

			//putting grains item to cache with key as a combination of name, quantity and price. This will be unique and lowercase to support case insensitive 
			cache.put(e.getItemName().toLowerCase().concat(e.getQuantity().toLowerCase()).concat(e.getPrice().substring(1).toLowerCase()),e);
		}

		item= mapper.writeValueAsString(listfinal);
		if(listfinal.isEmpty())
			return "NOT_FOUND";
		else
			return item;
	}


	@Override
	public String getFoodAsync(String name) throws JsonProcessingException, InterruptedException, ExecutionException
	{

		String item=null;
		//Retrieving Food items from client by calling client APIs in parallel, supports fast-buy-item
		CompletableFuture<List<FoodStock>> vegetables = serviceAsync.getVegetables();
		CompletableFuture<List<FoodStock>> fruits =serviceAsync.getFruits();
		CompletableFuture<List<FoodStock>> grains =serviceAsync.getGrains();

		List<FoodStock> listfinal = new ArrayList<FoodStock>();

		for (FoodStock e : vegetables.get()) {
			if(e.getProductName().equalsIgnoreCase(name))
				listfinal.add(e);
		}

		for (FoodStock e : fruits.get()) {
			if(e.getName().equalsIgnoreCase(name))
				listfinal.add(e);
		}
		for (FoodStock e : grains.get()) {
			if(e.getItemName().equalsIgnoreCase(name))
				listfinal.add(e);
		}

		item= mapper.writeValueAsString(listfinal);
		if(listfinal.isEmpty())
			return "NOT_FOUND";
		else
			return item;
	}

	@Override
	public String getSummaryCache() throws JsonProcessingException{



		return mapper.writeValueAsString(cache.printCache());
	}

	@Override
	public String getFoodQuantityPriceCache(String name, String Quantity, String Price) throws JsonProcessingException{

		String Result =null;
		//get matching food item from the cache, if not found make call to vendor API
		if(cache.get(name.toLowerCase().concat(Quantity.toLowerCase()).concat(Price.toLowerCase())) != null)
		{

			Result=mapper.writeValueAsString(cache.get(name.toLowerCase().concat(Quantity.toLowerCase()).concat(Price.toLowerCase())));
		}
		else
			Result = getFoodQuantityPrice(name, Quantity, Price);

		return Result;
	}


}
