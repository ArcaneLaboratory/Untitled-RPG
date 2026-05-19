package com.arcanelaboratory.untitledrpg.screens;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
    private Stage stage;
    private Viewport viewport;

    private Label testLabel;
    private Image testImage;

    public HUD(SpriteBatch batch){
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        Skin skin = new Skin(atlas);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        testLabel = new Label("HP: 100", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(testLabel).expandX().padTop(10);

        testImage = new Image(new Texture(new FileHandle("ui/testbar.png")));
        table.bottom();
        table.add(testImage).bottom();

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public Stage getStage(){
        return stage;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose(){
        stage.dispose();
    }
}
