package item3;

public class Item3 {
    /*
        private 생성자나 열거 타입으로 싱글턴임을 보증하라

        싱글턴(singleton)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.
        싱글턴의 전형적인 예로는 함수와 같은 무상태 객체나 설계상 유일해야 하는 시스템 컴포넌트를 들 수 있다.
        그런데 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.

        타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글턴이 아니라면 싱글턴 인스턴스를 가짜(mock) 구현으로
        대체할 수 없기 때문이다.

        싱글턴을 만드는 방식은 보통 둘 중 하나다. 두 방식 모두 생성자는 private으로 감춰두고,
        유일한 인스턴스에 접근할 수 있는 수단으로 public static 멤버를 하나 마련해둔다.
        우선 public static 멤버가 final 필드인 방식을 살펴보자.
     */

    // public static final 필드 방식의 싱글턴
    public static class Elvis {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {}

        public void leaveTheBuilding() {}

        /*
            private 생성자는 public static final 필드인 Elvis.INSTANCE를 초기화할 때 딱 한번만 호출된다.
            public이나 protected 생성자가 없으므로 Elvis 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임이 보장된다.

            장점 :
            해당 클래스가 싱글턴임이 API에 명백히 드러난다는 것, 간결함
         */
    }

    /*
        싱글턴을 만드는 두 번째 방법에서는 정적 팩터리 메서드를 public static 멤버로 제공한다.
     */

    // 정적 팩터리 방식의 싱글턴
    public static class Elvis2 {
        private static final Elvis2 INSTANCE = new Elvis2();
        private Elvis2() {}
        public static Elvis2 getInstance() { return INSTANCE; }

        public void leaveTheBuilding() {}

        /*
            Elvis2.getInstance는 항상 같은 객체의 참조를 반환하므로 제2의 Elvis2 인스턴스란 결코 만들어지지 않는다.

            장점 :
            API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다는 점
            유일한 인스턴스를 반환하던 팩터리 메서드가 (예컨대) 호출하는 스레드별로 다른 인스턴스를 넘겨주게 할 수 있음
            원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다는 점
            정적 팩터리의 메서드 참조를 공급자(supplier)로 사용할 수 있다는 점.
         */
    }

    /*
        둘 중 하나의 방식으로 만든 싱글턴 클래스를 직렬화하려면 단순히 Serializable을 구현한다고 선언하는 것만으로는 부족하다.
        모든 인스턴스 필드를 일시적(transient)이라고 선언하고 readResolve 메서드를 제공해야 한다.
     */
    public static class Elvis3 {
        public static final Elvis3 INSTANCE = new Elvis3();
        private Elvis3() {}

        public void leaveTheBuilding() {}

        // 싱글턴임을 보장해주는 readResolve 메서드
        private Object readResolve() {
            // '진짜' Elvis를 반환하고, 가짜 Elvis는 가비지 컬렉터에 맡긴다.
            return INSTANCE;
        }
        /*
            private 생성자는 public static final 필드인 Elvis.INSTANCE를 초기화할 때 딱 한번만 호출된다.
            public이나 protected 생성자가 없으므로 Elvis 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임이 보장된다.

            장점 :
            해당 클래스가 싱글턴임이 API에 명백히 드러난다는 것, 간결함
         */
    }

    /*
        싱글턴을 만드는 세 번째 방법은 원소가 하나인 열거 타입을 선언하는 것이다.
     */

    // 열거 타입 방식의 싱글턴 - 바람직한 방법
    public enum Elvis4 {
        INSTANCE;

        public void leaveTheBuilding() {}

        /*
            public 필드 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화할 수 있고,
            심지어 아주 복잡한 직렬화 상황이나 리플렉션 공격에도 제2의 인스턴스가 생기는 일을 완벽히 막아준다.
            조금 부자연스러워 보일 수는 있으나 대부분의 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.

            단, 만들려는 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다 (열거 타입이 다른 인터페이스를 구현하도록 선언할 수는 있다.)
         */
    }
}
