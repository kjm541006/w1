package study.board.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

@Service
public class AuthService {

//    @Autowired
//    private MemberRepository memberRepository;
//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public boolean login(String username, String password, HttpSession session) {
//        Member member = memberRepository.findByUsername(username);
//        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
//            session.setAttribute("loggedInUser", member); // 세션에 사용자 정보 저장
//            return true;
//        }
//        return false; // 로그인 실패
//    }

    public void logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
    }
}
