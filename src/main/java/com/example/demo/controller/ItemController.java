package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.ItemDto;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
/**
 *
 * @author S.Tatsukawa
 * 商品 (Item) Controller
 *
 */
@Controller
public class ItemController {
    /**
     * 商品情報 Service
     */
    @Autowired
    ItemService itemService;

    /**
     * 商品情報一覧画面を表示
     * @param model Model
     * @return 商品情報の一覧画面のHTML
     */
    @RequestMapping(value = "/item/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Item> items = itemService.serchAll();

        ModelMapper modelMapper = new ModelMapper();

        List<ItemDto> itemsDto = items
                .stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());

        model.addAttribute("items", itemsDto);

        return "item/index";
    }
}
