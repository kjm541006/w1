package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SpringMemberListControllerV1 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process(){

        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("members");
        // addObject는 매개변수가 1개일 경우 작성된 객체의 클래스이름을 소문자로 변환해 키로 사용
        // 즉 members만 넣을경우 key = list
        mv.addObject("members", members);

        return mv;
    }
}
