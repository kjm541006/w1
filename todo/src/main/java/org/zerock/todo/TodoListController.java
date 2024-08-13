package org.zerock.todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "todoListController", urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {
    public static List<String> todos = new ArrayList<>();
    public static String name = "asdf";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("name", name);
        req.setAttribute("todos", todos);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/todo/list.jsp");
        rd.forward(req, res);
    }

    public static void addTodos(String title) {
        todos.add(title);
    }
}
