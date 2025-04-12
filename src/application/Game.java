package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import application.config.Constants;
import application.config.Controls;
import application.entities.BigEnemy;
import application.entities.Player;
import application.entities.Projectile;
import application.entities.model.CircleObject;
import application.factories.EnemyFactory;
import application.interfaces.HasHP;
import application.physics.Collision;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Game  extends Application {
	private Pane root;
	private Player player;
    private List<Projectile> projectiles = new ArrayList<>();
    private List<CircleObject> enemies   = new ArrayList<>();    
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    
    private long spawnInterval          = Constants.BASE_ENEMY_SPAWN_INTERVAL;
    private long lastEnemySpawnTime     = 0;
    private long spawnAccelerationTimer = 0;
    private int missedBigEnemies        = 0;
    private int score                   = 0;
    
    private boolean isGameOver = false;
    private Label gameOverTitle;
    private Label gameOverLabel;
    private Label scoreLabel;
    private Label livesLabel;
    private Label missedLabel;
    
	@Override
	public void start(Stage primaryStage) {	
		
	    initializeUI(primaryStage);
	    initializeGameLoop();
	}
	
	private void update(long now) {
    	
    	if (isGameOver) {
            handleGameOver();
            return;
        }

        handlePlayerMovement();
        handleEnemySpawn(now);     
        handleShooting();
        handleProjectilesMovement();
        handleEnemiesMovement();
    }
	
	
	private void initializeUI(Stage primaryStage) {
		
	    // Gere la superposition des elements
	    StackPane mainLayout = new StackPane();

	    // Fond du jeu
	    root = new Pane();
	    root.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
	    
	    // Background
	    Image bgImage = new Image(getClass().getResourceAsStream("/backgrounds/bg_blurred.png"));
	    ImageView background = new ImageView(bgImage);

	    background.setFitWidth(Constants.STAGE_WIDTH);
	    background.setFitHeight(Constants.STAGE_HEIGHT);
	    
	    root.getChildren().add(0, background);
	    
	    // UI Layer (au-dessus du root)
	    Pane uiLayer = new Pane();

	    // Label score
	    scoreLabel = new Label("Score : " + score);
	    scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    scoreLabel.setTextFill(Color.WHITE);
	    scoreLabel.setLayoutX(10);
	    scoreLabel.setLayoutY(10);

	    // Label HP joueur
	    livesLabel = new Label("Vies : " + Constants.PLAYER_HP);
	    livesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    livesLabel.setTextFill(Color.WHITE);
	    livesLabel.setLayoutX(10);
	    livesLabel.setLayoutY(40);

	    // Label erreurs ratées
	    missedLabel = new Label("Erreurs ratés : " + missedBigEnemies);
	    missedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    missedLabel.setTextFill(Color.WHITE);
	    missedLabel.setLayoutX(10);
	    missedLabel.setLayoutY(70);

	    // Ajout des éléments UI dans uiLayer (affiché au-dessus du root)
	    uiLayer.getChildren().addAll(scoreLabel, livesLabel, missedLabel);

	    // Labels fin de jeu
	    gameOverTitle = new Label("GAME OVER");
	    gameOverTitle.setTextFill(Color.WHITE);
	    gameOverTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));
	    gameOverTitle.setVisible(false);

	    gameOverLabel = new Label("Appuie sur ENTRÉ pour rejouer");
	    gameOverLabel.setTextFill(Color.LIGHTGRAY);
	    gameOverLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
	    gameOverLabel.setVisible(false);

	    // Place les labels l'un au-dessus de l'autre
	    VBox gameOverBox = new VBox(20, gameOverTitle, gameOverLabel);
	    gameOverBox.setAlignment(Pos.CENTER);

	    // Superposition des elementse
	    mainLayout.getChildren().addAll(root, uiLayer, gameOverBox);

	    // Configuration de la scène
	    Scene scene = new Scene(mainLayout);
	    scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
	    scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

	    // Initialisation de la scène
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Errors Shooter");
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}

	private void initializeGameLoop() {
		
	    // Ajout du joueur
	    player = new Player();
	    root.getChildren().add(player.getHitbox());
	    root.getChildren().add(player.getSprite());
	    
	    // Game loop
	    AnimationTimer timer = new AnimationTimer() {
	        public void handle(long now) {
	            update(now);
	        }
	    };
	    timer.start();
	}
	
	private void shoot() {
		double x = player.getX() + player.getWidth() / 2;
		double y = player.getY();
        Projectile projectile = new Projectile(x, y);
        projectiles.add(projectile);
        root.getChildren().add(projectile.getHitbox());
    }

    private void spawnEnemy() {
    	// Créer un ennemi avec un rayon aléatoire qui determine son type
        CircleObject enemy = EnemyFactory.createRandomEnemy();
        enemies.add(enemy);
        root.getChildren().add(enemy.getHitbox());
        root.getChildren().add(enemy.getSprite());
    }
    
    private void restartGame() {
        isGameOver = false;
        spawnInterval = Constants.BASE_ENEMY_SPAWN_INTERVAL;
        lastEnemySpawnTime = 0;
        spawnAccelerationTimer = 0;
        score = 0;
        missedBigEnemies = 0;

        // Supprime tous les ennemis et projectiles restants
        for (Projectile p : projectiles) root.getChildren().remove(p.getHitbox());
        for (CircleObject e : enemies) {
        	root.getChildren().remove(e.getHitbox()); 
        	root.getChildren().remove(e.getSprite());
        }
        projectiles.clear();
        enemies.clear();

        // Réinitialiser le joueur
        root.getChildren().remove(player.getHitbox());
        root.getChildren().remove(player.getSprite());
        player = new Player();
        root.getChildren().add(player.getHitbox());
        root.getChildren().add(player.getSprite());
        
        // Reinitialise les labels
        gameOverLabel.setVisible(false);
        gameOverTitle.setVisible(false);
        missedLabel.setText("Erreurs ratés : " + missedBigEnemies);
        scoreLabel.setText("Score : " + score);
        livesLabel.setText("Vies : " + player.getHP());
    }
    
    private void handleGameOver() {
        if (pressedKeys.contains(Controls.RESTART))
            restartGame();
    }
    
    private void handlePlayerMovement() {
        if (pressedKeys.contains(Controls.MOVE_LEFT)) player.moveLeft();
        if (pressedKeys.contains(Controls.MOVE_RIGHT)) player.moveRight();
        if (pressedKeys.contains(Controls.MOVE_UP)) player.moveUp();
        if (pressedKeys.contains(Controls.MOVE_DOWN)) player.moveDown();
    }
    
    private void handleEnemySpawn(long now) {
    	// Gestion du spawn d'ennemis
        if (now - lastEnemySpawnTime > spawnInterval) {
            spawnEnemy();
            lastEnemySpawnTime = now;
        }

        // Accélération du spawn toutes les 2 secondes (par exemple)
        if (now - spawnAccelerationTimer >= Constants.SPAWN_ACCELERATION_INTERVAL) {
            if (spawnInterval > Constants.MIN_SPAWN_INTERVAL) {
                spawnInterval -= Constants.SPAWN_DECREASE_STEP;
            }
            // Si l'intervalle est déjà au minimum, ne le modifie plus
            if (spawnInterval < Constants.MIN_SPAWN_INTERVAL) {
                spawnInterval = Constants.MIN_SPAWN_INTERVAL;
            }
            spawnAccelerationTimer = now;
        }
    }
    
    private void handleShooting() {
    	// Le joueur peut tirer plus vite si il spam clique (intentionnel)
        if (pressedKeys.contains(Controls.SHOOT)) {
            player.shootCooldown--;
            if (player.shootCooldown <= 0) {
                shoot();
                player.shootCooldown = Constants.PLAYER_AUTOSHOOT_CD;
            }
        } else {
            player.shootCooldown = 0;
        }
    }

    private void handleProjectilesMovement() {
        Iterator<Projectile> projectileIt = projectiles.iterator();
        while (projectileIt.hasNext()) {
            Projectile p = projectileIt.next();
            p.moveUp();

            if (p.isOutOfScreen()) {
                root.getChildren().remove(p.getHitbox());
                projectileIt.remove();
                score = score - 50;
                if (score < 0) score = 0;
            	scoreLabel.setText("Score : " + score);
                continue;
            }
            
            handleProjectileEnemyCollisions(p, projectileIt);
        }
    }
    
    private void handleEnemiesMovement() {
        Iterator<CircleObject> enemyIt = enemies.iterator();
        while (enemyIt.hasNext()) {
        	CircleObject e = enemyIt.next();
            e.moveDown();

            if (e.isOutOfScreen()) {
            	if (e instanceof BigEnemy) {
                    missedBigEnemies++;
                    missedLabel.setText("Erreurs ratés : " + missedBigEnemies);
                    if (missedBigEnemies >= Constants.MAX_MISSED_BIG_ENEMIES) {
                    	triggerGameOver();
                        return;
                    }
                }
                root.getChildren().remove(e.getHitbox());
                root.getChildren().remove(e.getSprite());
                enemyIt.remove();
                continue;
            }
            
            handlePlayerEnemyCollisions(e, enemyIt);
            
        }
    }
    
    private void handleProjectileEnemyCollisions(Projectile p, Iterator<Projectile> projectileIt) {
        Iterator<CircleObject> enemyIt = enemies.iterator();
        while (enemyIt.hasNext()) {
            CircleObject e = enemyIt.next();
            if (Collision.projectileEnemyCollision(p, e)) {
            	root.getChildren().remove(p.getHitbox());
            	projectileIt.remove();
            	HasHP e_hp = (HasHP) e;
            	e_hp.takeDamage(Constants.PROJECTILE_DAMAGE);
                if (e_hp.isDead()) {
	                root.getChildren().remove(e.getHitbox());
	                root.getChildren().remove(e.getSprite());
	                enemyIt.remove();
	                if (e instanceof BigEnemy) {
	                    score = score + 1000;
	                	scoreLabel.setText("Score : " + score);
	                } else { 
	                	score = score + 100;
	                	scoreLabel.setText("Score : " + score);
	                }
                }
                return; // Projectile est déjà détruit, pas besoin de continuer
            }
        }
    }
    
    private void handlePlayerEnemyCollisions(CircleObject e, Iterator<CircleObject> enemyIt) {
    	if (Collision.playerEnemyCollision(player, e)) {
            root.getChildren().remove(e.getHitbox());
            root.getChildren().remove(e.getSprite());
            enemyIt.remove();
            player.takeDamage(Constants.PROJECTILE_DAMAGE);
            livesLabel.setText("Vies : " + player.getHP());
            if (player.isDead()) 
                triggerGameOver();
    	}
    }
    
    private void triggerGameOver() {
        isGameOver = true;
        gameOverLabel.setVisible(true);
        gameOverTitle.setVisible(true);
    }

}
