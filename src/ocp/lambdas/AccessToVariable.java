package ocp.lambdas;

public class AccessToVariable {
    public static void main(String[] args) {

    }
}

@FunctionalInterface
interface Gorilla { String move(); }

class GorillaFamaly {
    String walk = "walk";
    void everyonePlay(boolean baby) {
        String approach = "amble";
//         approach = "run";

        play(() -> walk);
        play(() -> baby ? "hitch a ride" : "run");
        play(() -> approach);
        play(() -> GorillaTeam.run);
    }
    void play(Gorilla g) {
        System.out.println(g.move());
    }
}

class GorillaTeam {
    static String run = "run";
}
