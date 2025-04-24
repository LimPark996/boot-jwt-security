package org.example.bootjwtsecurity.controller;

import org.apache.coyote.BadRequestException;
import org.example.bootjwtsecurity.model.dto.AuthTokenDTO;
import org.example.bootjwtsecurity.model.dto.UserRequestDTO;
import org.example.bootjwtsecurity.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequestDTO dto) throws BadRequestException {
        accountService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody UserRequestDTO dto) throws BadRequestException {
        AuthTokenDTO tokenDTO = accountService.login(dto);
        return ResponseEntity.ok(tokenDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

/**
1. @RequestBody UserRequestDTO dto의 역할과 필요성
@RequestBody 어노테이션은 HTTP 요청의 본문(body)을 Java 객체로 변환(역직렬화)하는 역할을 합니다.
왜 필요한지:

데이터 전달: 클라이언트가 사용자 정보(아이디, 비밀번호 등)를 JSON 형태로 서버에 전송할 때, 이를 Java 객체로 쉽게 변환하기 위함입니다.
객체 지향 프로그래밍: HTTP 요청 데이터를 객체로 추상화하여 더 객체 지향적인 코드를 작성할 수 있습니다.
유효성 검증: Spring의 검증 기능(Validation)을 통해 입력 데이터를 자동으로 검증할 수 있습니다.

이 경우 UserRequestDTO는 사용자의 등록 및 로그인 정보(아마도 username, password 등)를 담고 있는 클래스입니다.

2. ResponseEntity.status(HttpStatus.CREATED).build()의 역할
이 코드는 HTTP 응답을 생성하는 역할을 합니다.
역할과 의미:

ResponseEntity.status(HttpStatus.CREATED): HTTP 상태 코드 201(Created)를 설정합니다.
.build(): 본문(body) 없이 응답 객체를 생성합니다.

왜 이렇게 사용하는지:

적절한 상태 코드: 리소스 생성 성공(사용자 등록)을 나타내기 위해 201 Created를 사용합니다.
데이터 없음: 응답에 반환할 데이터가 없을 때 .build()를 사용합니다. 등록 성공 여부는 상태 코드로 충분히 알 수 있기 때문입니다.

로그인의 경우는 ResponseEntity.ok(tokenDTO)를 사용하여 200 OK와 함께 토큰 데이터를 응답합니다.
**/
