package application.config;

public class Constants {
	
    // Fenêtre
    public static final double STAGE_WIDTH  = 500;
    public static final double STAGE_HEIGHT = 700;

    // Joueurs
    public static final double PLAYER_WIDTH  = 65;
    public static final double PLAYER_HEIGHT = 60;
    public static final double PLAYER_SPEED  = 5;
    public static final int    PLAYER_HP     = 3;

    // Projectiles
    public static final double PROJECTILE_WIDTH  = 7;
    public static final double PROJECTILE_HEIGHT = 20;
    public static final double PROJECTILE_SPEED  = 15;
    public static final int    PROJECTILE_DAMAGE = 1;

    // Enemies
    public final static int MAX_MISSED_BIG_ENEMIES = 3;
    
    public final static int ENEMY_MAX_RADIUS = 35;
    public final static int ENEMY_MIN_RADIUS = 15;
    
    public final static int    BIG_ENEMY_MIN_RADIUS = 32;
    public final static double BIG_ENEMY_MAX_SPEED  = 4;
    public final static double BIG_ENEMY_MIN_SPEED  = 2;
    //public final static int    BIG_ENEMY_MAX_HP     = 5;
    public final static int    BIG_ENEMY_MIN_HP     = 3;
    
    public final static int    SMALL_ENEMY_MAX_RADIUS = 32;
    public final static double SMALL_ENEMY_MAX_SPEED  = 7;
    public final static double SMALL_ENEMY_MIN_SPEED  = 5;
    public final static int    SMALL_ENEMY_HP         = 1;

    // Spawnrate
    public static final long BASE_ENEMY_SPAWN_INTERVAL = 1_000_000_000L;  // 1 seconde (1 000 000 000 nanosecondes)
    public static final long SPAWN_ACCELERATION_INTERVAL = 5_000_000_000L; // 5 secondes 
    public static final long SPAWN_DECREASE_STEP = 100_000_000L;  // Réduction de 100 ms à chaque étape 
    public static final long MIN_SPAWN_INTERVAL = 200_000_000L;  // 200 ms 
        
    // Cooldowns
    public static final int  PLAYER_AUTOSHOOT_CD = 15;
    
    // Hitbox
    public static final boolean SHOW_HITBOX = false;
}
