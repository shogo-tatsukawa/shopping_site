package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

/**
 *
 * @author S.Tatsukawa
 *  商品情報 Service
 */
@Service
@Transactional
public class ItemService implements ServiceBase{
// public class ItemService {
    /**
     * 商品情報 Repository
     */
    @Autowired
    ItemRepository itemRepository;

    @Override
    // public Iterable<Item> searchAll() {
    public List<Item> searchAll() {
        // Item Tableの内容を全検索
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> searchOneById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public void insertItem(Item item) {
        LocalDateTime now = LocalDateTime.now();

        // idがnullの場合、新規作成のため、登録日時と更新日時に現在時刻を設定する
        if (item.getId() == null) {
            item.setCreatedAt(now);
            item.setUpdatedAt(now);

         // idがnullでない場合、更新のため、更新日時に現在時刻を設定する
        } else {

            // idを条件に登録済みの商品情報を検索
            Optional<Item> itemCopyOpt = itemRepository.findById(item.getId());

            Item itemCopy = itemCopyOpt.get();
            // 元の商品情報からitem（編集先）に作成日時をコピーする
            item.setCreatedAt(itemCopy.getCreatedAt());
            // 更新日時は現在の時刻を設定する
            item.setUpdatedAt(now);
        }
        itemRepository.save(item);
    }

}
