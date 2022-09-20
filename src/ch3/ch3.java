package ch3;

/*
 * ch3. 람다 표현식
 * 람다 표현식 만드는 법, 사용법, 코드 간결화
 *
 * 3.1 람다란 무엇인가?
 * 익명 함수의 단순화
 * 
 * 람다의 특징
 * - 익명 : 이름이 없음
 * - 함수 : (메소드처럼) 클래스에 종속되지 않음
 * - 전달 : 파라미터로 전달, 변수로 저장 가능
 * - 간결성 : 자질구레한 코드를 쳐냄
 *
 * 람다 표현식 구성요소
 * (람다 파라미터) -> 람다 바디
 * - 파라미터 리스트
 * - 화살표
 * - 람다 바디
 *
 * 3.2 어디에, 어떻게 람다를 사용할까?
 * 함수형 인터페이스 문맥에서 사용 가능
 * 그렇다면 함수형 인터페이스란?
 * 
 * 3.2.1 함수형 인터페이스
 * 정확히 하나의 추상 메소드를 지정하는 인터페이스
 * Comparator, Runnable 등
 *
 * public interface Comparator<T> {
 *  int compare(T o1, T o2);
 * }
 *
 * 함수형 인터페이스를 사용하면
 * 전체 표현식을 함수형 인터페이스의 인스턴스로 취급 가능
 * (람다식을 인터페이스 인스턴스로)
 *
 * 3.2.2 함수 디스크립터
 * 함수형 인터페이스의 추상 메소드 시그니쳐 == 람다 표현식 시그니쳐
 * 람다 표현식 시그니쳐 서술 메소드 : 함수 디스크립터
 * ex) () -> void, (int, int) -> int
 *
 * 함수형 인터페이스를 인수로 받는 메소드만 람다를 사용할 수 있는 이유
 * 원래 함수 형식(클래스-메소드 X)을 고려했으나 기존 방식 유지를 선택
 * 함수형 인터페이스 with 하나의 추상 메소드 <-> 람다식
 *
 * @FunctionalInterface
 * 함수형 인터페이스임을 가리키는 어노테이션
 * 어노테이션이 붙은 인터페이스가 함수형 인터페이스가 아니라면 컴파일 에러 발생
 *
 * 3.3 람다 활용 : 실행 어라운드 패턴
 * 순환 패턴 : 자원 열고, 처리한 다음, 자원 닫는 순서로 구성
 * 보통 설정(자원 열기), 정리(자원 닫기) 과정은 비슷
 * => 실제 자원을 처리하는 코드를 설정, 정리 두 과정이 둘러싸는 형태
 *    : 실행 어라운드 패턴
 * 
 * 초기화/준비 코드
 * 작업 A | 작업 B
 * 정리/마무리 코드
 *
 * 3.3.1 1단계 : 동작 파라미터화를 기억하라
 * 한 번에 한 줄만 읽는 코드를 다른 동작을 하도록 바꾸려면?
 * 기존 설정, 정리 재사용
 * 다른 동작을 수행할 수 있도록 메소드에 동작 전달
 * String result = processFIle((BufferedReader br) ->
 *                              br.readLine() + br.readLine());
 *
 * 3.3.2 2단계 : 함수형 인터페이스를 이용해서 동작 전달
 * BufferedReader -> String과 IOException을 던지는 시그니쳐와
 * 일치하는 함수형 인터페이스 만들기
 * @FunctionalInterface
 *
 * public interface BufferedReaderProcessor {
 *  String process(BufferedReader b) throws IOException;
 * }
 *
 * public String processFile(BufferedProcessor p) throws IOException {
 *  ...
 * }
 *
 * 3.3.3 3단계 : 동작 실행
 * processFile 바디 내에서 BufferedReaderProcessor.process 호출
 *
 * public String processFile(BufferedReaderProcessor p) throws IOException {
 *  try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
 *   return p.process(br);
 *  }
 * }
 *
 * 3.3.4 4단계 : 람다 전달
 * 한 행 처리 코드
 * String oneLine = processFile((BufferedReader br) -> br.readLine());
 * 두 행 처리 코드
 * String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
 *
 * 3.4 함수형 인터페이스 사용
 * 함수형 인터페이스 추상 메소드 시그니처 : 함수 디스크립터
 * 자바의 함수형 인터페이스 Comparable, Runnable, Callable 등
 *
 * 3.4.1 Predicate
 * java.util.function.Predicate<T>
 * test 추상 메소드 정의
 * (T) -> boolean
 *
 * List<String> nonEmpty = filter(listOfStrings, (String s) -> !s.isEmpty());
 *
 * 3.4.2 Consumer
 * java.util.function.Consumer<T>
 * accept 추상 메소드 정의
 * T 객체를 받아 어떤 동작을 수행하고 싶을 때 사용
 * (T) -> void
 *
 * 3.4.3 Function
 * java.util.function.Function<T, R>
 * apply 추상 메소드 정의
 * 입력을 출력으로 매핑하는 람다 정의할 때 사ㅛㅇㅇ
 * (T) -> R
 *
 * List<Integer> l = map(
 *      Arrays.asList("lambdas", "in", "action"),
 *      (String s) -> s.length());
 *
 * # 기본형 특화
 * 제네릭은 참조형만 사용 가능
 * -> 기본형 타입은 박싱, 언박싱 필요 (+ 오토박싱)
 * but 변환 비용 소모
 *
 * 기본형을 입출력으로 사용하는 상황에서 오토박싱을 피할 수 있는 함수형 인터페이스
 * 특정 형식을 입력으로 받는 함수형 인터페이스 이름 앞에 Double, Int, Long 등 형식명
 *
 * 예외는 새 함수형 인터페이스 정의 or try-catch로 처리
 *
 * 3.5 형식 검사, 형식 추론, 제약
 * 람다를 이해하려면 실제 형식을 파악해야 함
 *
 * 3.5.1 형식 검사
 * 어떤 콘텍스트에서 기대되는 람다 표현식의 형식 : 대상 형식(target type)
 * 1. 람다가 사용된 콘텍스트 무엇? 메소드 정의 확인
 * 2. 대상 형식 확인
 * 3. 추상 메소드 확인
 * 4. 디스크립터 확인
 * 5. 람다 시그니처와 디스크립터 비교, 타입 검사
 *
 * 3.5.2 같은 람다, 다른 함수형 인터페이스
 * 대상 형식 특징 때문에 같은 람다 표현식이라도 다른 함수형 인터페이스로ㅎ 사용될 수 있음
 * ex) Callable, PrivilegedAction은 둘 다 () -> T
 *
 * 특별한 void 호환 규칙
 * 람다 바디에 일반 표현식이 있으면 void 반환 디스크립터와 호환됨
 * ex) Consumer는 T -> void 지만 T -> boolean 도 유효함
 *
 * 같은 디스크립터를 가진 함수형 인터페이스를 가지는 메소드를 오버로딩할 때
 * execute((Action) () -> {}); 으로 명시 가능
 *
 * 3.5.3 형식 추론
 * 자바 컴파일러는 람다 표현식이 사용된 콘텍스트를 이용해서 함수형 인터페이스 추론
 * 대상 형식 > 함수 디스크립터 > 시그니처 추론
 * => 컴파일러는 람다 표현식 파라미터 형식에 접근할 수 있어 생략 가능
 * ex) filter(inventory, apple -> GREEN.equals(apple.getColor()));
 *
 * 3.5.4 지역 변수 사용
 * 람다식은 자유 변수(람다식 외부 변수) 활용 가능 : 람다 캡처링
 * 람다식은 한 번만 할당할 수 있는 지역 변수(값 변경 x)를 캡쳐 가능
 *
 * # 지역 변수의 제약
 * 인스턴스 변수 : 힙에 저장
 * 지역 변수 : 스택에 저장
 * 람다식에서는 자유 지역 변수의 복사본을 제공
 * => 복사본 값의 변화 허용 x, 한 번만 할당해야 함
 * 
 * 3.6 메소드 참조
 * inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
 * inventory.sort(comparing(Apple::getWeight));
 *
 * 3.6.1 요약
 * 메소드 참조 == 람다의 축약형
 * -> 가독성 높임
 * Apple::getWeight == (Apple a) -> a.getWeight()
 *
 * # 메소드 참조 만드는 방법
 * 메소드 참조 유형 세 가지
 * 1. 정적 메소드 참조
 * 2. 다양한 형식의 인스턴스 메소드 참조
 * 3. 기존 객체 인스턴스 메소드 참조
 *
 * 생성자, 배열 생성자, super 호출 등에 사용 가능한 특별 형식의 메소드 참조
 *                 람다                               메소드 참조
 * (args) -> ClassName.staticMethod(args)       ClassName::staticMethod
 * (arg0, rest) -> arg0.instanceMethod(rest)    ClassName::instanceMethod
 * (args) -> expr.instanceMethod(args)          expr::instanceMethod
 *
 * 3.6.2 생성자 참조
 * ClassName::new
 *
 * Supplier<Apple> c1 = Apple::new;
 * Apple a1 = c1.get();
 *
 * Function<Integer, Apple> c2 = Apple::new;
 * Apple a2 = c2.apply(110);
 *
 * List<Apple> apples = map(weights, Apple::new);
 *
 * BiFunction<Color, Integer, Apple> c3 = Apple::new;
 * Apple a3 = c3.apply(GREEN, 110);
 *
 * 맵을 통해 생성자 접근 가능
 * map.put("apple", Apple::new);
 *
 * 3.7 람다, 메소드 참조 활용하기
 *
 * 3.7.2 1단계 : 코드 전달
 * public class AppleComparator implements Comparator<Apple> {
 *  public int compare(Apple a1, Apple a2) {
 *   return a1.getWeight().compareTo(a2.getWeight());
 *  }
 * }
 * inventory.sort(new AppleComparator));
 *
 * 3.7.2 2단계 : 익명 클래스 사용
 * inventory.sort(new Comparator<Apple>() {
 *  public int compare(Apple a1, Apple a2) {
 *   return a1.getWeight().compareTo(a2.getWeight());
 *  }
 * });
 * 
 * 3.7.3 3단계 : 람다 표현식 사용
 * inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
 *
 * inventory.sort(comparing(apple -> apple.getWeight()));
 *
 * 3.7.4 4단계 : 메소드 참조 사용
 * inventory.sort(comparing(Apple::getWeight));
 *
 * 3.8 람다 표현식을 조합할 수 있는 유용한 메소드
 * 간단한 람다 표현식을 조합해 복잡한 람다 표현식 만들기
 * with 디폴트 메소드
 *
 * 3.8.1 Comparator 조합
 * Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
 *
 * 역정렬
 * inventory.sort(comparing(Apple::getWeight).reversed());
 *
 * Comparator 연결
 * inventory.sort(comparing(Apple::getWeight)
 *              .reversed() // 역정렬
 *              .thenComparing(Apple::getCountry)); // 국가순 정렬
 *
 * 3.8.2 Predicate 조합
 * Predicate 인터페이스는 negate, and, or 세 디폴트 메소드 제공
 * 
 * negate : 특정 프레디케이트 반전에 사용
 * Predicate<Apple> notRedApple = redApple.negate();
 * 
 * and : 두 람다 조합
 * Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
 *
 * or : 두 람다 조합
 * Predicate<Apple> redAndHeavyAppleOrGreen =
 *      redApple.and(apple -> apple.getWeight() > 150)
 *              .or(apple -> GREEN.equals(a.getColor()));
 *
 * 3.8.3 Function 조합
 * Function 인터페이스는 andThen, compose 두 디폴트 메소드 제공
 *
 * andThen : 주어진 함수를 적용한 결과를 다른 함수 입력으로 전달하는 함수 반환
 * Function<Integer, Integer> f = x -> x + 1;
 * Function<Integer, Integer> g = x -> x * 2;
 * Function<Integer, Integer> h = f.andThen(g); g(f)
 * int result = h.apply(1); // (x+1)*2 = 4 반환
 *
 * compose : 주어진 함수를 먼저 실행한 결과를 외부 함수 인수로 제공
 * Function<Integer, Integer> f = x -> x + 1;
 * Function<Integer, Integer> g = x -> x * 2;
 * Function<Integer, Integer> h = f.compose(g); // f(g)
 * int result = h.apply(1); // (x*2)+1 = 3 반환
 *
 * 여러 유틸리티 메소드를 조합해서 다양한 변환 파이프라인 생성 가능
 * Function<String, String> addHeader = Letter::addHeader;
 * Function<String, String> transformationPipeline =
 *      addHeader.andThen(Letter::checkSpelling)
 *               .andThen(Letter::addFooter);
 *
 * 3.9 비슷한 수학적 개념
 * 3.9.1 적분
 * f = x -> x+10
 * integrate(f, 3, 7)
 *
 * 3.9.2 자바 8 람다로 연결
 * integrate((double x) -> f(x), 3, 7))
 * integrate(c::f, 3, 7)
 *
 * public double integrate(DoubleFunction<Double> f, double a, double b) {
 *  return (f.apply(a) + f.apply(b)) * (b - a) / 2.0
 * }
 * public double integrate(DoubleUnaryOperator f, double a, double b) {
 *  return (f.applyAsDouble(a) + f.applyAsDouble(b)) * (b - a) / 2.0
 * }
 *
 * 3.10 마치며
 * - 람다 표현식은 익명 함수의 일종
 * - 람다 표현식으로 간결한 코드 구현
 * - 함수형 인터페이스는 하나의 추상 메소드만을 정의하는 인터페이스
 * - 함수형 인터페이스를 기대하는 위치에서만 람다 표현식 사용 가능
 * - 람다 표현식 전체가 함수형 인터페이스의 인스턴스 취급
 * - java.util.function이 제공하는 함수형 인터페이스
 *   Predicate<T>, Function<T, R>, Supplier<T>, Consumer<T>, BinaryOperator<T>
 * - 박싱 동작을 피할 수 있는 기본형 특화 인터페이스
 * - 실행 어라운드 패턴 (with 람다) 를 활용하면 유연성 + 재사용성
 * - 람다식 기대 형식 : 대상 형식, target type
 * - 메소드 참조를 사용하면 구현을 재사용, 직접 전달 가능
 * - 람다를 조합할 수 있는 다양한 디폴트 메소드 제공
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ch3 {
    String oneLine = processFile((BufferedReader br) -> br.readLine());
    String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());

    public ch3() throws IOException {
    }

    public String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }
}
