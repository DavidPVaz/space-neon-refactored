package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.Drawable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class Frames implements Drawable {

    private final Text display;

    public Frames() {
        display = new Text(FPS_X, FPS_Y, "FPS");
    }

    @Override
    public void show() {
        display.setColor(Color.WHITE);
        display.draw();
    }

    @Override
    public void hide() {
        display.delete();
    }

    public void update(int fps){
        display.setText("FPS: " + fps);
    }
}
