package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.Drawable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;

import static david.vaz.space.neon.refactored.game.Constants.SCORE_X;
import static david.vaz.space.neon.refactored.game.Constants.SCORE_Y;

public final class Score implements Drawable {

    private int score;
    private final Text display;

    public Score() {
        score = 1000;
        display = new Text(SCORE_X, SCORE_Y, "Score");
    }

    @Override
    public void show() {
        display.setColor(Color.CYAN);
        display.grow(40, 15);
        display.draw();
    }

    @Override
    public void hide() {
        display.delete();
    }

    public void update(int score) {
        this.score += score;

        String toDisplay = this.score < 10 ? " 000" + this.score :

                this.score < 100 ? " 00" + this.score :

                        this.score < 1000 ? " 0" + this.score : "" + this.score;

        display.setText("Score:" + toDisplay);
    }

    public int value() {
        return score;
    }
}
