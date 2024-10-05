package study.board.service;

import study.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    public Board createBoard(Board board);
    public List<Board> getAllBoards();
    public Optional<Board> getBoardById(Long id);
    public Board updateBoard(Long id, Board updateParams);
    public void deleteBoard(Long id);
}
