package org.zerock.w2.dao;

import lombok.Cleanup;
import org.zerock.w2.domain.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    public MemberVO getWithPassword(String mid, String mpw) throws Exception{

        String sql = "select mid, mpw, mname from tbl_member where mid = ? and mpw = ?";


        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, mid);
        preparedStatement.setString(2, mpw);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        MemberVO memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .build();

        return memberVO;

    }
}
