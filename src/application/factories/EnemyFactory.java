package application.factories;

import application.config.Constants;
import application.entities.BigEnemy;
import application.entities.SmallEnemy;
import application.entities.model.CircleObject;

public class EnemyFactory {
    
    public static CircleObject createRandomEnemy() {
    	
    	// Rayon aleatoire entre ENEMY_MIN_RADIUS et ENEMY_MAX_RADIUS
        double radius = Constants.ENEMY_MIN_RADIUS + (int) (Math.random() * (Constants.ENEMY_MAX_RADIUS - Constants.ENEMY_MIN_RADIUS + 1));
        
        if (radius >= Constants.BIG_ENEMY_MIN_RADIUS) {
            return new BigEnemy(radius);
        } else {
            return new SmallEnemy(radius);
        }
    }
}