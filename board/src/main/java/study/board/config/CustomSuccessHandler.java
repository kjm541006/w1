package study.board.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import study.board.entity.Member;
import study.board.service.MemberService;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("--------onAuthenticationSuccess-----------");
        String username = authentication.getName();
        log.info("custom SuccessHandler username: " + username);
        Optional<Member> loggedInMember = memberService.getMemberByUsername(username);
        log.info("custom SuccessHandler loggedInMember: " + loggedInMember);

        if (loggedInMember.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", loggedInMember.get());

            // 로그
            log.info("로그인한 사용자: {}", username);
            log.info("로그인한 사용자 id: {}", loggedInMember.get().getId());
        } else {
            log.warn("로그인한 사용자를 찾을 수 없습니다.");
        }

        response.sendRedirect("/boards"); // 로그인 성공 후 이동할 페이지
    }
}
