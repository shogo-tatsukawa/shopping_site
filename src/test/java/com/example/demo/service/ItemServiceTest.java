package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@ExtendWith(SpringExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void test_searchOneById() {
        LocalDateTime now = LocalDateTime.now();

        when(itemRepository.findById((long) 10)).thenReturn(getItemId10(now));
        Optional<Item>  itemOpt = itemService.searchOneById((long) 10);
        Item item = itemOpt.get();

        // 結果確認
        assertThat(itemOpt.isPresent()).isEqualTo(true);
        assertThat(item.getId()).isEqualTo((long) 10);
        assertThat(item.getName()).isEqualTo("test");
        assertThat(item.getDescription()).isEqualTo("test");
        assertThat(item.getCreatedAt()).isEqualTo(now);
        assertThat(item.getUpdatedAt()).isEqualTo(now);
        assertThat(item.getDeleteFlag()).isEqualTo(false);
    }

    public Optional<Item> getItemId10(LocalDateTime now) {

        // LocalDateTime now = LocalDateTime.now();
        Item item = new Item();
        item.setId((long) 10);
        item.setName("test");
        item.setDescription("test");
        item.setCreatedAt(now);
        item.setUpdatedAt(now);
        item.setDeleteFlag(false);

        return Optional.ofNullable(item);
    }

}
