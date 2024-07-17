# 카카오 API를 활용하여 사용자 로그인 기능을 구현

## 설명
- 카카오 API를 활용하여 사용자 로그인 기능을 구현
- 로그인 요청 로직
  - 로그인 요청시 Redirect URL로 카카오 로그인 화면 이동
  - 로그인시 code 발급
  - code를 통해 사용자 토큰 발급
  - 토큰을 통해 사용자 정보 가져오기
  - 조회한 사용자 정보 세션에 저장
- 로그아웃
  - 세션에 저장된 정보 삭제

### 디렉터리 구조
```bash
├── controller
│   ├── KakaoLoginController.java     // 로그인 요청, 로그아웃 기능
│   └── ViewController.java           // View 담당
└── service
│   └── KakaoAuthService.java         // 내부 처리 로직
└── KakaoLoginApplication.java  
```

### 시나리오
1. 사용자가 카카오 로그인 버튼을 누른다
2. 화면이 카카오에서 제공하는 로그인 화면으로 이동한다.
3. 카카오 로그인 후 로그인 한 화면으로 이동한다.
4. 사용자가 원할시 로그아웃을 한다

