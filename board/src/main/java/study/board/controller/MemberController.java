package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // 회원 등록 페이지
    @GetMapping("/register")
    public String register(){

        return "/member/register";
    }
    // 회원 추가
    @PostMapping("/register")
    public String addMember(RedirectAttributes redirectAttributes){

        return "redirect:/member/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String getLogin(){

        return "/member/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(RedirectAttributes redirectAttributes){

        return "redirect:/boards";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes){

        return "redirect:/member/login";
    }

    // 회원 상세 페이지
    @GetMapping("/{memberId}")
    public String getDetail(){

        return "/member/detail";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/{memberId}/edit")
    public String getEdit(){

        return "/member/edit";
    }

    // 회원 정보 수정
    @PostMapping("/{memberId}/edit")
    public String edit(RedirectAttributes redirectAttributes){

        return "redirect:/member/{memberId}";
    }
}
