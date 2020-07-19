package com.foodaggregator.service;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.foodaggregator.model.FoodStock;


@Service
public class CLientFoodAggregatorAsync {

	HttpHeaders headers = new HttpHeaders();
	HttpEntity<FoodStock[]> entity = new HttpEntity<FoodStock[]>(headers);

	RestTemplate restTemplate = new RestTemplate();

	@Async
	public CompletableFuture<List<FoodStock>> getVegetables() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/vegetables";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);

		return CompletableFuture.completedFuture(Arrays.asList(list));
	}

	@Async
	public CompletableFuture<List<FoodStock>> getFruits() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/fruits";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);
		return CompletableFuture.completedFuture(Arrays.asList(list));
	}


	@Async   
	public CompletableFuture<List<FoodStock>> getGrains() {
		String url = "https://my-json-server.typicode.com/2020-abhilash/mock-api/grains";
		FoodStock[] list = restTemplate.getForObject(url,FoodStock[].class);
		return CompletableFuture.completedFuture(Arrays.asList(list));
	}

}
