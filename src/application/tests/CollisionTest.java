package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.physics.Collision;

public class CollisionTest {

    // === Tests collision entre deux rectangles ===

    @Test
    void testRectRectCollision_overlap() {
        // Deux rectangles qui se superposent partiellement
        assertTrue(Collision.rectRectCollision(0, 0, 10, 10, 5, 5, 10, 10));
    }

    @Test
    void testRectRectCollision_noOverlap() {
        // Deux rectangles éloignés
        assertFalse(Collision.rectRectCollision(0, 0, 10, 10, 20, 20, 5, 5));
    }

    @Test
    void testRectRectCollision_touchingEdges() {
        // Deux rectangles qui se touchent exactement par les bords (pas de collision)
        assertFalse(Collision.rectRectCollision(0, 0, 10, 10, 10, 0, 10, 10));
    }

    @Test
    void testRectRectCollision_contained() {
        // Un rectangle complètement à l’intérieur de l’autre
        assertTrue(Collision.rectRectCollision(0, 0, 20, 20, 5, 5, 5, 5));
    }

    // === Tests collision entre cercle et rectangle ===

    @Test
    void testCircleRectCollision_overlap() {
        // Cercle qui touche et dépasse un peu le rectangle
        assertTrue(Collision.circleRectCollision(10, 10, 10, 10, 15, 15, 8));
    }

    @Test
    void testCircleRectCollision_noOverlap() {
        // Cercle loin du rectangle
        assertFalse(Collision.circleRectCollision(0, 0, 10, 10, 30, 30, 5));
    }

    @Test
    void testCircleRectCollision_touchingCorner() {
        // Le cercle touche exactement le coin du rectangle (distance = rayon)
        assertTrue(Collision.circleRectCollision(0, 0, 10, 10, 20, 20, Math.sqrt(200)));
    }

    @Test
    void testCircleRectCollision_inside() {
        // Le cercle est entièrement à l’intérieur du rectangle
        assertTrue(Collision.circleRectCollision(0, 0, 20, 20, 10, 10, 5));
    }

    @Test
    void testCircleRectCollision_touchingEdge() {
        // Le cercle touche juste le bord du rectangle
        assertTrue(Collision.circleRectCollision(10, 10, 10, 10, 20, 15, 5));
    }
}
