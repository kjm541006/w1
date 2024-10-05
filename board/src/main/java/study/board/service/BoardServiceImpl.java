package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.entity.Board;
import study.board.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Board createBoard(Board board) {

        return boardRepository.save(board);
    }

    @Override
    public List<Board> getAllBoards() {

        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> getBoardById(Long id) {

        return boardRepository.findById(id);
    }

    @Override
    public Board updateBoard(Long id, Board updateParams) {
        Board findBoard = boardRepository.findById(id).orElseThrow(()-> new RuntimeException("Board Not Found"));
        findBoard.setTitle(updateParams.getTitle());
        findBoard.setContent(updateParams.getContent());

        return boardRepository.save(findBoard);
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
