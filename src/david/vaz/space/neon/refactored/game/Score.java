package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.Drawable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;

import static david.vaz.space.neon.refactored.game.Constants.SCORE_X;
import static david.vaz.space.neon.refactored.game.Constants.SCORE_Y;

final class Score implements Drawable {

    private int score;
    private Text display;

    Score () {
        this.score = 0;
        this.display = new Text(SCORE_X, SCORE_Y, "Score");
    }

    @Override
    public void show() {
        display.setColor(Color.CYAN);
        display.grow(35, 15);
        display.draw();
    }

    @Override
    public void hide() {
        display.delete();
    }

    void update(int score){
        this.score += score;
        display.setText("Score: " + this.score);
    }
}
