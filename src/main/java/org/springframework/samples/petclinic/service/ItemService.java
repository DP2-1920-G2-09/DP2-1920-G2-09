
package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

	private ItemRepository itemRepository;


	@Autowired
	public ItemService(final ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional
	public void saveItem(final Item item) {
		this.itemRepository.save(item);
	}

	@Transactional
	public List<Item> findItemsInShoppingCart(final int shoppingCartId) {
		return this.itemRepository.findItemsInShoppingCard(shoppingCartId);
	}

	@Transactional
	public Item checkIfItemIsIntheShoppingCart(final int shoppingCartId, final int productId) {
		return this.itemRepository.checkIfItemIsIntheShoppingCart(shoppingCartId, productId);
	}

	@Transactional
	public void deleteItem(final int itemId) {
		this.itemRepository.deleteById(itemId);
	}

	@Transactional
	public Item findItemById(final int itemId) {
		return this.itemRepository.findById(itemId).orElse(null);
	}

}
