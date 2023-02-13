package com.hello.spring.controller;

import com.hello.spring.dto.ReplyRequestDto;
import com.hello.spring.dto.ReplyResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/reply/{id}")
    public ReplyResponseDto addReply(@PathVariable Long id, @RequestBody ReplyRequestDto dto, HttpServletRequest request) {
        return replyService.addReply(id, dto, request);
    }

    @PutMapping("api/reply/{id}")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto dto, HttpServletRequest request) {
        return replyService.update(id, dto, request);
    }

    @DeleteMapping("api/reply/{id}")
    public StatusResponseDto deleteReply(@PathVariable Long id, HttpServletRequest request) {
        return replyService.delete(id, request);
    }
}
