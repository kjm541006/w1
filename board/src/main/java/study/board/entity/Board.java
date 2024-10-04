package study.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne // 다대일 관계
    @JoinColumn(name = "author_id") // 외래 키 설정
    private Member author; // 게시판 작성자

    @OneToMany(mappedBy = "board")
    private Set<Comment> comments;
}
