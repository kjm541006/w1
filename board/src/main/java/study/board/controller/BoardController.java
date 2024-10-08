package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.service.BoardServiceImpl;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 리스트 조회
    @GetMapping
    public String getBoardList(Model model){
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);

        return "board/boardList";
    }

    // 글 추가 form
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board/add")
    public String addForm(){

        return "board/add";
    }

    // 글 등록
    @PostMapping("/board/add")
    public String addBoard(Board board, RedirectAttributes redirectAttributes, HttpSession session){
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            board.setAuthor(loggedInUser); // 현재 로그인한 사용자 설정
        }
        Board savedBoard = boardService.createBoard(board);
        redirectAttributes.addAttribute("boardId", savedBoard.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/boards/{boardId}";
    }

    @GetMapping("/{boardId}")
    public String getBoardById(Model model, @PathVariable Long boardId){
        Board board = boardService.getBoardById(boardId).orElseThrow(()-> new RuntimeException("Not Found"));
        model.addAttribute("board", board);

        return "board/board";
    }
}
