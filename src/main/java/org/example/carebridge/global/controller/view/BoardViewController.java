package org.example.carebridge.global.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.board.dto.view.BoardViewResponseDto;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable Long id, Model model) {
        try {
            BoardViewResponseDto board = boardService.findBoardByIdUsedByView(id);
            log.info("게시글 데이터: " + board); // 데이터 로그 확인
            model.addAttribute("board", board);
            return "board";
        } catch (Exception e) {
            log.error("게시글 조회 중 오류 발생: " + e.getMessage());
            model.addAttribute("error", "게시글을 찾을 수 없습니다.");
            return "errorPage";
        }
    }



    @GetMapping("/board/new")
    public String getCreateBoardPage(Model model) {
        model.addAttribute("board", new BoardViewResponseDto()); // 새로운 게시글을 위한 빈 객체 생성
        return "addBoard"; // addBoard.html 렌더링
    }
}
