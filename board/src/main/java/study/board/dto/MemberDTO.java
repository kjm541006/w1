package study.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDTO {

    private String username;
    private List<BoardDTO> boards;
}

