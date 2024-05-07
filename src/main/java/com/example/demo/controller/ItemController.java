package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String create(@Validated @ModelAttribute ItemForm itemForm, BindingResult bindingResult, Model model) {
        Item item = itemConverter.toItem(itemForm);

        // バリデーション結果の判定
        if (!bindingResult.hasErrors()) {
            itemService.insertItem(item);
            // indexにリダイレクト
            return "redirect:/item/index";
        } else {
            // エラーがある場合は、新規登録画面を呼ぶ
            // model.addAttribute(itemForm);
            return "/item/new";
        }
    }

    /**
     * 商品情報の詳細を表示する
     * @param 詳細を表示したい商品情報のid (Long)
     */
    @RequestMapping(value="/item/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="id") Long id, Model model) {
        if (id == null) {
            return "/item/index";
        // idがnullでない場合、idをキーに商品情報取得
        } else {
            Optional<Item> itemOpt = itemService.searchOneById(id);
            // 中身が空でなければデータを取り出す
            if (itemOpt.isPresent()) {
                Item item = itemOpt.get();
                ItemDto itemDto = itemConverter.toDto(item);
                model.addAttribute("item", itemDto);
                return "/item/show";
            } else {
                return "/item/index";
            }
        }
    }

    /**
     * 商品情報の編集画面を表示する
     * @param 編集したい商品情報のid (Long)
     */
    @RequestMapping(value="/item/edit", method=RequestMethod.GET)
    public String edit(@RequestParam(value="id") Long id, Model model) {
        if (id == null) {
            return "/item/index";
        } else {
            Optional<Item> itemOpt = itemService.searchOneById(id);
            // 中身が空でなければデータを取り出す
            if (itemOpt.isPresent()) {
                Item item = itemOpt.get();
                ItemForm itemForm = itemConverter.toForm(item);
                model.addAttribute("itemForm", itemForm);
                return "/item/edit";
            } else {
                return "/item/index";
            }
        }
    }

    /**
     * 商品情報の更新をする
     */
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute ItemForm itemForm, BindingResult bindingResult, Model model) {
        Item item = itemConverter.toItem(itemForm);

        // バリデーション結果の判定
        if (!bindingResult.hasErrors()) {
            itemService.insertItem(item);
            // indexにリダイレクト
            return "redirect:/item/index";
        } else {
            // エラーがある場合は、編集画面を呼ぶ
            // model.addAttribute(itemForm);
            return "/item/edit";
        }
    }
}
