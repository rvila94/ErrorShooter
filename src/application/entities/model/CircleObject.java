package application.entities.model;

import application.config.Constants;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class CircleObject {
    protected Circle hitbox;
    protected double x;
    protected double y;
    protected double radius;

    public CircleObject(double radius, Color color) {
        this.radius = radius;
        hitbox = new Circle(radius, color);
        hitbox.setVisible(Constants.SHOW_HITBOX);
        
        // position aléatoire sur l'axe X
        x  = radius + Math.random() * (Constants.STAGE_WIDTH - 2 * radius + 1);
        y  = -radius;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void updateView() {
        hitbox.setTranslateX(x);
        hitbox.setTranslateY(y);
    }
    
    
    public boolean isOutOfScreen() {
        return y - radius > Constants.STAGE_HEIGHT;
    }

    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void moveUp();
    public abstract void moveDown();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
    
    public ImageView getSprite() {
    	return null;
    }
}
