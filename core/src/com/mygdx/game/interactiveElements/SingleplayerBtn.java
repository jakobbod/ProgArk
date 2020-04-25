package com.mygdx.game.interactiveElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SingleplayerBtn extends Actor {

    private Texture singleTexture;
    public Image singleplayerBtn;

    public SingleplayerBtn() {
        singleTexture = new Texture(Gdx.files.internal("singleplayerBtn.png"));
        this.singleplayerBtn = new Image(new TextureRegionDrawable(new TextureRegion(singleTexture)));
    }

    public Image getSingleplayerBtn(){
        return this.singleplayerBtn;
    }
}