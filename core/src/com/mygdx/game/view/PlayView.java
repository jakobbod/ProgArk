package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ImpossibleGravity;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.GameOverController;
import com.mygdx.game.controller.PlayerController;
import com.mygdx.game.controller.ViewController;
import com.mygdx.game.interactiveElements.MenuBtn;
import com.mygdx.game.interactiveElements.PauseBtn;
import com.mygdx.game.interactiveElements.QuitBtn;
import com.mygdx.game.model.Grass;
import com.mygdx.game.model.Ground;
import com.mygdx.game.model.Heaven;
import com.mygdx.game.model.Obstacle;
import com.mygdx.game.model.ObstacleFactory;
import com.mygdx.game.model.Player;

import java.util.Random;

import java.awt.Menu;

// import com.mygdx.game.controller.GameController;

// Import the sprites here, when these are created in model (e.g. the character, obstacles)

public class PlayView extends SuperView {
    protected GameController gameController;
    private PlayerController pc;
    private Stage stage;

    // The elements in our view - instantiate character, obstacles etc.
    // Can also import e.g. gameWorld, engine etc.
    private Player character;
    private int touchPos;




    //private Array<Ground> grounds = world.getGrounds();

    private ObstacleFactory obstacleFactory;
    private Array<Obstacle> obstacles;
    private long lastObstacle;
    private Random obstacle_occurrence;

    private MenuBtn menuBtn;
    private PauseBtn pauseBtn;

    public PlayView(ViewController vc){

        this.gameController = new GameController(vc);
        this.pc = new PlayerController(vc);
      
        this.pauseBtn = new PauseBtn();
        this.menuBtn = new MenuBtn();
     

        // Setting up the stage, adding the actors (buttons)
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        character = new Player();

        camera.setToOrtho(false, ImpossibleGravity.WIDTH, ImpossibleGravity.HEIGHT);

        // GENERATING NEW OBSTACLES
        obstacleFactory = new ObstacleFactory();
        obstacles = new Array<Obstacle>();
        //obstacles.add(obstacleFactory.generateObstacle(camera.position.x * 2));
        lastObstacle = System.currentTimeMillis();

        menuBtn.getMenuBtn().setPosition(ImpossibleGravity.WIDTH / 10, ImpossibleGravity.HEIGHT, Align.left);
        pauseBtn.getPauseBtn().setPosition(ImpossibleGravity.WIDTH / 3, ImpossibleGravity.HEIGHT, Align.left);

        menuBtn.getMenuBtn().setSize(100, 40);
        pauseBtn.getPauseBtn().setSize(100, 40);

        stage.addActor(pauseBtn.getPauseBtn());
        stage.addActor(menuBtn.getMenuBtn());

        // LISTENERS FOR CLICK GESTURES
        menuBtn.getMenuBtn().addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("menuBtn is clicked");
                gameController.quitGame();
            }
        });

        pauseBtn.getPauseBtn().addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("pauseBtn is clicked");
                gameController.pauseGame();
            }
        });

        // LISTENERS FOR TOUCH GESTURES
        pauseBtn.getPauseBtn().addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("playBtn is touched.");
                gameController.pauseGame();
            }
        });

        menuBtn.getMenuBtn().addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("menuBtn is touched.");
                gameController.quitGame();
            }
        });
        obstacle_occurrence = new Random();
    }

    @Override
    protected void handleInput() {
        // Every input from the user should call on a function for the character.
        // The action is defined in the model-class of the character.

        int deltaY = swipe();

        if (deltaY != 0) {
            System.out.println(deltaY);
            pc.swipe(character, deltaY);
        } else if (Gdx.input.justTouched()) {
            pc.touch(character);
        }

        
        /*
        if (Gdx.input.justTouched()) {
            pc.touch(character);
        }

         */

            // Put the rest of the actions here
/*
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("PauseBtn is pressed.");
                // gameController.pauseGame();
            }
        });

 */

    }


    public int swipe() {
        if (Gdx.input.justTouched() && (Gdx.input.getDeltaY() > 10 || Gdx.input.getDeltaY() < -10)) {
            return Gdx.input.getDeltaY();
        }
        return 0;
    }

    @Override
    public void update(float dt) {
        handleInput();

        // The character must have an update -and getPosition-method in its model.
        // For other methods required, see which functions are called upon character below.
        character.update(dt);
        world.update(dt, camera);


        // TODO: LOGIKKEN FOR OBSTACLES MÅ INN I OBSTACLE
        for (Obstacle obstacle : obstacles) {
            //obstacle.update(dt);

            if(obstacle.collides(character.getBounds())) {
                //gameController.GameOver();
            }

        }

        if (System.currentTimeMillis() - lastObstacle >= 500 + obstacle_occurrence.nextInt(2000)) {
            lastObstacle = System.currentTimeMillis();
            obstacles.add(obstacleFactory.generateObstacle(camera.position.x * 2, 0));
        }
        camera.position.set(character.getPosition().x + 100, ImpossibleGravity.HEIGHT/2, 0);
        camera.update();

            // If character hits ground, change to menu state
        /*
        if(character.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)


            gameController.gameover(); //Have not been made yet

        camera.update();

         */

    }

    @Override
    // Each view is responsible for knowing what it needs to draw.
    // Here we draw the background, character, obstacles and ground.
    public void render (SpriteBatch sb){
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(world.getBackground(), camera.position.x-(camera.viewportWidth/2), camera.position.y-(camera.viewportHeight/2), ImpossibleGravity.HEIGHT, ImpossibleGravity.HEIGHT);
        sb.draw(character.getSprite(), character.getPosition().x, character.getPosition().y);



        sb.draw(world.getGrass().getGround(), world.getGrass().getGroundPos1().x, world.getGrass().getGroundPos1().y);
        sb.draw(world.getGrass().getGround(), world.getGrass().getGroundPos2().x, world.getGrass().getGroundPos2().y);

        for (Obstacle obstacle : obstacles) {
            sb.draw(obstacle.getSpikes(), obstacle.getPosition().x, obstacle.getPosition().y, 70, 100);
        }
        sb.draw(world.getHeaven().getGround(), world.getHeaven().getGroundPos1().x, world.getHeaven().getGroundPos1().y);
        sb.draw(world.getHeaven().getGround(), world.getHeaven().getGroundPos2().x, world.getHeaven().getGroundPos2().y);

        sb.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void dispose(){
        // Remember to dispose of everything drawn on the screen.
        world.dispose();
        character.dispose();


        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }

        System.out.println("Play View Disposed");
    }

    @Override
    public void show() {

    }


}
