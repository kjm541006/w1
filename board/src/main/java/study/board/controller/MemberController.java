package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        log.info("get register 호출");

        return "member/register";
    }
    // 회원 추가
    @PostMapping("/register")
    public String addMember(Member member, RedirectAttributes redirectAttributes){
        memberService.register(member);

        return "redirect:/member/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String getLogin(String errorCode, String logout, HttpSession session){
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        log.info("loggedInUser: " + loggedInUser);

        if(loggedInUser != null){

            return "redirect:/boards";
        }else{
            log.info("------get login------");
            log.info("logout: " + logout);

            if(logout != null){
                log.info("user logout.......");
            }

            return "member/login";
        }
    }

    // 로그인
    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        log.info("----------login------------");
    }

    // 로그아웃
//    @PostMapping("/logout")
//    public String logout(HttpSession session){
////        authService.logout(session);
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
    public String edit(@PathVariable Long memberId, Member member, RedirectAttributes redirectAttributes){
        memberService.updateMember(memberId, member);

        return "redirect:/member/{memberId}";
    }
}
