package study.project1.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.project1.domain.Item;
import study.project1.repository.ItemRepositoryImpl;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/items")
public class ItemController {

    ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

    @GetMapping
    public String list(Model model){
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute("itemList", itemList);

        return "/items/list";
    }

    @GetMapping("/{itemId}")
    public String findOne(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.find(itemId);

        model.addAttribute("item", findItem);

        return "/items/item";
    }

    @GetMapping("/add")
    public String addForm(){

        return "/items/add";
    }

    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes){
        // 추가 기능 구현
        log.info("추가");
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.find(itemId);
        model.addAttribute("item", findItem);

        return "/items/edit";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, Item item){
        itemRepository.update(itemId, item);

        return "redirect:/items/{itemId}";
    }


    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
