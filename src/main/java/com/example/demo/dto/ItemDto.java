package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;
/**
 *
 * @author S.Tatsukawa
 * 商品情報のDTOモデル
 */
@Data
public class ItemDto {
    /**
     * ID
     */
    private Long id;

    /*
     * 名前
     */
    private String name;

    /**
     * 商品説明
     */
    private String description;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;
}
