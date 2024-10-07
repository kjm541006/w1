package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.board.entity.Member;
import study.board.service.AuthService;
import study.board.service.MemberServiceImpl;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final AuthService authService;

    // 회원 등록 페이지
    @GetMapping("/register")
    public String register(){

        return "/member/register";
    }
    // 회원 추가
    @PostMapping("/register")
    public String addMember(Member member, RedirectAttributes redirectAttributes){
        memberService.register(member);

        return "redirect:/member/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String getLogin(){

        return "/member/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (authService.login(username, password, session)) {
            return "redirect:/boards"; // 로그인 성공 시 홈으로 리다이렉트
        } else {
            return "redirect:/member/login?error"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }

    // 로그아웃
    // 시큐리티 수정 필요
//    @PostMapping("/logout")
//    public String logout(RedirectAttributes redirectAttributes){
//
//        return "redirect:/member/login";
//    }

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
