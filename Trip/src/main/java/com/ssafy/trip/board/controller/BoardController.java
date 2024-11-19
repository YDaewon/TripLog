package com.ssafy.trip.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Board Controller", description = "게시글 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@PostMapping("")
	@Operation(summary = "게시글 & 공지사항 등록", description = "게시글 & 공지사항 등록을 담당")
	public ResponseEntity<String> create(
			 @Parameter(description = "게시글 정보", required = true)
			 @RequestBody BoardDto boardDto) {
       try {
    	   System.out.println(boardDto.toString());
    	   if(boardDto.getNotice() != 0) boardService.createNotice(boardDto);
    	   else boardService.createBoard(boardDto);
           return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 성공");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 문제 발생");
       }
   }

	@GetMapping("")
	@Operation(summary = "게시글 목록", description = "게시글 목록을 불러옴")
    public ResponseEntity<?> boardall() {
        try {
            List<BoardDto> lists = boardService.listAll();
            if (lists != null) {
                return ResponseEntity.ok(lists); // 로그인 성공 시 사용자 정보 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리스트가 비어있어요");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }
	
	@GetMapping("notice")
	@Operation(summary = "공지사항 목록", description = "공지사항 목록을 불러옴")
    public ResponseEntity<?> noticeall() {
        try {
            List<BoardDto> lists = boardService.listNotice();
            if (lists != null) {
                return ResponseEntity.ok(lists); // 공지사항 목록을 불러옴
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리스트가 비어있어요");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }
	
	@GetMapping("{articleNo}")
	@Operation(summary = "게시글 정보", description = "articleNo에 해당하는 게시글 정보를 반환")
    public ResponseEntity<?> getinfo(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo) {
        try {
            BoardDto boardDto = boardService.getBoard(articleNo);
            if (boardDto != null) {
                return ResponseEntity.ok(boardDto); // 로그인 성공 시 사용자 정보 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 개시글 정보가 없음");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }
	
	@PutMapping("")
    @Operation(summary = "게시글 수정", description = "게시글 정보를 수정하는 URL")
    public ResponseEntity<String> modify(@RequestBody BoardDto boardDto) {
        try {
            boardService.modifyBoard(boardDto);
            return ResponseEntity.ok("게시글 수정 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정 중 문제 발생");
        }
    }
	
	@DeleteMapping("{articleNo}")
	@Operation(summary = "게시글 삭제", description = "articleNo에 해당하는 게시글 정보를 삭제")
    public ResponseEntity<String> delete(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo) {
        try {
            boardService.deleteBoard(articleNo);
            return ResponseEntity.ok("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 문제 발생");
        }
    }
}
