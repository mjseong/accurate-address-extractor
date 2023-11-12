# Accurate road-address extractor
국내 도로명주소(구/로/길)을 조회하고 추출하기 위한 Application 입니다.
* 2개의 Mode 지원
    * csv mode - csv 파일경로 입력받아 대량의 주소를 추출하여 출력하는 기능
    * console mode - 문장 또는 주소를 입력받아 추출하는 기능
    
## Idea
1. 문장을 분할하고 도로명(구/로/길) 정규식 이용 전처리 통해 주소를 구분할 수 있게 분할합니다.
2. 분할된 결과는 후처리를 통해 주소후보(candidate address) 생성합니다.
3. 주소후보군들은 DB,OpenAPI와 같은 기능들을 통해 검증하고 검증된 주소는 출력합니다.


## Requirements
* Java 17 
* OS(Mac, Linux, Windows) environment

## Setup
```sh
./gradlew build
```

## Execution
```sh
java -jar build/libs/app-1.0-SNAPSHOT.jar
```

## Constraints

* 한국 도로명 주소에서 (도/시/군)(면/읍) 단위는 추출하지 않음.
  * 고도화 예정 기능 - 국내 주소(도/시/군)(면/읍)를 추출하는 기능 추가.
* 도로명 주소의 비패턴화로 인해 구현의 어려움, 따라서 일부 지명의 누락이 발생이 존재.
* 외부 통신 Lib 적용 제외
    * 추출된 주소의 주소 DB 검색 또는 OpenAPI 조회를 통한 검증 불가.
    * 한국내 자치구 지명만 메모리로 구축하여 검증기능 포함됨.
    * 서버 기능으로 포함하는게 맞다고 판단됨.
* common.utils.ExtractionAddress.class를 이용해 독립적인 API사용.
  * library를 만들어 다른 Application에 사용할 수 있게하면 더 좋을듯함.


## Feature

* 일반 문장에서 도로명 주소(구/로/길) 추출
* csv파일을 이용한 도로명 주소(구/로/길) 추출
* 구, 로, 길 주소에 따라 (구/길, 로/길, 구/로/길) 추출
* 비패턴형 주소 "서울 용산구 회나 무로 3 5 길"에서 주소 "용산구 회나무로 35길" 추출

## Preview

```sh
$ java -jar build/libs/accurate-address-extractor-1.0-SNAPSHOT.jar
프로그램 종류(csv: c 또는 console: 아무 키나 눌러주세요)를 입력을 하세요: c
프로그램을 종료하려면 'exit'을 입력하세요.
console mode - 주소를 입력 하세요: 종로구 종로 1번길
사용자 입력: 종로구 종로 1번길
출력 결과: 종로구 종로 1번길
console mode - 주소를 입력 하세요: 내가 사는집은 성남 분당구 대왕판교로
사용자 입력: 내가 사는집은 성남 분당구 대왕판교로
출력 결과: 분당구 대왕판교로
console mode - 주소를 입력 하세요: exit
프로그램을 종료합니다.
$
```
