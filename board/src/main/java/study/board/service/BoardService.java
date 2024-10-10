package study.board.service;

import study.board.dto.BoardDTO;
import study.board.entity.Board;
import study.board.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    public Board createBoard(Board board, Member member);
    public List<BoardDTO> getAllBoards();
    public Optional<Board> getBoardById(Long id);
    public Board updateBoard(Long id, Board updateParams);
    public void deleteBoard(Long id);
}
