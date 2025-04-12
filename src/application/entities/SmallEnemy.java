package application.entities;

import application.config.Constants;
import application.entities.model.CircleObject;
import application.interfaces.HasHP;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SmallEnemy extends CircleObject implements HasHP {

    private int hp;
    private ImageView sprite;

    public SmallEnemy(double radius) {
        super(radius, Color.PINK);
        
        hp = Constants.SMALL_ENEMY_HP;
        
        Image image = new Image(getClass().getResourceAsStream("/sprites/warning.png"));
        sprite = new ImageView(image);
        sprite.setFitWidth(radius * 2);
        sprite.setFitHeight(radius * 2);
    }
    
    @Override
	public void updateView() {
    	super.updateView();
        sprite.setTranslateX(x - radius);
        sprite.setTranslateY(y - radius);
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public void moveDown() { 
    	// Plus c’est "gros" plus c’est lent
        double speed = 
        		Constants.SMALL_ENEMY_MAX_SPEED - (radius - Constants.ENEMY_MIN_RADIUS) 
        		* (Constants.SMALL_ENEMY_MAX_SPEED - Constants.SMALL_ENEMY_MIN_SPEED) 
        		/ (Constants.SMALL_ENEMY_MAX_RADIUS - Constants.ENEMY_MIN_RADIUS);
        y += speed;
        updateView();}

	@Override
	public void moveLeft() {
		return; // Les ennemies ne bougent que vers le bas pour le moment
	}

	@Override
	public void moveRight() {
		return; // Les ennemies ne bougent que vers le bas pour le moment
	}

	@Override
	public void moveUp() {
		return; // Les ennemies ne bougent que vers le bas pour le moment
	}
	
	@Override
	public ImageView getSprite() {
		return sprite;
	}
}
