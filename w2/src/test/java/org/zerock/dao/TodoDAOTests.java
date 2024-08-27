package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.w2.dao.TodoDAO;
import org.zerock.w2.domain.TodoVO;

import java.util.List;
import java.time.LocalDate;

public class TodoDAOTests {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception{
        todoDAO.getTime();
    }

    @Test
    public void testInsert() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title..")
                .dueDate(LocalDate.now())
                .completed(true)
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception{

        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testOne() throws Exception{
        TodoVO vo = todoDAO.selectOne(5L);
        System.out.println(vo);
    }

    @Test
    public void deleteOne() throws Exception{
        todoDAO.deleteOne(2L);

    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO vo = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.now())
                .completed(true)
                .build();

        todoDAO.updateOne(vo);

    }
}
