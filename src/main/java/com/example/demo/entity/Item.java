package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author S.Tatsukawa
 * 商品情報 Entity
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "item")
public class Item {
    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名前（商品名）
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 商品説明
     */
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 登録日時
     */
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 削除されたかどうか
     */
    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;
}
