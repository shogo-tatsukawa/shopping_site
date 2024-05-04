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
     * Entity -> Dto
     * @param items Iterable<Item>
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
     * Form -> Entity
     * @param itemForm
     * @return item
     */
    public Item toItem(ItemForm itemForm) {
        return modelMapper.map(itemForm, Item.class);
    }
}
