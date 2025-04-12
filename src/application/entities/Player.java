package application.entities;

import application.config.Constants;
import application.entities.model.RectangleObject;
import application.interfaces.HasHP;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Player extends RectangleObject implements HasHP{

    private int hp = 3;
    public int shootCooldown = 0;
    private ImageView sprite;

    public Player() {
    	super(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Color.BLUE);
    	
    	// Position au milieu de l'axe X, en bas de l'axe Y
    	x = (Constants.STAGE_WIDTH - Constants.PLAYER_WIDTH) / 2;
    	y = Constants.STAGE_HEIGHT - Constants.PLAYER_HEIGHT - 50;
    	
    	Image image = new Image(getClass().getResourceAsStream("/sprites/computer.png"));
        sprite = new ImageView(image);
        sprite.setFitWidth(width);
        sprite.setFitHeight(height);
    	
    	updateView();
    }
    
    @Override
	public void updateView() {
    	super.updateView();
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
    }
 
    // Gestion de collision avec bordures dans deplacements
    @Override
    public void moveLeft() {
    	x = Math.max(0, x - Constants.PLAYER_SPEED);
        updateView();
    }
    
    @Override
    public void moveRight() {
    	x = Math.min(Constants.STAGE_WIDTH - Constants.PLAYER_WIDTH, x + Constants.PLAYER_SPEED);
        updateView();
    }
    
    @Override
    public void moveUp() {
    	y = Math.max(0, y - Constants.PLAYER_SPEED);
        updateView();
    }
    
    @Override
    public void moveDown() {
    	y = Math.min(Constants.STAGE_HEIGHT - Constants.PLAYER_HEIGHT, y + Constants.PLAYER_SPEED);
        updateView();
    }

	@Override
	public int getHP() {
		return hp;
	}

	@Override
	public void takeDamage(int damage) {
		if (hp == 0) return;
        hp -= damage;	
	}
	
	@Override
	public boolean isDead() {
        return hp <= 0;
    }
	
	public ImageView getSprite() {
		return sprite;
	}
}
