package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Item;
/**
 *
 * @author S.Tatsukawa
 * 商品情報 Repository
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
