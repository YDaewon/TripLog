package com.triplog.comment.controller;

import com.triplog.comment.model.commentDto;
import com.triplog.comment.service.commentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Tag(name = "댓글 API", description = "댓글 관련 CRUD API")
public class commentController {

    private final commentService commentService;
    
    public commentController(commentService commentService) {
		super();
		this.commentService = commentService;
	}

	@Operation(summary = "특정 게시글의 댓글 목록 조회", description = "특정 게시글(articleNo)의 모든 댓글을 가져옵니다.")
    @GetMapping("/{articleNo}")
    public ResponseEntity<List<commentDto>> getComments(@PathVariable int articleNo) {
        List<commentDto> comments = commentService.getComments(articleNo);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.")
    @PostMapping("")
    public ResponseEntity<Void> createComment(@RequestBody commentDto commentDto) {
    	System.out.println(commentDto.toString());
        commentService.createComment(commentDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.")
    @PutMapping("")
    public ResponseEntity<Void> modifyComment(
    		@RequestParam("commentNo") Long commentNo,
            @RequestParam("comment") String comment) {
    	System.out.println(commentNo + ", " + comment);
        commentService.modifyComment(commentNo, comment);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @PutMapping("/delete/{commentNo}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentNo) {
        commentService.deleteComment(commentNo);
        return ResponseEntity.ok().build();
    }
}
