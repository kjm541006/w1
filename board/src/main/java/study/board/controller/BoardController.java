package study.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
//            model.addAttribute("username", username);

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
        model.addAttribute("username", authentication.getName());

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
    public String getBoardById(Model model, @PathVariable Long boardId, HttpSession session){
        log.info("boardId = " + boardId);
        Board board = boardService.getBoardById(boardId).orElseThrow(()-> new RuntimeException("Not Found"));
        Long loggedInUserId = ((Member) session.getAttribute("loggedInUser")).getId();

        log.info("Board: {}", board);
        log.info("Author: {}", board.getAuthor()); // author 객체 확인
        if (board.getAuthor() != null) {
            log.info("Author Username: {}", board.getAuthor().getUsername());
        }

        model.addAttribute("board", board);
        model.addAttribute("loggedInUserId", loggedInUserId);

        return "board/board";
    }

    @GetMapping("/{boardId}/edit")
    public String getEditBoard(Model model, @PathVariable Long boardId, HttpSession session) {
        Board board = boardService.getBoardById(boardId).orElseThrow(() -> new RuntimeException("Not Found"));
        Long loggedInUserId = ((Member) session.getAttribute("loggedInUser")).getId();

        // 작성자 아닐 경우 접근 제어
        if(!board.getAuthor().getId().equals(loggedInUserId)){
            throw new AccessDeniedException("You do not have permission to edit this post.");
        }

        // Board 엔티티를 BoardDTO로 변환
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setAuthorName(board.getAuthor().getUsername()); // 작성자 이름 설정

        log.info("작성자: " + board.getAuthor().getId());

        // 댓글 리스트도 필요하다면 추가

        model.addAttribute("board", boardDTO);
        model.addAttribute("loggedInUserId", loggedInUserId);
        return "board/edit";
    }

    @PostMapping("/{boardId}/edit")
    public String editBoard(@ModelAttribute BoardDTO updateParams, @PathVariable Long boardId) {
        boardService.updateBoard(boardId, updateParams);
        return "redirect:/boards";
    }

    // 댓글 있을 경우 삭제 안되는 버그 수정해야 함(참조 있을 경우 삭제 x)
    @PostMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);

        return "redirect:/boards";
    }

}
