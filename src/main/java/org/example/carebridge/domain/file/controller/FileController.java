package org.example.carebridge.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.board.service.BoardService;
import org.example.carebridge.domain.file.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{boardId}/files")
public class FileController {

    private final FileService fileService;
    private final BoardService boardService;
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @PathVariable Long boardId) {

        Board board = boardService.getBoardEntityById(boardId);

        String fileUrl = fileService.uploadFile(file, board);

        return ResponseEntity.status(HttpStatus.CREATED).body(fileUrl);
    }
}
