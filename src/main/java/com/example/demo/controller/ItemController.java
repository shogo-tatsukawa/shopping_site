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

import jakarta.servlet.http.HttpServletRequest;
/**
 *
 * @author S.Tatsukawa
 * 商品 (Item) Controller
 *
 */
@Controller
public class ItemController {
    //@Autowired
    //HttpServletRequest request;
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
     * @return 商品情報の一覧画面
     */
    @RequestMapping(value = "/item/index", method = RequestMethod.GET)
    public String index(Model model) {
        // すべての商品情報を取得
        List<Item> items = itemService.searchAll();

        // DTOに変換
        List<ItemDto> itemsDto = itemConverter.toDtoList(items);

        // modelに格納
        model.addAttribute("items", itemsDto);

        return "item/index";
    }

    /**
     * 新規登録画面を表示する
     * @param model Model
     * @return 新規登録画面
     */
    @RequestMapping(value = "/item/new", method = RequestMethod.GET)
    public String entryNew(Model model, HttpServletRequest request) {
        // 空のItemFormをmodelに格納
        model.addAttribute("itemForm", new ItemForm());

        // CSRF対策用トークンをmodelに格納
        String token = request.getSession().getId();
        model.addAttribute("token", token);

        return "item/new";
    }

    /**
     * 商品情報を新規登録する
     * @param token String トークン(CSRF対策用)
     * @param itemForm ItemForm 商品情報のForm
     * @param bindingResult BindingResult Formのバリデーション結果
     * @param model Model
     * @return 商品情報の一覧画面
     */
    @RequestMapping(value = "/item/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("token") String token,
                          @Validated @ModelAttribute ItemForm itemForm,
                          BindingResult bindingResult, Model model,
                          HttpServletRequest request) {
        // CSRF対策 tokenのチェック
        if (token == null || !(token.equals(request.getSession().getId()))) {
            // エラーの場合、エラー画面に遷移
            return "/item/error/unknown";
        } else {
            // Itemに変換
            Item item = itemConverter.toItem(itemForm);

            // バリデーション結果の判定
            if (!bindingResult.hasErrors()) {
                // エラーがない場合は、商品情報を登録
                itemService.insertItem(item);
                // 商品情報一覧画面にリダイレクト
                return "redirect:/item/index";
            } else {
                // エラーがある場合は、新規登録画面を呼ぶ
                return "/item/new";
            }
        }
    }

    /**
     * 商品情報の詳細を表示する
     * @param id Long 詳細を表示したい商品情報のid
     * @param model Model
     * @return 商品情報の詳細画面
     */
    @RequestMapping(value="/item/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="id") Long id, Model model) {
         // idを条件に商品情報を取得する
         Optional<Item> itemOpt = itemService.searchOneById(id);

         // 取得したデータの確認
         if (itemOpt.isPresent() && itemOpt.get().getDeleteFlag() == false) {
             // 中身が空でないかつ論理削除されていなければデータを取り出す
             Item item = itemOpt.get();
             // DTOに変換
             ItemDto itemDto = itemConverter.toDto(item);
             // modelに格納
             model.addAttribute("item", itemDto);
             // 商品情報の詳細画面を表示
             return "/item/show";

         } else {
             // 中身が空もしくは論理削除されている場合、エラー画面を表示
             return "/item/error/unknown";
         }
    }

    /**
     * 商品情報の編集画面を表示する
     * @param id Long 編集したい商品情報のid
     * @param model Model
     * @return 商品情報の編集画面
     */
    @RequestMapping(value="/item/edit", method=RequestMethod.GET)
    public String edit(@RequestParam(value="id") Long id, Model model, HttpServletRequest request) {
        // idを条件に商品情報を取得する
        Optional<Item> itemOpt = itemService.searchOneById(id);

        // 取得したデータの確認
        if (itemOpt.isPresent() && itemOpt.get().getDeleteFlag() == false) {
            // 中身が空でないかつ論理削除されていなければデータを取り出す
            Item item = itemOpt.get();
            // ItemFormに変換
            ItemForm itemForm = itemConverter.toForm(item);
            // itemFormをmodelに格納
            model.addAttribute("itemForm", itemForm);
            // CSRF対策用トークンをModelに設定
            String token = request.getSession().getId();
            model.addAttribute("token", token);
            // 商品情報の編集画面に遷移
            return "/item/edit";

        } else {
            // 中身がからもしくは論理削除されている場合、エラー画面を表示
            return "/item/error/unknown";
        }
    }

    /**
     * 商品情報の更新をする
     * @param token String トークン(CSRF対策用)
     * @param itemForm ItemForm 商品情報のForm
     * @param bindingResult BindingResult Formのバリデーション結果
     * @param model Model
     * @return 商品情報の一覧画面
     */
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("token") String token,
                          @Validated @ModelAttribute ItemForm itemForm,
                          BindingResult bindingResult, Model model,
                          HttpServletRequest request) {
        // CSRF対策 tokenのチェック
        if (token == null || !(token.equals(request.getSession().getId()))) {
            // エラーの場合、エラー画面に遷移
            return "/item/error/unknown";
        } else {
            // Itemに変換
            Item item = itemConverter.toItem(itemForm);

            // バリデーション結果の判定
            if (!bindingResult.hasErrors()) {
                // エラーがない場合は、商品情報を登録
                itemService.insertItem(item);
                // 商品情報の一覧画面にリダイレクト
                return "redirect:/item/index";
            } else {
                // エラーがある場合は、編集画面を呼ぶ
                return "/item/edit";
            }
        }
    }

    /**
     * 商品情報を論理削除する
     * @param id Long 論理削除したい商品情報のid
     * @return 商品情報の一覧画面
     */
    @RequestMapping(value = "/item/destroy", method = RequestMethod.POST)
    public String destroy(@RequestParam(value="id") Long id) {
        // idを条件に商品情報を論理削除する
        itemService.destroy(id);

        return "redirect:/item/index";
    }

}
