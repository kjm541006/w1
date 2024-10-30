package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.dto.CommentDTO;
import study.board.entity.Board;
import study.board.entity.Comment;
import study.board.entity.Member;
import study.board.service.BoardServiceImpl;
import study.board.service.CommentServiceImpl;

import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;
    private final BoardServiceImpl boardService;

    @PostMapping("/{boardId}/addComment")
    public String postComment(@PathVariable Long boardId, CommentDTO commentDTO, HttpSession session){
        Long loggedInUserId = ((Member) session.getAttribute("loggedInUser")).getId();
        commentService.createComment(commentDTO, loggedInUserId);

        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/{boardId}/comments/{commentId}/edit")
    public String editComment(Model model, @PathVariable Long boardId, @PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId).orElseThrow(()-> new RuntimeException("Not Found"));
        Board board = boardService.getBoardById(boardId).orElseThrow(() -> new RuntimeException("Not Found"));

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());

        model.addAttribute("board", board);
        model.addAttribute("comment", commentDTO);
        return "comment/edit-comment"; // 댓글 수정 페이지로 이동
    }

    @PostMapping("/{boardId}/comments/{commentId}/update")
    public String updateComment(@PathVariable Long boardId, @PathVariable Long commentId, CommentDTO commentDTO){
        commentService.updateComment(commentId, commentDTO);

        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{boardId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId){
        commentService.deleteComment(commentId);

        return "redirect:/boards/" + boardId;
    }

}
