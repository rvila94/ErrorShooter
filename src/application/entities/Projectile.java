package application.entities;

import application.config.Constants;
import application.entities.model.RectangleObject;
import javafx.scene.paint.Color;

public class Projectile extends RectangleObject{
    				
    public Projectile(double x, double y) {
        super(Constants.PROJECTILE_WIDTH, Constants.PROJECTILE_HEIGHT, Color.LIMEGREEN);
        hitbox.setVisible(true);
        
    	this.x = x;
        this.y = y;
        
        updateView();
    }
    
    @Override
    public void moveUp() {
        y -= Constants.PROJECTILE_SPEED;
        updateView();       
    }

    public boolean isOutOfScreen() {
        return y < 0;
    }

	@Override
	public void moveLeft() {
		return; // Les projectiles ne bougent que vers le haut pour le moment		
	}

	@Override
	public void moveRight() {
		return; // Les projectiles ne bougent que vers le haut pour le moment		
	}

	@Override
	public void moveDown() {
		return; // Les projectiles ne bougent que vers le haut pour le moment	
	}

}
