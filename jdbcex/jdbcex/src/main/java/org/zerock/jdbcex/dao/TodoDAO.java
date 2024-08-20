package org.zerock.jdbcex.dao;

import com.sun.tools.javac.comp.Todo;
import lombok.Cleanup;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class TodoDAO {


    public String getTime() {
        String now = null;
        try (Connection connection = ConnectionUtil.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT NOW()");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) { // 결과가 있는지 확인
                now = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }


    public String getTime2() throws Exception {
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String now = resultSet.getString(1);

        return now;
    }


    public void insert(TodoVO vo) throws Exception{
        String sql = "insert into tbl_todo (title, dueDate, completed) values (?, ?, ?)";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isCompleted());

        preparedStatement.execute();
    }


    public List<TodoVO> selectAll() throws Exception{

        String sql = "select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        // 다음 행이 있을 경우 true, 다음 행이 없을 경우 false
        while(resultSet.next()) {
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .completed(resultSet.getBoolean("completed"))
                    .build();

            list.add(vo);
        }
        return list;
    }

    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();


        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .completed(resultSet.getBoolean("completed"))
                .build();

        return vo;
    }

    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt = connection.prepareStatement(sql);

        psmt.setLong(1, tno);

        psmt.executeUpdate();
    }

    public void updateOne(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title = ?, dueDate = ?, completed = ? where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt = connection.prepareStatement(sql);

        psmt.setString(1, todoVO.getTitle());
        psmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        psmt.setBoolean(3, todoVO.isCompleted());
        psmt.setLong(4, todoVO.getTno());

        psmt.executeUpdate();


    }

}

