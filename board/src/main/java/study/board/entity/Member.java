package study.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "author")
    private Set<Board> boards; // 해당 회원이 작성한 게시판 목록

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments; // 해당 회원이 작성한 댓글 목록
}
