package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerV4() {
        controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();

        ControllerV4 controllerV4 = controllerV4Map.get(requestURI);
        if(controllerV4 == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 모든 요청 파라미터의 정보를 얻는 Map 객체 생성
        Map<String, String> paramMap = createParamMap(req);

        Map<String, Object> model = new HashMap<>();

        // 각 컨트롤러의 process는 view 이름만 리턴
        String viewName = controllerV4.process(paramMap, model);

        // jsp로 전달 (RequestDispatcher MyView로 분리 및 jsp 경로명을 반환해주는 viewResolver 메서드 사용)
        MyView view = viewResolver(viewName);
        // dispatcher는 req를 사용해야 하므로 view에서 받은 model을 req로 변환 후 사용
        view.render(model, req, resp);

    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}
