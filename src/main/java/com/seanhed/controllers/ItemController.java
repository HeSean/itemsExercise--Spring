package com.seanhed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seanhed.data.beans.Item;
import com.seanhed.services.ItemServiceImpl;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	// http://localhost:8080/item/getItem
	@GetMapping("/getItem")
	public ResponseEntity<Object> getItem(@RequestParam long itemNo) {
		return itemServiceImpl.getItemByItemNo(itemNo);
	}

	// http://localhost:8080/item/getAllItems
	@GetMapping("/getAllItems")
	public ResponseEntity<Object> getAllItems() {
		return itemServiceImpl.getAllItems();
	}

	// http://localhost:8080/item/addItem
	@PostMapping("/addItem")
	public ResponseEntity<Object> addItem(@RequestBody Item item) {
		return itemServiceImpl.addNewItem(item);
	}
	
	// http://localhost:8080/item/updateItem
	@PostMapping("/updateItem")
	public ResponseEntity<Object> updateItem(@RequestBody Item item) {
		return itemServiceImpl.updateItem(item);
	}

	// http://localhost:8080/item/deleteItem
	@DeleteMapping("/deleteItem")
	public ResponseEntity<Object> deleteItem(@RequestParam long itemNo) {
		return itemServiceImpl.deleteItemFromStock(itemNo);
	}

	// http://localhost:8080/item/withdrawItemAmount
	@PutMapping("/withdrawItemAmount")
	public ResponseEntity<Object> withdrawItemAmount(@RequestParam long itemNo, @RequestParam int amount) {
		return itemServiceImpl.withdrawItemAmount(itemNo, amount);
	}

	// http://localhost:8080/item/depositItemAmount
	@PutMapping("/depositItemAmount")
	public ResponseEntity<Object> depositItemAmount(@RequestParam long itemNo, @RequestParam int amount) {
		return itemServiceImpl.depositItemAmount(itemNo, amount);
	}

}
