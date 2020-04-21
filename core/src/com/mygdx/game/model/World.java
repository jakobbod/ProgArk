package com.mygdx.game.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


public class World {

    private Texture background;
    private Grass grass;
    private Heaven heaven;


    //private Sound sound;

    //TODO make background depended on input to variate between different backgrounds/modes
    private static int BG_MODE;

    public World() {
        background = new Texture("background.png"); //locally saved
        grass = new Grass();
        heaven = new Heaven();

        /* TODO Sound is working but is starting multiple times over each other
            and is delaying the game

        sound = Gdx.audio.newSound(Gdx.files.internal("marioTrack.mp3"));
        sound.play(1f);

         */
    }

    public Texture getBackground() {
        return background;
    }

    public Grass getGrass() {
        return grass;
    }

    public Heaven getHeaven() {
        return heaven;
    }

    /* Might need this to select different backgrounds
    public void setBackground(Texture background) {
        this.background = background;
    }

     */


    /* Might need this to select different backgrounds
    public void setGround(Texture ground) {
        this.ground = ground;
    }

     */

    //public Vector3 getGroundPos() { //last added grounds position
        //return grounds.peek().getGroundPos();
    //}


    public void update(float dt, OrthographicCamera camera) {
        grass.update(dt, camera);
        heaven.update(dt, camera);

    }

    public void dispose() {
        background.dispose();
        //sound.dispose();
        //for (Ground ground : grounds) {
          //  ground.dispose();
        //}
    }
}
