package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Item;
/**
 *
 * @author S.Tatsukawa
 * Entity、DTO、Form Converter
 */
@Component
public class ItemConverter {
    /**
     * ModelMapper
     */
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * List<Entity> -> List<Dto>
     * @param items List<Item>
     * @return itemsDto List<ItemDto>
     */
    public List<ItemDto> toDtoList(List<Item> items) {
        List<ItemDto> itemsDto = new ArrayList<>();

        for (Item item : items) {
            itemsDto.add(modelMapper.map(item, ItemDto.class));
        }
        return itemsDto;
    }

    /**
     * Entity -> Dto
     * @param item Item
     * @return itemDto ItemDto
     */
    public ItemDto toDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    /**
     * Entity -> Form
     * @param item Item
     * @return itemForm ItemForm
     */
    public ItemForm toForm(Item item) {
        return modelMapper.map(item, ItemForm.class);
    }

    /**
     * Form -> Entity
     * @param itemForm ItemForm
     * @return item Item
     */
    public Item toItem(ItemForm itemForm) {
        return modelMapper.map(itemForm, Item.class);
    }
}
