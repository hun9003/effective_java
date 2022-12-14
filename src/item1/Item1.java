package item1;

public class Item1 {
    /*
     * 생성자 대신 정적 팩터리 메서드를 고려하라.
     *
     * 클라이언트가 클래스의 인스턴스를 얻는 전통적인 수단은 public 생성자다.
     * 하지만 모든 프로그래머가 꼭 알아둬야 할 기법이 하나 더 있다.
     * 클래스는 생성자와 별도로 정적 팩터리 메서드(static factory method) 를 제공할 수 있다.
     * 그 클래스의 인스턴스를 반환하는 단순한 정적 메서드 말이다.
     * 다음 코드는 boolean 기본 타입의 박싱 클래스(boxed class)인 Boolean 에서 발취한 간단한 예다.
     * 이 메서드는 기본 타입인 boolean 값을 받아 Boolean 객체 참조로 변환해 준다
     */
    public static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }


    /*
     * 정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다.
     * 그렇다고 하더라도 정적 팩터리를 사용하는 게 유리한 경우가 더 많으므로
     * 무작정 public 생성자를 제공하던 습관이 있다면 고치자
     */

}
