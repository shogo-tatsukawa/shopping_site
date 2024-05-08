package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Item;
/**
 *
 * @author S.Tatsukawa
 * ServiceクラスのBaseインターフェイス
 *
 */
public interface ServiceBase {

    /**
     * テーブル中のレコードを全件取得
     * @return 全件のレコード（Iterable型）
     */
    // public Iterable<Item> searchAll();
    public abstract List<Item> searchAll();

    /**
     * idをキーにしてレコードを1件取得する
     * @param id Long
     * @return 1件のレコード（Optional型）
     */
    public abstract Optional<Item> searchOneById(Long id);

    /**
     * 取得したデータをDBにInsertする
     * @param item Item
     */
    public abstract void insertItem(Item item);

    /**
     * idをキーにして商品情報を論理削除する
     * @param id Long
     */
    public abstract void destroy(Long id);
}
