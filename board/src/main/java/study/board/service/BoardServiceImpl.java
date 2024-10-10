package study.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import study.board.dto.BoardDTO;
import study.board.dto.CommentDTO;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberServiceImpl memberService;

    @Override
    public Board createBoard(BoardDTO boardDTO, Member member) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Member> loggedInMember = memberService.getMemberByUsername(username);

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setAuthor(loggedInMember.get());

        return boardRepository.save(board);
    }

    @Override
    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> {
                    String authorName = board.getAuthor() != null ? board.getAuthor().getUsername() : "Anonymous"; // 작성자 이름 가져오기
                    List<CommentDTO> commentDTOs = board.getComments().stream()
                            .map(comment -> new CommentDTO(comment.getId(), comment.getContent(), comment.getAuthor().getUsername()))
                            .collect(Collectors.toList());

                    return new BoardDTO(board.getId(), board.getTitle(), board.getContent(), authorName, commentDTOs);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Board> getBoardById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username = authentication.getName(); // 사용자 이름
            log.info("boardService get board principal = " + principal);
            log.info("boardService get board username = " + username);
            // 추가적인 사용자 정보 접근 가능
        } else {
            // 인증되지 않은 사용자 처리
            log.info("-------get board: username not found=--------");
        }

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
