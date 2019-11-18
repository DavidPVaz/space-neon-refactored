package david.vaz.space.neon.refactored.drawable;

import david.vaz.space.neon.refactored.resources.Image;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class AbstractDrawable implements Drawable {

    private Picture picture;

    public AbstractDrawable(double x, double y, Image image) {
        this.picture = new Picture(x, y, image.path());
    }

    @Override
    public void show() {
        picture.draw();
    }

    @Override
    public void hide() {
        picture.delete();
    }

    protected Picture getPicture() {
        return picture;
    }

    protected void load(String imagePath) {
        picture.load(imagePath);
    }
}
