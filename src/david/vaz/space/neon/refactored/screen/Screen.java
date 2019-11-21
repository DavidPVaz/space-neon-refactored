package david.vaz.space.neon.refactored.screen;

import david.vaz.space.neon.refactored.drawable.Drawable;
import david.vaz.space.neon.refactored.input.Input;

public interface Screen extends Drawable {

    void setup();

    void process(Input input);

}
