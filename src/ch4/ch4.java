package ch4;

/*
 * ch4. 스트림 소개
 *
 * 4.1 스트림이란 무엇인가?
 * 데이터 컬렉션 반복을 투명하게 병렬로 처리하는 기능
 *
 * 스트림을 사용하면
 * - 선언형으로 코드를 구현할 수 있음
 *  제어 블록 없이 동작의 수행 지정할 수
 * - 여러 빌딩 블록 연산을 연결해 복잡한 데이터 처리 파이프라인을 만들 수 있음
 *  가독성 + 명확성 유지
 * 
 * 연산은 고수준 빌딩 블록으로 이루어져 있음
 * 특정 스레딩 모델에 제한되지 않고 자유롭게 어떤 상황에서든 사용 가능
 * => 스레드, 락 걱정할 필요가 없음
 *
 * 스트림 API 특징
 * - 선언형 : 더 간결하고 가독성이 좋아짐
 * - 조립할 수 있음 : 유연성이 좋아짐
 * - 병렬화 : 성능이 좋아짐
 * 
 * 4.2 스트림 시작하기
 * 스트림 : 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
 * - 연속된 요소
 *  특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스 제공
 *  컬렉션: 시간, 공간 복잡성과 관련된 요소 저장 및 접근 연산
 *  스트림: 표현 계산식
 * - 소스
 *  데이터 제공 소스(컬렉션 등)로부터 데이터 소비
 * - 데이터 처리 연산
 *  함수형 프로그래밍 언어에서 일반적으로 지원하는 연산, db와 비슷한 연산 지원
 *
 * 스트림의 주요 특징
 * - 파이프라이닝
 *  스트림 연산끼리 연결해 파이프라인을 구성할 수 있도록 스트림 자신 반환
 *  laziness, short-circuiting 등 최적화
 * - 내부 반복
 * 
 * 4.3 스트림과 컬렉션
 * 둘 다 연속된 요소 형식의 값을 저장하는 자료구조 인터페이스 제공
 *
 *      스트림                     컬렉션
 * 인터넷 스트리밍                 DVD
 * 요청할 때만 요소 계산     모든 값을 메모리에 저장
 * 게으른 생성               적극적 생성
 *
 * 4.3.1 딱 한 번만 탐색할 수 있다
 * 탐색된 스트림 요소는 소비됨
 * 다시 탐색항려면 반복 사용이 가능한 데이터 소스여야 함
 *
 * 4.3.2 외부 반복과 내부 반복
 * 컬렉션은 사용자가 직접 요소를 반복 : 외부 반복
 * 스트림은 작업 수행만 지정하면 알아서 처리 : 내부 반복
 *
 * for (Dish dish: menu) {
 *  names.add(dish.getName());
 * }
 *
 * List<String> names = menu.stream()
 *               .map(Dish::getName).collect(toList());
 *
 * 내부반복이 좋은 이유
 * - 병렬 처리
 * - 최적화된 순서로 처리
 * 
 * foreach는 스스로 관리해야 함
 *
 * 4.4 스트림 연산
 * Stream 인터페이스의 연산 두 그룹
 * - filter, map, limit 등 파이프라인 형성 : 중간 연산
 * - collect 등 파이프라인 실행하고 닫음     : 최종 연산
 *
 * 4.4.1 중간 연산
 * 중간 연산은 다른 스트림 반환
 * -> 중간 연산 체인 질의
 *
 * 단말 연산을 스트림 파이프라인에 실행하기 전까지 아무 연산도 수행하지 않음 : 게으름
 * 중간 연산을 합쳐 최종 연산으로 한번에 처리
 *
 * 쇼트서킷 : 전체 값 중 필요한 값만 계산
 * 루프 퓨점 : 다른 연산이 하나의 과정으로 병합
 *
 * 4.4.2 최종 연산
 * List, Integer, void 등 스트림 이외의 결과를 반환
 *
 * 4.4.3 스트림 이용하기
 * 스트림 이용 과정
 * - 질의를 수행할 데이터 소스
 * - 스트림 파이프라인을 구성할 중간 연산 연결
 * - 스트림 파이프라인을 실행하고 결과를 만들 최종 연산
 *
 * 중간연산
 * filter, map, limit, sorted, distinct 등
 * 
 * 최종연산
 * forEach, count, collect 등
 * 
 * 4.5 로드맵
 * 사례, 패턴
 * 
 * 4.6 마치며
 * - 스트림은 소스에서 추출된 연속 요소, 데이터 처리 연산 지원
 * - 스트림은 내부 반복 지원, 반복 추상화
 * - 중간 연산은 다른 연산과 연결
 * - 최종 연산은 스트림 파이프라인을 처리해 스트림이 아닌 결과 반환
 * - 스트림 요소는 게으르게 계산
 *
 */

public class ch4 {
}
