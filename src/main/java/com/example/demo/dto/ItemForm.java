package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message="名前は必須入力項目です")
    private String name;

    /**
     * 商品説明
     */
    @NotBlank(message="商品情報は必須入力項目です")
    private String description;

}
