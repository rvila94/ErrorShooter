package application.physics;

import application.entities.Player;
import application.entities.Projectile;
import application.entities.model.CircleObject;
import application.entities.model.RectangleObject;

/**
 * Classe utilitaire pour gérer les collisions entre différents objets du jeu.
 */
public class Collision {
	
	/**
     * Vérifie s'il y a collision entre deux rectangles.
     *
     * @param x1 Coordonnée X du premier rectangle
     * @param y1 Coordonnée Y du premier rectangle
     * @param w1 Largeur du premier rectangle
     * @param h1 Hauteur du premier rectangle
     * @param x2 Coordonnée X du second rectangle
     * @param y2 Coordonnée Y du second rectangle
     * @param w2 Largeur du second rectangle
     * @param h2 Hauteur du second rectangle
     * @return true s'il y a une collision, false sinon
     */
    public static boolean rectRectCollision(
            double x1, double y1, double w1, double h1,
            double x2, double y2, double w2, double h2) {

        return x1 < x2 + w2 &&
               x1 + w1 > x2 &&
               y1 < y2 + h2 &&
               y1 + h1 > y2;
    }
    
    /**
     * Vérifie s'il y a collision entre un cercle et un rectangle.
     *
     * @param rx Coordonnée X du rectangle
     * @param ry Coordonnée Y du rectangle
     * @param rw Largeur du rectangle
     * @param rh Hauteur du rectangle
     * @param cx Coordonnée X du centre du cercle
     * @param cy Coordonnée Y du centre du cercle
     * @param r  Rayon du cercle
     * @return true s'il y a une collision, false sinon
     */
    public static boolean circleRectCollision(
    		double rx, double ry, double rw, double rh,
            double cx, double cy, double r) {

        double closestX = clamp(cx, rx, rx + rw);
        double closestY = clamp(cy, ry, ry + rh);
        double dx = cx - closestX;
        double dy = cy - closestY;

        return dx * dx + dy * dy < r * r;
    }

    /**
     * Force une valeur à rester dans une plage donnée.
     *
     * @param value La valeur à restreindre
     * @param min   La borne inférieure
     * @param max   La borne supérieure
     * @return La valeur restreinte entre min et max
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
    
    /**
     * Vérifie la collision entre un projectile (rectangle) et un ennemi (cercle) auquel on crée une hitbox rectangulaire.
     * 
     * @param p Le projectile
     * @param e L'ennemi
     * @return true s'il y a une collision, false sinon
     */
    public static boolean projectileEnemyCollision(Projectile p, CircleObject e) {
        return rectRectCollision(
                p.getX(), p.getY(), p.getWidth(), p.getHeight(),
                e.getX() - e.getRadius(), e.getY() - e.getRadius(), e.getRadius() * 2, e.getRadius() * 2);
    }
    
    /**
     * Vérifie la collision entre un joueur (rectangle) et un ennemi (cercle).
     *
     * @param p Le joueur
     * @param e L'ennemi
     * @return true s'il y a une collision, false sinon
     */
    public static boolean playerEnemyCollision(Player p, CircleObject e) {
        return circleRectCollision(
                p.getX(), p.getY(), p.getWidth(), p.getHeight(),
                e.getX(), e.getY(), e.getRadius());
    }
    
    
    
    /* 
		Nous n'utilisons pas ces deux prochaines méthodes car on s'est appercu que cette detection 
		n'est pas une optimisation dans notre cas 
    */
    
    
    /**
     * Calcule la distance entre le centre d'un cercle et le point du rectangle le plus proche.
     *
     * @param rectX         Position X du coin supérieur gauche du rectangle.
     * @param rectY         Position Y du coin supérieur gauche du rectangle.
     * @param rectWidth     Largeur du rectangle.
     * @param rectHeight    Hauteur du rectangle.
     * @param circleCenterX Coordonnée X du centre du cercle.
     * @param circleCenterY Coordonnée Y du centre du cercle.
     * @param circleRadius  Rayon du cercle.
     * @return La distance entre le bord du rectangle et le centre du cercle.
     */
    public static double calculateDistanceCircleRectangle(
    		double rectX,         double rectY,         double rectWidth, double rectHeight,
    		double circleCenterX, double circleCenterY, double circleRadius) {
    	
		// Trouve le point le plus proche du rectangle par rapport au cercle
		double closestX = clamp(circleCenterX, rectX, rectX + rectWidth);
		double closestY = clamp(circleCenterY, rectY, rectY + rectHeight);
		
		// Calculer la distance entre le centre du cercle et le point le plus proche du rectangle
		double distanceX = circleCenterX - closestX;
		double distanceY = circleCenterY - closestY;
		
		return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }  
    
    /**
     * Vérifie si un rectangle et un cercle sont suffisamment proches
     * pour qu'une collision soit potentiellement possible.
     * Cette méthode est utilisée comme optimisation pour éviter
     * les calculs de collisions quand les objets sont trop éloignés.
     *
     * @param r Le rectangle (objet qui hérite de RectangleObject).
     * @param c L'ennemi représenté par un cercle.
     * @return true si la distance entre les deux objets est inférieure au seuil défini, false sinon.
     */
    public static boolean closeEnoughForCollision(RectangleObject r, CircleObject c) {

		double threshold = c.getRadius() * 1.5;
		double distance = calculateDistanceCircleRectangle( 
				r.getX(), r.getY(), r.getWidth(), r.getHeight(),
    			c.getX(), c.getX(), c.getRadius());
		
		return (distance < threshold);   	
    }
}
