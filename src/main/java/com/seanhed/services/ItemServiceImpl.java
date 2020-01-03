package com.seanhed.services;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seanhed.data.beans.Item;
import com.seanhed.data.repo.ItemRepository;
import com.seanhed.services.dao.ItemService;
import static com.seanhed.utils.MinLog.*;
import com.seanhed.utils.ResponseUtil;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	private static final String ITEM_NOT_FOUND = "Item not found.";
	private static final String ITEMS_NOT_FOUND = "Items not found.";
	private static final String FAILED_TO_ADD = "Failed to add new item.";
	private static final String FAILED_TO_UPDATE = "Failed to update item.";

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * Get item by item number
	 *
	 * @param itemNo - item number
	 * @return wanted item wrapped in ResponseEntity
	 * @exception HttpStatus 404 NOT_FOUND on failing to fetch item
	 */
	@Override
	public ResponseEntity<Object> getItemByItemNo(long itemNo) {
		try {
			return new ResponseEntity<>(itemRepository.findById(itemNo), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(404, ITEM_NOT_FOUND);
		}
	}

	/**
	 * Get all items
	 *
	 * @return all items wrapped in ResponseEntity
	 * @exception HttpStatus 404 NOT_FOUND on failing to fetch items
	 */
	@Override
	public ResponseEntity<Object> getAllItems() {
		try {
			List<Item> items = itemRepository.findAll();
			info("items retrived are -> " + items);
			return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(404, ITEMS_NOT_FOUND);
		}
	}

	/**
	 * Add new item to database
	 *
	 * @param item - item object
	 * @return newly added item
	 * @exception HttpStatus 500 INTERNAL_SERVER_ERROR on failing to add item
	 */
	@Override
	public ResponseEntity<Object> addNewItem(Item item) {
		try {
			info("added item is " + item);
			return ResponseUtil.generateSuccessMessage(itemRepository.save(item).toString());
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(500, FAILED_TO_ADD);
		}
	}

	/**
	 * Update existing item in database
	 *
	 * @param item - item object
	 * @return updated item
	 * @exception  HttpStatus 500 INTERNAL_SERVER_ERROR on failing to update item
	 */
	@Override
	public ResponseEntity<Object> updateItem(Item item) {
		try {
			info("updateItem item  " + item);
			Item tempItem = itemRepository.getOne(item.getItemNo());
			tempItem.setName(item.getName());
			tempItem.setInventoryCode(item.getInventoryCode());
			return ResponseUtil.generateSuccessMessage(itemRepository.save(tempItem).toString());
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(500, FAILED_TO_UPDATE);
		}
	}

	/**
	 * Delete item from stock
	 *
	 * @param itemNo - item number
	 * @return deleted item wrapped in ResponseEntity
	 * @exception HttpStatus 404 NOT_FOUND on failing to fetch item for delete
	 */
	@Override
	public ResponseEntity<Object> deleteItemFromStock(long itemNo) {
		try {
			return new ResponseEntity<>(itemRepository.deleteByItemNo(itemNo), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(404, ITEM_NOT_FOUND);
		}
	}

	/**
	 * Withdraw item amount from stock
	 *
	 * @param itemNo - item number
	 * @param amount - amount to withdraw from item's quantity
	 * @return updated item with new amount
	 * @exception HttpStatus 404 INTERNAL_SERVER_ERROR  on failing to fetch item for withdrawal
	 */
	@Override
	public ResponseEntity<Object> withdrawItemAmount(long itemNo, int amount) {
		try {
			Item item = itemRepository.findItemByItemNo(itemNo);
			item.setAmount(item.getAmount() - amount);
			itemRepository.save(item);
			return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(404, ITEM_NOT_FOUND);
		}
	}

	/**
	 * Deposit item amount to stock
	 *
	 * @param itemNo - item number
	 * @param amount - amount to deposit to item's quantity
	 * @return updated item with new amount
	 * @exception HttpStatus 404 NOT_FOUND on failing to fetch item for deposit
	 */
	@Override
	public ResponseEntity<Object> depositItemAmount(long itemNo, int amount) {
		try {
			Item item = itemRepository.findItemByItemNo(itemNo);
			item.setAmount(item.getAmount() + amount);
			itemRepository.save(item);
			return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			error(e.getLocalizedMessage());
			return ResponseUtil.generateErrorCode(404, ITEM_NOT_FOUND);
		}
	}

}
