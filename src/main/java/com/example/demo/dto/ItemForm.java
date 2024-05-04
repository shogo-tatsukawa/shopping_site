package com.example.demo.dto;

import lombok.Data;

/**
 *
 * @author S.Tatsukawa
 * 商品情報のFormモデル
 *
 */
@Data
public class ItemForm {
    /**
     * ID
     */
    private Long id;

    /**
     * 名前
     */
    private String name;

    /**
     * 商品説明
     */
    private String description;

}
