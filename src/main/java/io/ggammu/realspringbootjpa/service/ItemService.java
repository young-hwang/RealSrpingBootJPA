package io.ggammu.realspringbootjpa.service;

import io.ggammu.realspringbootjpa.controller.BookForm;
import io.ggammu.realspringbootjpa.domain.item.Book;
import io.ggammu.realspringbootjpa.domain.item.Item;
import io.ggammu.realspringbootjpa.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(Long id, BookForm param) {
        Item item = itemRepository.findOne(id);
        item.setName(param.getName());
        item.setStockQuantity(param.getStockQuantity());
        item.setPrice(param.getPrice());
    }
}
