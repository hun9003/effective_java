package item2;

public class Item2 {
    /*
    생성자에 매개변수가 많다면 빌더를 고려하라

    정적 팩터리와 생성자에는 똑같은 제약이 하나 있다.
    선택적 매개변수가 많을 때 적절히 대응하기 어렵다는 점이다.
 */
    public static void main(String[] args) {
        // 점층정 생성자 패턴
        NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0);

        // 자바 빈즈 패턴
        NutritionFacts2 cocaCola2 = new NutritionFacts2();
        cocaCola2.setServingSize(240);
        cocaCola2.setServings(8);
        cocaCola2.setCalories(100);
        cocaCola2.setSodium(35);
        cocaCola2.setCarbohydrate(27);

        // 빌더 패턴
        NutritionFacts3 cocaCola3 = new NutritionFacts3.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();
    }

    /*
        점층적 생성자 패턴 - 확장하기 어렵다.

        보통 이런 생성자는 사용자가 설정하길 원치 않는 매개변수까지 포함하기 쉬운데,
        어쩧 수 없이 그랜 매개변수에도 값을 지정해줘야 한다.

        점층적 생성자 패턴도 쓸 수는 있지만, 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.
     */
    public static class NutritionFacts {
        private final int servingSize;  //  필수      (ml, 1회 제공량)
        private final int servings;     //  필수      (회, 총 n회 제공량)
        private final int calories;     //  선택      (1회 제공량당)
        private final int fat;          //  선택      (g/1회 제공량)
        private final int sodium;       //  선택      (mg/1회 제공량)
        private final int carbohydrate; //  선택      (g/1회 제공량)

        public NutritionFacts(int servingSize, int servings) {
            this(servingSize, servings, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories) {
            this(servingSize, servings, calories,0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat) {
            this(servingSize, servings, calories, fat,0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
            this(servingSize, servings, calories, fat, sodium, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
            this.servingSize = servingSize;
            this.servings = servings;
            this.calories = calories;
            this.fat = fat;
            this.sodium = sodium;
            this.carbohydrate = carbohydrate;
        }
    }

    /*
        자바빈즈 패턴 - 일관성이 깨지고, 불변으로 만들 수 없다.

        점층적 생성자 패턴의 단점들이 자바빈즈 패턴에서는 더 이상 보이지 않는다.
        코드가 길어지긴 했지만 인스턴스를 만들기 쉽고, 그 결과 더 읽기 쉬운 코드가 되었다.

        자바빈즈 패턴에서는 객체 하나를 만들려면 메서드를 여러 개 호출해야 하고,
        객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 된다
     */
    public static class NutritionFacts2 {
        private int servingSize   = -1;   //  필수      (ml, 1회 제공량)
        private int servings      = -1;   //  필수      (회, 총 n회 제공량)
        private int calories      = 0;    //  선택      (1회 제공량당)
        private int fat           = 0;    //  선택      (g/1회 제공량)
        private int sodium        = 0;    //  선택      (mg/1회 제공량)
        private int carbohydrate  = 0;    //  선택      (g/1회 제공량)

        public NutritionFacts2() {}
        // 세터 메서드
        public void setServingSize(int servingSize) { this.servingSize = servingSize; }
        public void setServings(int servings) { this.servings = servings; }
        public void setCalories(int calories) { this.calories = calories; }
        public void setFat(int fat) { this.fat = fat; }
        public void setSodium(int sodium) { this.sodium = sodium; }
        public void setCarbohydrate(int carbohydrate) { this.carbohydrate = carbohydrate; }
    }

    /*
        빌더 패턴(Builder pattern)

        클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(혹은 정적 팩터리)를 호출해 빌더 객체를 얻는다.
        그런 다음 빌더 객체가 제공하는 일종의 세터 메서드들로 원하는 선택 매개변수들을 설정한다.
        마지막으로 매개변수가 없는 build 메서드를 호출해 드디어 우리가 필요한 객체를 얻는다.
     */
    public static class NutritionFacts3 {
        private final int servingSize;  //  필수      (ml, 1회 제공량)
        private final int servings;     //  필수      (회, 총 n회 제공량)
        private final int calories;     //  선택      (1회 제공량당)
        private final int fat;          //  선택      (g/1회 제공량)
        private final int sodium;       //  선택      (mg/1회 제공량)
        private final int carbohydrate; //  선택      (g/1회 제공량)

        public static class Builder {
            // 필수 매개변수
            private final int servingSize;
            private final int servings;

            // 선택 매개변수 - 기본값으로 초기화 한다.
            private int calories        = 0;
            private int fat             = 0;
            private int sodium          = 0;
            private int carbohydrate    = 0;

            public Builder(int servingSize, int servings) {
                this.servingSize = servingSize;
                this.servings = servings;
            }

            public Builder calories(int val) {
                calories = val;
                return this;
            }

            public Builder fat(int val) {
                fat = val;
                return this;
            }

            public Builder sodium(int val) {
                sodium = val;
                return this;
            }

            public Builder carbohydrate(int val) {
                carbohydrate = val;
                return this;
            }

            public NutritionFacts3 build() {
                return new NutritionFacts3(this);
            }
        }

        private NutritionFacts3(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }
    }
}

