# KBank Manager

이 프로젝트는 Spring Boot와 Maven을 사용하여 Anylink 4 솔루션에서 제공하지 않는 WEB 기반의 UI 관리 기능을 보충하기 위해 작성한 FEP 운영자용 솔루션 관리 어플리케이션입니다. FEP(Front-End Processor) 시스템의 회선 정보, 통계, 로그, 시뮬레이션 등을 웹 인터페이스를 통해 관리할 수 있습니다.

## 주요 기능

- **회선 관리**: FEP 회선 정보 조회 및 관리 (line.jsp, line2.jsp)
- **통계 및 그래프**: 시스템 통계, 간트 차트, 볼륨 그래프 등 시각화 (graph/, statistic/)
- **로그 조회**: 온라인 및 배치 로그 조회 (log/)
- **시뮬레이션**: FEP 시뮬레이션 기능 (sim/)
- **API 관리**: 외부 API 통신 관리 (api/)
- **테스트 도구**: 인바운드/아웃바운드 테스트, 텔넷 등 (test/)
- **관리 기능**: 사용자 인증, 설정 관리 등 (auth/, manager/)

## 요구 사항

- Java 17 이상
- Maven 3.6 이상
- MySQL 또는 기타 지원 데이터베이스 (application.yml에서 설정)

## 설치 및 설정

1. **저장소 클론**:
   ```bash
   git clone <repository-url>
   cd kbank-manager
   ```

2. **의존성 설치**:
   ```bash
   mvn clean install
   ```

3. **데이터베이스 설정**:
   `src/main/resources/application.yml` 파일에서 데이터베이스 연결 정보를 설정하세요.

4. **애플리케이션 실행**:
   ```bash
   mvn spring-boot:run
   ```

   기본 포트는 8080입니다. 브라우저에서 `http://localhost:8080`으로 접근하세요.

## 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/kbk/fep/
│   │       ├── FepManagerApplication.java  # 메인 애플리케이션 클래스
│   │       ├── ServletInitializer.java     # 서블릿 초기화
│   │       ├── mngr/                       # 관리 기능
│   │       │   ├── ctl/                    # 컨트롤러
│   │       │   ├── dao/                    # 데이터 액세스 객체
│   │       │   └── svc/                    # 서비스
│   │       └── sim/                        # 시뮬레이션 기능
│   ├── resources/
│   │   ├── application.yml                 # 애플리케이션 설정
│   │   └── config/                         # 설정 파일들
│   └── webapp/
│       └── WEB-INF/
│           └── jsp/                        # JSP 뷰 파일들
│               ├── auth/                   # 인증 관련
│               ├── graph/                  # 그래프 및 차트
│               ├── line/                   # 회선 관리
│               ├── list/                   # 목록 조회
│               ├── test/                   # 테스트 도구
│               └── ...
└── test/
    └── java/                               # 테스트 코드
```

## 사용 방법

1. 애플리케이션 실행 후 로그인 페이지로 이동합니다.
2. 관리자 권한으로 로그인하세요.
3. 메뉴를 통해 원하는 기능을 선택하세요:
   - 회선대장: 회선 정보 조회 및 관리
   - 통계: 시스템 통계 및 그래프 확인
   - 로그조회: 온라인/배치 로그 확인
   - 시뮬레이션: FEP 시뮬레이션 실행

## API 엔드포인트

주요 REST API 엔드포인트:

- `GET /admin/line`: 회선 정보 조회
- `POST /admin/statistic`: 통계 데이터 조회
- `GET /log/list`: 로그 목록 조회
- `POST /sim/run`: 시뮬레이션 실행

자세한 API 문서는 Swagger UI (`/swagger-ui.html`)에서 확인하세요.

## 개발 및 기여

1. 이슈를 생성하거나 기존 이슈에 참여하세요.
2. 브랜치를 생성하고 변경 사항을 커밋하세요.
3. Pull Request를 생성하세요.

### 코드 스타일

- Java: Google Java Style Guide 준수
- JSP: 표준 HTML/CSS/JS 사용

### 테스트 실행

```bash
mvn test
```

## 라이선스

이 프로젝트는 [MIT License](LICENSE) 하에 배포됩니다.

## 연락처

문의 사항은 hc5642@me.com 또는 [GitHub Issues]를 통해 연락주세요.
