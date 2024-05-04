package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.ItemConverter;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.ItemForm;
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
     * 商品情報 Converter
     */
    @Autowired
    ItemConverter itemConverter;

    /**
     * 商品情報一覧画面を表示
     * @param model Model
     * @return 商品情報の一覧画面のHTML
     */
    @RequestMapping(value = "/item/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Item> items = itemService.searchAll();

        List<ItemDto> itemsDto = itemConverter.toDtoList(items);

        model.addAttribute("items", itemsDto);

        return "item/index";
    }

    /**
     * 新規登録画面を表示する
     */
    @RequestMapping(value = "/item/new", method = RequestMethod.GET)
    public String entryNew(Model model) {
        //ItemDto itemDto = new ItemDto();
        model.addAttribute("itemForm", new ItemForm());

        return "item/new";
    }

    /**
     * 商品情報を新規登録する
     */
    @RequestMapping(value = "/item/create", method = RequestMethod.POST)
    public String create(@Validated ItemForm itemForm, BindingResult bindingResult, Model model) {
        Item item = itemConverter.toItem(itemForm);

        // バリデーション結果の判定
        if (!bindingResult.hasErrors()) {
            itemService.insertItem(item);
            return "redirect:/item/index";
        } else {
            // エラーがある場合は、新規登録画面を呼ぶ
            // model.addAttribute(itemForm);
            return "/item/new";
        }
    }
}
