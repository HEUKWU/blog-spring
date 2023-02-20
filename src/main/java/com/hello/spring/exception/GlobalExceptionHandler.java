package com.hello.spring.exception;

import com.hello.spring.dto.StatusResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<StatusResponseDto> notFoundMember() {
        return new ResponseEntity<>(new StatusResponseDto("회원을 찾을 수 없습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(DuplicatMemberException.class)
    public ResponseEntity<StatusResponseDto> duplicateMember() {
        return new ResponseEntity<>(new StatusResponseDto("중복된 username 입니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StatusResponseDto> invalidToken() {
        return new ResponseEntity<>(new StatusResponseDto("토큰이 유효하지 않습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<StatusResponseDto> permission() {
        return new ResponseEntity<>(new StatusResponseDto("작성자만 삭제/수정 할 수 있습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(NotFoundContentsException.class)
    public ResponseEntity<StatusResponseDto> notFoundContents() {
        return new ResponseEntity<>(new StatusResponseDto("해당 목록을 찾을 수 없습니다.", 400), HttpStatus.BAD_GATEWAY);
    }
}