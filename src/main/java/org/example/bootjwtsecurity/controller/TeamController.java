// org.example.bootjwtsecurity.controller 패키지에 속함
package org.example.bootjwtsecurity.controller;

// Swagger 문서화를 위한 OpenAPI 어노테이션들
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

// Lombok 라이브러리의 어노테이션 - 생성자 자동 생성 기능 제공
import lombok.RequiredArgsConstructor;

// DTO(Data Transfer Object) 및 엔티티 클래스 가져오기
import org.example.bootjwtsecurity.model.dto.TeamRequestDTO;
import org.example.bootjwtsecurity.model.entity.Team;

// 비즈니스 로직을 처리하는 서비스 클래스 가져오기
import org.example.bootjwtsecurity.service.TeamService;

// Spring의 HTTP 응답 관련 클래스
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Spring REST 컨트롤러 어노테이션들
import org.springframework.web.bind.annotation.*;

// Java 컬렉션 프레임워크
import java.util.List;

// REST API 컨트롤러임을 선언 - JSON 형태로 응답 반환
@RestController

// 이 컨트롤러의 기본 URL 경로 지정
@RequestMapping("/api/baseball/teams")

// final 필드에 대한 생성자 자동 생성 (Lombok)
@RequiredArgsConstructor

// Swagger에서 사용할 보안 스키마 정의
@SecurityScheme(
        name = "bearerAuth",      // 보안 스키마 이름
        type = SecuritySchemeType.HTTP,  // HTTP 타입의 보안 방식
        scheme = "bearer",        // Bearer 스키마 사용
        bearerFormat = "JWT",     // JWT 포맷 사용
        description = "JWT Bearer token"  // 설명
)
public class TeamController {
    // 팀 관련 비즈니스 로직을 처리하는 서비스 주입 (final로 불변성 보장)
    private final TeamService teamService;
    
    // HTTP GET 요청 처리 메서드 (기본 경로에 매핑)
    @GetMapping
    
    // Swagger 문서에 이 API에 대한 보안 요구사항 명시
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<Team>> findAllTeams() {
        // 서비스를 통해 모든 팀 목록 조회
        List<Team> teamList = teamService.findAllTeams();
        
        // 팀 목록과 함께 200 OK 상태 코드 반환
        return ResponseEntity.ok(teamList); // HTTP 200 OK 상태 코드와 함께 팀 목록을 응답 본문으로 반환합니다. 
    }
    
    // HTTP POST 요청 처리 메서드 (기본 경로에 매핑)
    @PostMapping
    
    // Swagger 문서에 이 API에 대한 보안 요구사항 명시
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Team> createTeam(@RequestBody TeamRequestDTO dto) {
        // 요청 본문의 DTO를 사용하여 새 팀 생성
        Team team = teamService.saveTeam(dto);
        
        // 생성된 팀과 함께 201 Created 상태 코드 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }
}

/**
ResponseEntity는 Spring Framework에서 제공하는 클래스로, HTTP 응답을 표현하는데 사용됩니다. 
이 클래스는 HTTP 응답의 세 가지 핵심 요소를 포함합니다:

- 상태 코드(Status Code) - HTTP 응답 상태 코드(예: 200 OK, 404 Not Found, 500 Internal Server Error 등)
- 헤더(Headers) - HTTP 응답 헤더 정보
- 본문(Body) - 응답 데이터 자체

@SecurityScheme과 @Operation의 관계
정의와 참조의 관계:
@SecurityScheme는 보안 방식을 정의합니다.
@Operation의 security 속성은 이미 정의된 보안 방식을 참조합니다.

범위:
@SecurityScheme는 클래스 레벨에 적용되어 전역적으로 보안 방식을 정의합니다.
@Operation의 security 속성은 메서드 레벨에서 특정 API에 보안 요구사항을 적용합니다.

@Operation(security = @SecurityRequirement(name = "bearerAuth"))가 메서드 위에 붙어 있으면, 
해당 구문은 Swagger 문서에서 "이 특정 API 엔드포인트는 'bearerAuth'라는 이름의 보안 방식이 필요하다"고 표시합니다.

예를 들어:
@GetMapping
@Operation(security = @SecurityRequirement(name = "bearerAuth"))
public ResponseEntity<List<Team>> findAllTeams() { ... }
이렇게 하면 Swagger UI에서 /api/baseball/teams GET 엔드포인트에 대해 "이 API를 호출하려면 JWT Bearer 토큰이 필요합니다"라고 명시됩니다.
"메서드 레벨에서 적용"이라는 말은 클래스 전체가 아닌 특정 메서드(여기서는 findAllTeams())에만 이 보안 요구사항이 적용된다는 의미입니다. 
따라서 같은 컨트롤러의 다른 메서드에는 다른 보안 요구사항을 적용하거나 보안 요구사항이 없게 할 수도 있습니다.
**/
