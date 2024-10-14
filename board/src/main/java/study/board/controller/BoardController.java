package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.board.dto.BoardDTO;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.service.BoardServiceImpl;
import study.board.service.MemberServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final MemberServiceImpl memberService;

    // 리스트 조회
    @GetMapping
    public String getBoardList(Model model, HttpSession session, Authentication authentication){
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        if (loggedInUser == null && authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            log.info("Authentication 에서 가져온 username: " + username);

            Optional<Member> loggedInMember = memberService.getMemberByUsername(username);
            if(loggedInMember.isPresent()){
                Long id = loggedInMember.get().getId();
                model.addAttribute("logIn", true);
                session.setAttribute("loggedInUser", loggedInMember.get());
                loggedInUser = (Member) session.getAttribute("loggedInUser");

                log.info("로그인한 사용자: {}", username);
                log.info("로그인한 사용자 id: {}", id);
            }else {
                log.warn("로그인한 사용자를 찾을 수 없습니다.");
            }
        }

        log.info("loggedInUser= " + loggedInUser);

        if(loggedInUser != null){
                Long id = loggedInUser.getId();
                model.addAttribute("logIn", true);
                session.setAttribute("loggedInUser", loggedInUser);

                log.info("로그인한 사용자 id: {}", id);
            }else {
                model.addAttribute("logIn", false);

                log.warn("로그인한 사용자를 찾을 수 없습니다.");
            }

        List<BoardDTO> boardDTOs = boardService.getAllBoards();
        model.addAttribute("boards", boardDTOs);

        return "board/boardList";
    }

    // 글 추가 form
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board/add")
    public String addForm(HttpSession session){

        return "board/register";
    }

    // 글 등록
    @PostMapping("/board/add")
    public String addBoard(BoardDTO boardDTO, RedirectAttributes redirectAttributes, HttpSession session){
        log.info("---------글 등록----------");
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            log.info("로그인한 사용자: {}", loggedInUser.getUsername());

            Board savedBoard = boardService.createBoard(boardDTO, loggedInUser);
            redirectAttributes.addAttribute("boardId", savedBoard.getId());
            redirectAttributes.addAttribute("status", true);
        } else {
            log.warn("로그인한 사용자가 없습니다.");
        }

        return "redirect:/boards/{boardId}";
    }

    @GetMapping("/{boardId}")
    public String getBoardById(Model model, @PathVariable Long boardId){
        log.info("boardId = " + boardId);
        Board board = boardService.getBoardById(boardId).orElseThrow(()-> new RuntimeException("Not Found"));
        log.info("Board: {}", board);
        log.info("Author: {}", board.getAuthor()); // author 객체 확인
        if (board.getAuthor() != null) {
            log.info("Author Username: {}", board.getAuthor().getUsername());
        }
        model.addAttribute("board", board);

        return "board/board";
    }

    @GetMapping("/{boardId}/edit")
    public String getEditBoard(Model model, @PathVariable Long boardId){
        Board board = boardService.getBoardById(boardId).orElseThrow(() -> new RuntimeException("Not Found"));
        model.addAttribute("board", board);

        return "board/edit";
    }
}
