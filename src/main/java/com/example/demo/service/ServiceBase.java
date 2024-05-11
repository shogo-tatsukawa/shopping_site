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
     * @return List<Item> 全件のレコード
     */
    public abstract List<Item> searchAll();

    /**
     * idをキーにしてレコードを1件取得する
     * @param id Long 取得したいレコードのid
     * @return Optional<Item> 1件のレコード
     */
    public abstract Optional<Item> searchOneById(Long id);

    /**
     * 取得したデータをDBにInsertする
     * @param item Item 登録したい商品情報
     */
    public abstract void insertItem(Item item);

    /**
     * idをキーにして商品情報を論理削除する
     * @param id Long 論理削除したいレコードのid
     */
    public abstract void destroy(Long id);
}
