// 해당 클래스가 'org.example.bootjwtsecurity.service' 패키지에 속함을 명시
package org.example.bootjwtsecurity.service;

// Lombok 라이브러리의 어노테이션으로, final 필드를 매개변수로 하는 생성자를 자동 생성
import lombok.RequiredArgsConstructor;

// 클라이언트로부터 받은 데이터를 담는 DTO(Data Transfer Object) 클래스 import
import org.example.bootjwtsecurity.model.dto.TeamRequestDTO;

// 데이터베이스 테이블과 매핑되는 엔티티 클래스 import
import org.example.bootjwtsecurity.model.entity.Team;

// 데이터베이스 접근을 위한 리포지토리 인터페이스 import
import org.example.bootjwtsecurity.model.repository.TeamRepository;

// 이 클래스가 서비스 계층의 빈(Bean)임을 명시하는 Spring 어노테이션
import org.springframework.stereotype.Service;

// List 컬렉션을 사용하기 위한 import
import java.util.List;

// 이 클래스가 Spring의 서비스 계층 컴포넌트임을 명시
@Service

// final 필드에 대한 생성자를 자동 생성(Lombok)
@RequiredArgsConstructor
public class TeamService {
    // TeamRepository 의존성 주입(final로 선언하여 불변성 보장)
    // 데이터베이스 접근을 위한 리포지토리 인터페이스
    private final TeamRepository teamRepository;
    
    // 모든 팀 정보를 조회하는 메서드
    public List<Team> findAllTeams() {
        // 리포지토리의 findAll 메서드를 호출하여 모든 팀 엔티티를 가져옴
        return teamRepository.findAll();
    }
    
    // DTO 객체를 받아 새로운 팀을 저장하는 메서드
    public Team saveTeam(TeamRequestDTO dto) {
        // 새로운 Team 엔티티 객체 생성
        Team team = new Team();
        
        // DTO에서 받은 이름 값을 엔티티에 설정
        // DTO -> 엔티티로 데이터 이전 (첫 번째 변환 지점)
        team.setName(dto.name());
        
        // DTO에서 받은 위치 값을 엔티티에 설정
        team.setLocation(dto.location());
        
        // DTO에서 받은 감독 값을 엔티티에 설정
        team.setManager(dto.manager());
        
        // DTO에서 받은 주장 값을 엔티티에 설정
        team.setCaptain(dto.captain());
        
        // 설정된 엔티티를 리포지토리를 통해 데이터베이스에 저장하고 저장된 엔티티를 반환
        // 여기서 save 메서드는 영속화된 엔티티를 반환함
        return teamRepository.save(team);
    }
}

/**
변환 과정:
Controller → Service로 DTO가 전달됩니다.
Service에서 DTO → Entity 변환이 일어납니다(saveTeam 메서드).
Repository를 통해 Entity가 데이터베이스에 저장됩니다.
저장된 Entity가 다시 Controller로 반환되어 클라이언트에게 응답됩니다.
**/
