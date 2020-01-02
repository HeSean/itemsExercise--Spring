package com.seanhed.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seanhed.data.beans.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Item findItemByItemNo(long itemNo);

	List<Item> deleteByItemNo(long itemNo);

}
