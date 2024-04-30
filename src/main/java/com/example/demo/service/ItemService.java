package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

/**
 *
 * @author S.Tatsukawa
 *  商品情報 Service
 */
@Service
public class ItemService {
    /**
     * 商品情報 Repository
     */
    @Autowired
    ItemRepository itemRepository;
    public List<Item> serchAll() {
        // Item Tableの内容を全検索
        return itemRepository.findAll();
    }
}
