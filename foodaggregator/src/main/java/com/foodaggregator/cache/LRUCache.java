package com.foodaggregator.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.foodaggregator.model.FoodStock;

/**
 * LRU cache implementation using double linked list and concurrenthashmap
 */

public final class LRUCache<K, V> {
	
	/**
	 * A doubly-linked-list implementation to save objects into the Hashmap
	 * as key-value pair
	 */

	private static class Node<K, V> {
		private FoodStock value;
		private K key;
		private Node<K, V> next, prev;
		public Node(K key, FoodStock value) {
			this.key = key;
			this.value = value;
		}
		@Override
		public String toString() {
			return value.toString();
		}
	}
	private int maxCapacity=0;
	private ConcurrentHashMap<K, Node<K, V>> map;
	private Node<K, V> head, tail;

	public LRUCache(int maxCapacity) {
		this(16, maxCapacity);
	}
	public LRUCache(int initialCapacity, int maxCapacity) {
		this.maxCapacity = maxCapacity;
		if (initialCapacity > maxCapacity)
			initialCapacity = maxCapacity;
		map = new ConcurrentHashMap<>(initialCapacity);
	}

	private void removeNode(Node<K, V> node) {
		if (node == null)
			return;
		if (node.prev != null) {
			node.prev.next = node.next;
		} else {
			head = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		} else {
			tail = node.prev;
		}
	}

	private void offerNode(Node<K, V> node) {
		if (node == null)
			return;
		if (head == null) {
			head = tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			node.next = null;
			tail = node;
		}
	}

	public void put(K key, FoodStock e) {
		if (map.contains(key)) {
			Node<K, V> node = map.get(key);
			node.value = e;
			removeNode(node);
			offerNode(node);
		} else {
			if (map.size() == maxCapacity) {
				System.out.println("maxCapacity of cache reached");
				map.remove(head.key);
				removeNode(head);
			}
			Node<K, V> node = new Node<K, V>(key, e);
			offerNode(node);
			map.put(key, node);
		}
	}
	public FoodStock get(K key) {
		Node<K, V> node = map.get(key);
		removeNode(node);
		offerNode(node);
		if (node != null)
			return node.value;
		else
			return null;
	}

	public List<FoodStock> printCache() throws JsonProcessingException {
		List<FoodStock> listfinal = new ArrayList<FoodStock>();
		Node<K, V> curr = head;
		while (curr != null) {
			listfinal.add(curr.value);
			curr = curr.next;
		}
		System.out.println(map);
		return listfinal;

	}
}
