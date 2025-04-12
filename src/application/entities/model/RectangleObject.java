package application.entities.model;

import application.config.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class RectangleObject {
    protected Rectangle hitbox;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public RectangleObject(double width, double height, Color color) {
    	this.width  = width;
    	this.height = height;
    	hitbox = new Rectangle(width, height, color);
    	hitbox.setVisible(Constants.SHOW_HITBOX);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void updateView() {
    	hitbox.setTranslateX(x);
    	hitbox.setTranslateY(y);
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
    
    public double getWidth() {
    	return width;
    }
    
    public double getHeight() {
    	return height;
    }
}
