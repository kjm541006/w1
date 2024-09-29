package study.project1.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
