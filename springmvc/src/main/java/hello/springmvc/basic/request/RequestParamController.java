package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void RequestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    // 가장 권장되는 방식
    @RequestMapping("/request-param-v2")
    public String RequestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge){

        log.info("username={}, age={}", memberName, memberAge);

        return "ok";

    }

    // HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String RequestParamV3(
            @RequestParam String username,
            @RequestParam int age){

        log.info("username={}, age={}", username, age);

        return "ok";

    }

    // String` , `int` , `Integer` 등의 단순 타입이면 `@RequestParam`도 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String RequestParamV4(String username, int age){

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String RequestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age){

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    // 값이 넘어오지 않았을 경우에 대한 기본값 지정
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String RequestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") Integer age){

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String RequestParamMap(@RequestParam Map<String, Object> paramMap){

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }
}
