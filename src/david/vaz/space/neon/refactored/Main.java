package david.vaz.space.neon.refactored;

import david.vaz.space.neon.refactored.engine.Engine;

public class Main {
    public static void main(String[] args) {

        Engine engine = new Engine(70);

        engine.init();
        engine.start();
    }
}
