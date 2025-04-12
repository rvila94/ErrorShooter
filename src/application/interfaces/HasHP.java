package application.interfaces;

public interface HasHP {
    int getHP();
    void takeDamage(int damage);
    boolean isDead();
}