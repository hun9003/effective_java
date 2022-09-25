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
}

