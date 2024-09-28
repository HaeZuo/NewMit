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



## 서비스

### 로그인

- OAuth2를 기반으로한 카카오, 네이버, 구글 소셜 로그인 페이지 구현

![image](https://github.com/user-attachments/assets/ed8fcaa9-1ab0-4d1e-aa2b-052cba069fe3)

### 메인페이지

- 메인 페이지의 레시피 조회 화면과 마이페이지 화면

  * 우측 상단 검색 아이콘을 이용해 레시피의 요소를 이용하여 검색

![image](https://github.com/user-attachments/assets/dc5f4e72-b059-4cfe-9c65-23b24805648b)

### 식자재 등록

- 자동으로 등록하기 선택 시 이미지 촬영 또는 선택 후 이미지 분석 및 등록 페이지로 이동
- 수동으로 등록하기 선택 시 즉시 식자재 등록 페이지로 이동하여 식자재 등록 가능

![image](https://github.com/user-attachments/assets/f65d65ac-ffff-4256-b051-1b2a7bcdf7e9)

### 식자재 리스트, 상세 화면

- 필터를 기반으로 쉽게 식자재를 조회하고 우측 상단 검색 아이콘을 통해 식자재 검색

![image](https://github.com/user-attachments/assets/367e06e7-2a99-47cd-a75d-328776c97ac1)

### 레시피 확인

- 등록된 레시피와 작성자 그리고 그에 들어가는 재료와 조리도구 등을 확인 가능
- 만약 필요한 경우 재료나 조리도구를 구입할 수 있도록 오픈마켓으로 이동

![image](https://github.com/user-attachments/assets/22c313be-ba5c-489c-a79b-0996dd8ff7ce)

### 레시피 조리

- 레시피 작성자가 제공하는 이미지와 내용을 각 단계에 맞게 제공
- 레시피 조리 간 작성자에 의도에 맞는 조리 시간 제공을 위한 자체 타이머 기능 제공

![image](https://github.com/user-attachments/assets/5f5efecb-f2de-4ce7-98bc-76f59c8c6142)

![image](https://github.com/user-attachments/assets/f57ebf38-91cd-4497-a985-45c25496368a)

### 레시피 등록

- 레시피의 정보를 직접 입력하고 단계 별 작성자가 원하는 이미지와 내용 제공
- 레시피를 이용할 때 필요한 재료, 조리도구를 선택하여 등록할 수 있는 기능 제공

![image](https://github.com/user-attachments/assets/cd75b2fd-dade-4998-90eb-3eb623709e63)

![image](https://github.com/user-attachments/assets/a6c84cb0-e330-4831-88df-c35461380608)


## 객체인식

> 객체인식 모델 Serving (TensorFlow Model Server)

![image](https://github.com/user-attachments/assets/606e9fb9-17d2-49b9-aba5-f16ac7200575)

### 객체인식 간 사용 이미지

- 총 2,082개의 과일, 야채, 스낵의 박스, 봉투 이미지를 수집
- 이미지를 Pascal VOC 기반의 XML 파일 형식으로 라벨링 수행

 ![image](https://github.com/user-attachments/assets/4db4fa29-e0b1-400f-96a5-a5ac13a8f980)

### 객체인식 결과

- 객체인식 테스트 결과 (1)
  
 ![image](https://github.com/user-attachments/assets/bdde6eff-e95a-45f8-9e9a-1cbae84c130b)

- 객체인식 테스트 결과 (2)
  
 ![image](https://github.com/user-attachments/assets/b942e48e-6455-4f7d-b45f-635fc769acd0)
 
- 객체인식 테스트 결과 (3)
  
 ![image](https://github.com/user-attachments/assets/307335b8-794d-432f-8494-2c7426097d46)

### LLM 활용
- 화두가 되고 있는 대규모 언어 모델(LLM)을 이용한 레시피 추천 기능
  * 국내 위주 서비스를 위해 국내 기업에서 제공되는 생성형 AI 서비스 이용 NAVER 사에서 제작한 HyperClova X 서비스의 HCX-003 모델을 이용
  * 보유 중인 식자재와 관련된 레시피를 자동 산정하여 사용자에게 제공

- 작성된 프롬프트

  ```
    ## 소개
   요리 레시피를 알려주는 AI입니다.
   요구사항에 맞춰 적절한 레시피를 추천합니다.
   
   ## 요구사항
   ingredients에 명시된 재료가 최대한 포함되는 레시피를 추천해주세요.
   except_recipe에 명시된 레시피는 제외하고 비슷하거나 유사한 레시피도 제외합니다.
   
   ## 제공되는 형식
   재료와 추천 제외 요리는 아래와 같이 JSON 형식으로 제공됩니다.
   { ingredients: ["양파", "파", "삼겹살"], except_recipe: ["양파 조림"]}
   
   ## 제공하는 형식
   위와 같은 형식으로 제공받을 경우 아래와 같이 JSON 형태로 답안을 제공해줍니다.
   { recipe_name: "삼겹살 덮밥", recipe_ingredients: ["양파", "파", "삼겹살", "통깨", "쌀", "간장", "설탕", "맛술", "굴소스", "간장", "물엿", "다진마늘", "물", "생강가루", "후춧가루"], recipes: [ "양파는 얇게 채를 썬 후 찬물에 담갔다가 체에 밭쳐 물기를 빼고 쪽파는 송송 썰어주세요. 볼에 간장 소스 재료를 넣고 섞어주세요.", "달군 팬에 삼겹살을 올려 앞뒤로 노릇하게 굽고 한입 크기로 썰어주세요.", "팬에 삼겹살을 굽고 나온 기름을 닦아 낸 후 간장 소스를 넣고 중약불에서 윤기 나게 조려가며 익혀주세요.", "그릇에 따뜻한 밥을 담은 후 채를 썬 양파를 올리고 삼겹살을 돌려 담아주세요. 삼겹살에 팬에 남은 소스를 얹은 후 실파와 통깨를 뿌려 맛있게 즐겨주세요. (기호에 따라 가운데 달걀노른자와 연겨자를 올려 드시면 더욱 맛있게 드실 수 있답니다.)" ]}
  ```
  * 입/출력에 따른 결과
 
    * 입력
    
    ```

    {
     ingredients: ["스파게티면", "미트볼", "삼겹살", "양파"],
     except_recipe: []
    }

    ```
    * 출력
   
    ```
    {
      recipe_name: "미트볼 스파게티",
      recipe_ingredients: ["스파게티면", "돼지고기 다짐육", "소고기 다짐육", "양파", "빵가루", "우유", "달걀", "소금", "후춧가루", "올리브오일", "마늘", "토마토소스", "페페론치노", "모차렐라 치즈"],
      recipes: [
        "먼저 미트볼을 만들어보겠습니다. 양파 1/4개는 잘게 다지고, 빵가루 2큰술과 우유 3큰술, 달걀 1/2개, 소금 약간, 후춧가루 약간을 넣어 끈기가 생길 때까지 치대어 주세요. 그리고 4등분하여 둥글납작하게 빚어 줍니다.",
        "이제 면을 삶아볼까요? 끓는 물에 소금 1큰술을 넣고 스파게티면 200g을 8분간 삶아 건져내 주세요. 삶은 면은 올리브오일을 뿌려 버무려 놓습니다.",
        "팬에 올리브오일을 두르고 다진 마늘 1큰술을 볶아 향을 내다가 토마토소스 1컵 반(400ml)을 붓고 페페론치노 5개를 손으로 부숴 넣습니다. 끓어오르면 미트볼을 넣어 굴려가며 6분간 끓여 익힙니다.",
        "삶은 스파게티면을 넣고 버무리듯 볶다가 불을 끄고 모차렐라 치즈 1줌을 올려 뚜껑을 덮어 치즈가 녹을 때까지 둡니다. 파슬리 가루를 솔솔 뿌려 마무리 해주세요.“
      ]
     }
    ```

  * 서비스 반영 상태

  ![image](https://github.com/user-attachments/assets/0fa76662-eafe-4717-9e5e-1fa1727997c1)
