package david.vaz.space.neon.refactored;

import david.vaz.space.neon.refactored.engine.Engine;

public class Main {
    public static void main(String[] args) {

        int targetFrames = 60;

        Engine engine = new Engine(targetFrames);

        engine.init();
        engine.start();
    }
}
