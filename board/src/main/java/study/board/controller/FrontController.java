package study.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class FrontController {

    @GetMapping
    public String init(){

        return "redirect:/boards";
    }
}