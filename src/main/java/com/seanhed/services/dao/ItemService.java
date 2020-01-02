package com.seanhed.services.dao;

import org.springframework.http.ResponseEntity;

import com.seanhed.data.beans.Item;

public interface ItemService {

	public ResponseEntity<Object> getItemByItemNo(long itemNo);
	
	public ResponseEntity<Object> getAllItems();
	
	public ResponseEntity<Object> addNewItem(Item item);
	
	public ResponseEntity<Object> updateItem(Item item);
	
	public ResponseEntity<Object> deleteItemFromStock(long itemNo);

	public ResponseEntity<Object> withdrawItemAmount(long itemNo, int amount);

	public ResponseEntity<Object> depositItemAmount(long itemNo, int amount);	





	
}
