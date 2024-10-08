package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("Login check Filter......");

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        HttpSession session = req.getSession();

        if(session.getAttribute("loginInfo") != null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //session에 loginInfo가 없다면 쿠키 체크
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        //세션에도 없고 쿠키도 없다면 로그인화면으로 이동
        if(cookie == null){
            resp.sendRedirect("/login");
            return;
        }

        //쿠키 존재 할 경우
        log.info("Cookie 존재");
        //uuid 값
        String uuid = cookie.getValue();

        try{
            MemberDTO memberDTO = MemberService.INSTANCE.getByUuid(uuid);

            log.info("쿠키로 조회한 사용자 정보: " + memberDTO);
            if(memberDTO == null){
                throw new Exception("Cookie value is not valid");
            }
            //회원 정보 세션에 추가
            session.setAttribute("loginInfo", memberDTO);
            filterChain.doFilter(servletRequest, servletResponse);


        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name){

        if(cookies == null || cookies.length == 0){
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies).filter(ck -> ck.getName().equals(name)).findFirst();

        return result.isPresent() ? result.get() : null;
    }
}
