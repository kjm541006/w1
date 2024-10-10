package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName; // 작성자 이름 추가
    private List<CommentDTO> comments; // 댓글 리스트 추가
}
