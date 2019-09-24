package gittest;

public class A {
    public static void main(String[] args) {
        String testPlacements[] = new String[]{"one", "two",
                "three","four"
        };


        for(int i = 0; i < 4; i++){
            System.out.println(testPlacements[i]);
        }
    }
    public String toString() {
        return "A";
    }
}
