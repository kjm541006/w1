package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(" [전체 파라미터 조회] - start ");

        req.getParameterNames()
                .asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + ": " + req.getParameter(paramName)));
                // paramName은 key(getParamNames로 받아옴), getParameter로 값 꺼내오기 가능

        System.out.println(" [전체 파라미터 조회] - end ");

        System.out.println();

        System.out.println("[단일 파라미터 조회] - start");

        String username = req.getParameter("username");
        System.out.println("username = " + username);
        String age = req.getParameter("age");
        System.out.println("age = " + age);

        System.out.println("[단일 파라미터 조회] - end");

        System.out.println("[이름이 같은 복수 파라미터 조회] - start");
        String[] usernames = req.getParameterValues("username");
        for (String s : usernames) {
            System.out.println("s = " + s);

        }
        System.out.println("[이름이 같은 복수 파라미터 조회] - end");

    }
}
