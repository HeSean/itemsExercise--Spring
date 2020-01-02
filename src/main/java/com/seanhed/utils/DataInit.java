package com.seanhed.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.seanhed.data.beans.Item;
import com.seanhed.data.repo.ItemRepository;

@Component
public class DataInit implements ApplicationRunner {

	@Autowired
	ItemRepository itemRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (itemRepository.count() == 0) {
			Item item1 = new Item();
			item1.setName("Pen");
			item1.setInventoryCode("ABC");
			item1.setAmount(5);

			Item item2 = new Item();
			item2.setName("Notebook");
			item2.setInventoryCode("DEF");
			item2.setAmount(5);

			Item item3 = new Item();
			item3.setName("Eraser");
			item3.setInventoryCode("HIG");
			item3.setAmount(10);

			itemRepository.save(item1);
			itemRepository.save(item2);
			itemRepository.save(item3);

		}

	}

}
