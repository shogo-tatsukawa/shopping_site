package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
@EnableJpaAuditing
public class ItemService implements ServiceBase{
// public class ItemService {
    /**
     * 商品情報 Repository
     * コンストラクタインジェクションでDI
     */
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
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
        // idの有無を確認
        if (item.getId() == null) {
            // nullの場合、新規作成のため削除フラグを初期値（false）に設定する
            item.setDeleteFlag(false);

        } else {
            // idがnullでない場合、更新のため削除フラグをコピーする
            // idを条件に登録済みの商品情報を検索
            Optional<Item> itemCopyOpt = searchOneById(item.getId());

            Item itemCopy = itemCopyOpt.get();
            // 元の商品情報からitem（編集先）に削除フラグをコピーする
            item.setDeleteFlag(itemCopy.getDeleteFlag());
        }
        // 商品情報を登録する
        itemRepository.save(item);
    }

    @Override
    public void destroy(Long id) {
        // idを条件に登録済みの商品情報を検索
        Optional<Item> itemOpt = searchOneById(id);

        Item item = itemOpt.get();

        // 論理削除フラグを立てる
        item.setDeleteFlag(true);
    }

}
