package com.foodaggregator.service;

import java.util.List;

import com.foodaggregator.model.FoodStock;

public interface ICLientFoodAggregators {

	//Make calls to API which provides vegetables stock.
	public List<FoodStock> getVegetables();
	//Make calls to API which provides Fruits stock.
	public List<FoodStock> getFruits();
	//Make calls to API which provides Grain stock.
	public List<FoodStock> getGrains();


}
