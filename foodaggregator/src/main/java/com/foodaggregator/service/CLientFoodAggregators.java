package com.foodaggregator.service;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.foodaggregator.model.FoodStock;


@Service
public class CLientFoodAggregators implements ICLientFoodAggregators {

	RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<FoodStock> getVegetables() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/vegetables";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);

		return Arrays.asList(list);
	}


	@Override   
	public List<FoodStock> getFruits() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/fruits";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);
		return Arrays.asList(list);
	}


	@Override   
	public List<FoodStock> getGrains() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/grains";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);
		return Arrays.asList(list);
	}

}
