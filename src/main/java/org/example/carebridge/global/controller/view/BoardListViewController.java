package org.example.carebridge.global.controller.view;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.dto.BoardFindResponseDto;
import org.example.carebridge.domain.board.dto.view.BoardListViewResponseDto;
import org.example.carebridge.domain.board.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardListViewController {

    private final BoardServiceImpl boardService;
    @GetMapping("/boardList")
    public String getBoardPage(Model model) {
        // 서비스에서 모든 게시글 가져오기
        List<BoardListViewResponseDto> boards = boardService.findAllBoardsUsedByView();

        // 모델에 데이터 추가
        model.addAttribute("boards", boards);

        return "boardList"; // board.html 렌더링
    }
}
