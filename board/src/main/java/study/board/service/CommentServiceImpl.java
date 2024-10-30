package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.dto.CommentDTO;
import study.board.entity.Board;
import study.board.entity.Comment;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.repository.CommentRepository;
import study.board.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Comment createComment(CommentDTO commentDTO, Long loggedInUserId) {
        // 게시글 조회
        Board board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // 작성자 조회
        Member author = memberRepository.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // 댓글 객체 생성 및 설정
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setBoard(board); // 게시글 설정
        comment.setAuthor(author); // 작성자 설정

        // 댓글 저장
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment updateComment(Long id, CommentDTO updateParams) {
        Comment findComment = commentRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
        findComment.setContent(updateParams.getContent());

        return commentRepository.save(findComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
