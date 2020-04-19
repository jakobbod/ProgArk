package com.mygdx.game.interactiveElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// Making texture into a button
public class PlayBtn extends Actor {
    private Texture playTexture;
    private Texture playPressTexture;
    public Image playBtn;

    public PlayBtn() {
        // Making the play button
        playTexture = new Texture(Gdx.files.internal("playBtn.png"));
        playPressTexture = new Texture(Gdx.files.internal("playBtn.png"));
        //this.playBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)),new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        this.playBtn = new Image(new TextureRegionDrawable(new TextureRegion(playTexture)));
    }

    public Image getPlayBtn(){
        return this.playBtn;
    }


}
