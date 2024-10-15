package study.board.service;

import study.board.dto.BoardDTO;
import study.board.entity.Board;
import study.board.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    public Board createBoard(BoardDTO boardDTO, Member member);
    public List<BoardDTO> getAllBoards();
    public Optional<Board> getBoardById(Long id);
    public void updateBoard(Long boardId, BoardDTO updateParams);
    public void deleteBoard(Long id);

}
