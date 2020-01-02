package com.seanhed.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seanhed.data.beans.Item;
import com.seanhed.data.repo.ItemRepository;
import com.seanhed.services.dao.ItemService;
import com.seanhed.utils.ResponseUtil;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public ResponseEntity<Object> getItemByItemNo(long itemNo) {
		try {
			Optional<Item> item = itemRepository.findById(itemNo);
			return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(404, "item not found");
		}
	}

	@Override
	public ResponseEntity<Object> getAllItems() {
		try {
			List<Item> items = itemRepository.findAll();
			System.out.println("items retrived are -> " + items);
			return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(404, "items not found");
		}
	}

	@Override
	public ResponseEntity<Object> addNewItem(Item item) {
		try {
			System.out.println("added item is " + item);
			return ResponseUtil.generateSuccessMessage(itemRepository.save(item).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(500, "failed to add new item");
		}
	}

	@Override
	public ResponseEntity<Object> updateItem(Item item) {
		try {
			System.out.println("updateItem item  " + item);
			Item tempItem = itemRepository.getOne(item.getItemNo());
			tempItem.setName(item.getName());
			tempItem.setInventoryCode(item.getInventoryCode());

			// updating amount
			System.out.println("old amount - " + tempItem.getAmount() + ", new amount - " + item.getAmount());
			if ( item.getAmount() < 0) {
				return ResponseUtil.generateErrorCode(400, "cannot change to negative quantity");
			} else if (tempItem.getAmount() > item.getAmount()) {
				withdrawItemAmount(item.getItemNo(), tempItem.getAmount() - item.getAmount());
			} else if (tempItem.getAmount() < item.getAmount()) {
				depositItemAmount(item.getItemNo(), item.getAmount() - tempItem.getAmount());
			}
			itemRepository.save(tempItem);
			return ResponseUtil.generateSuccessMessage(itemRepository.save(item).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(500, "failed to update item");
		}
	}

	@Override
	public ResponseEntity<Object> deleteItemFromStock(long itemNo) {
		return new ResponseEntity<>(itemRepository.deleteByItemNo(itemNo), HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<Object> withdrawItemAmount(long itemNo, int amount) {
		try {
			Item item = itemRepository.findItemByItemNo(itemNo);
			item.setAmount(item.getAmount() - amount);
			itemRepository.save(item);
			return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(404, "item not found");

		}

	}

	@Override
	public ResponseEntity<Object> depositItemAmount(long itemNo, int amount) {
		try {
			Item item = itemRepository.findItemByItemNo(itemNo);
			item.setAmount(item.getAmount() + amount);
			itemRepository.save(item);
			return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.generateErrorCode(404, "item not found");

		}
	}

}
