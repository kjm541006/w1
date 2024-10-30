package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.dto.CommentDTO;
import study.board.entity.Member;
import study.board.service.CommentServiceImpl;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/{boardId}/addComment")
    public String postComment(@PathVariable Long boardId, CommentDTO commentDTO, HttpSession session){
        Long loggedInUserId = ((Member) session.getAttribute("loggedInUser")).getId();
        commentService.createComment(commentDTO, loggedInUserId);

        return "redirect:/boards/" + boardId;
    }

}
