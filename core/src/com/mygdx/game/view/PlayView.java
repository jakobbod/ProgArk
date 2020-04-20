package com.mygdx.game.view;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ImpossibleGravity;
// import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.GameOverController;
import com.mygdx.game.controller.PlayerController;
import com.mygdx.game.controller.ViewController;
import com.mygdx.game.interactiveElements.PauseBtn;
import com.mygdx.game.model.Ground;
import com.mygdx.game.model.Obstacle;
import com.mygdx.game.model.ObstacleFactory;
import com.mygdx.game.model.Player;


// Import the sprites here, when these are created in model (e.g. the character, obstacles)

public class PlayView extends SuperView {
    protected ViewController gameController;
    private PlayerController pc;
    protected GameController gc;
    private Stage stage;
    private PauseBtn pauseBtn;

    // The elements in our view - instantiate character, obstacles etc.
    // Can also import e.g. gameWorld, engine etc.
    private Player character;

    private int touchPos;

    private Ground ground;


    //private Array<Ground> grounds = world.getGrounds();

    private ObstacleFactory obstacleFactory;
    private Array<Obstacle> obstacles;
    private long lastObstacle;



    public PlayView(ViewController vc){

        this.gameController = vc;
        this.pc = new PlayerController(vc);
        // this.pauseBtn = new PauseBtn();
        this.gc = new GameController(vc);

        // Setting up the stage, adding the actors (buttons)
        stage = new Stage(new ScreenViewport());
        // stage.addActor(pauseBtn);
        Gdx.input.setInputProcessor(stage);

        // Position the button
        // pauseBtn.setPosition(camera.position.x - pauseBtn.getWidth() / 2, camera.position.y);

        character = new Player();
        ground = new Ground();

        camera.setToOrtho(false, ImpossibleGravity.WIDTH, ImpossibleGravity.HEIGHT);

        // GENERATING NEW OBSTACLES
        obstacleFactory = new ObstacleFactory();
        obstacles = new Array<Obstacle>();
        //obstacles.add(obstacleFactory.generateObstacle(camera.position.x * 2));
        lastObstacle = System.currentTimeMillis();
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
        //world.update(dt);

        // TODO: LOGIKKEN FOR GROUND MÅ INN I GROUND/WORLD
        if (camera.position.x -(camera.viewportWidth / 2) > ground.getGroundPos1().x + ImpossibleGravity.WIDTH) {
            ground.getGroundPos1().add(ImpossibleGravity.WIDTH * 2, 0, 0);
        }
        if (camera.position.x -(camera.viewportWidth / 2) > ground.getGroundPos2().x + ImpossibleGravity.WIDTH) {
            ground.getGroundPos2().add(ImpossibleGravity.WIDTH * 2, 0, 0);
        }



        // TODO: LOGIKKEN FOR OBSTACLES MÅ INN I OBSTACLE
        for (Obstacle obstacle : obstacles) {
            //obstacle.update(dt);

            if(obstacle.collides(character.getBounds())) {
                gc.GameOver();
            }

        }

        if (System.currentTimeMillis() - lastObstacle >= 2000 ) {
            lastObstacle = System.currentTimeMillis();
            obstacles.add(obstacleFactory.generateObstacle(camera.position.x * 2, ground.getGroundHeight() - 10));
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

            sb.draw(ground.getGround(), ground.getGroundPos1().x, ground.getGroundPos1().y);
            sb.draw(ground.getGround(), ground.getGroundPos2().x, ground.getGroundPos2().y);

            //for (Ground ground : grounds) {
            //sb.draw(ground.getGround(), world.getGroundPos().x, world.getGroundPos().y);
            //}
       
            for (Obstacle obstacle : obstacles) {
                sb.draw(obstacle.getSpikes(), obstacle.getPosition().x, obstacle.getPosition().y, 70, 100);
            }

            sb.draw(ground.getGround(), ground.getGroundPos1().x, ground.getGroundPos1().y);
            sb.draw(ground.getGround(), ground.getGroundPos2().x, ground.getGroundPos2().y);


            sb.end();
        }

    @Override
    public void dispose(){
        // Remember to dispose of everything drawn on the screen.
        world.dispose();
        character.dispose();
        ground.dispose();
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }

        System.out.println("Play View Disposed");
    }


}
