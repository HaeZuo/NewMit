# NewMit

> 식자재 관리의 새로운 방법

## 프로젝트

### 개발 목표

- 식자재의 소비기한, 수량 등을 웹과 앱으로 쉽게 관리할 수 있도록 HTTP 기반의 서비스 제공

- 소비기한 임박 식자재를 소비하도록 유도함으로써 효율적인 식자재 소비를 장려하는 기능 제공

- 소비기한 임박 식자재 알림을 제공함으로써 보유 식자재 상태를 편하게 확인하는 기능 제공

- 나만의 레시피를 작성하고, 그 내용을 다른 사용자와 공유할 수 있는 레시피 공유 플랫폼 제공

- 사용자가 선택한 레시피에서 부족한 식자재를 구매할 수 있도록 플랫폼 연동 기능 제공

- 보유 중인 식자재를 기반으로 만들 수 있는 레시피 추천 서비스 제공

### 개발 내용

- (1) UI/UX 디자인 설계
  * Material Design을 기반으로 전반적인 UI/UX 디자인 수행
  * 모바일 사용자에 제공을 위한 서비스 디자인 환경 구현
  * Figma를 통한 UI 가이드라인 규정을 만들어 관리 및 제작

- (2) 웹 서비스 구조 및 설계
  * UI/UX 디자인 설계 내용을 바탕으로 사용자 편의성 기능 구현
  * OAuth2를 이용한 소셜 앱 로그인 서비스로 사용자 용이성 확보
  * 식자재 등록/수정/삭제 기능을 통한 사용자 식자재 관리 기능 구현
  * 객체인식 서비스와의 연계를 통한 식자재 자동 등록 관리 기능 구현
  * 레시피 등록/수정/삭제 기능을 이용한 사용자 레시피 공유 서비스 기능 구현

- (3) 객체인식 설계
  * Object Detection 대상 요소 취합 및 구분
  * 대상 요소에 따른 관련 이미지 수집 프로그램 작성 및 수집
  * 대상 요소 식별을 위한 사전 라벨링 수행
  * TensorFlow 2.15.0.post1 버전 기반 SSD Mobilenet V2 모델 학습
  * TensorFlow Model Server 2.15.1 버전 기반 Serving 서비스 진행

### 워크플로우

![image](https://github.com/HaeZuo/NewMit/assets/66985977/97037fa8-861b-4ffe-87cf-496890c9e700)

### 프로젝트 기간

- 2024-03-02 ~ 2024-07-31

### 프로젝트 팀원

- 팀원 (3명) <br/>

|<img src='https://avatars.githubusercontent.com/u/23098327?v=4' height=80 width=80px></img>|<img src='https://avatars.githubusercontent.com/u/66985977?v=4' height=80 width=80px></img>|<img src='https://avatars.githubusercontent.com/u/71180175?v=4' height=80 width=80px></img>|
|:-:|:-:|:-:|
|[Chance](https://github.com/ahs0432)|[JaeDragon](https://github.com/iJaeDragon)|[Nyoung_Ni](https://github.com/yheun03)|
