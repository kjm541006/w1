package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.board.entity.Board;
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
    @GetMapping("/board/add")
    public String addForm(){

        return "board/add";
    }

    // 글 등록
    @PostMapping("/board/add")
    public String addBoard(Board board, RedirectAttributes redirectAttributes){
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
