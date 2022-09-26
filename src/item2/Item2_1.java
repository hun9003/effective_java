package item2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Item2_1 {
    /*
        빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다.
        각 계층의 클래스에 관련 빌더를 멤버로 정의하자.
        추상 클래스는 추상 빌더를, 구체 클래스는 구체 빌더를 갖게 한다.

     */
// 다음은 피자의 다양한 종류를 표현하는 계층 구조의 루트에 놓은 추상클래스다.
    public static abstract class Pizza {
        public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
        final Set<Topping> toppings;

        abstract static class Builder<T extends Builder<T>> {
            EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
            public T addTopping(Topping topping) {
                toppings.add(Objects.requireNonNull(topping));
                return self();
            }

            abstract Pizza build();

            // 하위클래스는 이 메서드를 재정의(overriding) 하여
            // "this" 를 반환하도록 해야 한다.
            protected abstract T self();
        }

        Pizza(Builder<?> builder) {
            toppings = builder.toppings.clone();
        }
    }
    /*
        Pizza.Builder 클래스는 재귀적 타입 한정을 이용하는 제네릭 타입이다.
        여기에 추상 메서드인 self를 더해 하위 클래스에서는 형변환하지 않고도 메서드 연쇄를 지원할 수 있다.
        self 타입이 없는 자바를 위한 이 우회 방법을 시뮬레이트한 셀프 타입(simulated self-type) 관용구라 한다.
     */

    /*
        여기 Pizza의 하위 클래스가 2개 있다. 하나는 일반적인 뉴욕 피자이고,
        다른 하나는 칼초네 (calzone) 피자다. 뉴욕 피자는 크기(size) 매개변수를 필수로 받고.
        칼초네 피자는 소스를 안에 넣을지 선택(sauceInside)하는 매개변수를 필수로 받는다.
     */

    // 뉴욕 피자
    public static class NyPizza extends Pizza {
        public enum Size { SMALL, MEDIUM, LARGE }
        private final Size size;

        public static class Builder extends Pizza.Builder<Builder> {
            private final Size size;

            public Builder(Size size) {
                this.size = Objects.requireNonNull(size);
            }

            @Override
            public NyPizza build() {
                return new NyPizza(this);
            }

            @Override
            protected Builder self() {return this;}
        }

        private NyPizza(Builder builder) {
            super(builder);
            size = builder.size;
        }
    }

    // 칼초네 피자
    public static class Calzone extends Pizza {
        private final boolean sauceInside;

        public static class Builder extends Pizza.Builder<Builder> {
            private boolean sauceInside = false; // 기본값

            public Builder sauceInside() {
                sauceInside = true;
                return this;
            }

            @Override
            public Calzone build() {
                return new Calzone(this);
            }

            @Override
            protected Builder self() {
                return this;
            }
        }
        private Calzone(Builder builder) {
            super(builder);
            sauceInside = builder.sauceInside;
        }
    }

    /*
        각 하위 클래스의 빌더가 정의한 build 메서드는 해당하는 구체 하위 클래스를 반환하도록 선언한다.
        NyPizza.Builder 는 NyPizza를 반환하고 Calzone.Builder는 Calzone를 반환한다는 뜻이다.
        하위 클래스의 메서드가 상위 클래스의 메서드가 정의한 반환 타입이 아닌,
        그 하위 타입을 반환하는 기능을 공변 반환 타이핑 (covariant return typing) 이라 한다.
        이 기능을 이용하면 클라이언트가 형변환에 신경 쓰지 않고도 빌더를 사용할 수 있다.
     */

    /*
        이러한 '계층적 빌더' 를 사용하는 클라이언트의 코드도 앞선 영양정보 빌더를 사용하는 코드와 다르지 않다.
     */
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.SAUSAGE).addTopping(Pizza.Topping.ONION).build();

        Calzone calzone = new Calzone.Builder()
                .addTopping(Pizza.Topping.HAM).sauceInside().build();

        /*
            생성자로는 누릴 수 없는 사소한 이점으로, 빌더를 이용하면 가변 인수(varargs) 매개변수를 여러 개 사용할 수 있다.
            각각을 적절한 메서드로 나워 선언하면 된다.
            아니면 메서드를 여러 번 호출하도록 하고 각 호출 때 넘겨진 매개변수들을 하나의 필드로 모을 수도 있다.
         */
    }
}

/*
    핵심 정리
    생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는 게 더 낮다.
    매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다.
    빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.
 */

