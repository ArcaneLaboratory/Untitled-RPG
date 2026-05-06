package com.arcanelaboratory.untitledrpg;

import com.arcanelaboratory.untitledrpg.entity.DataLibrary;
import com.arcanelaboratory.untitledrpg.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public AssetManager assetManager;
    public DataLibrary library;

    @Override
    public void create() {
        library = new DataLibrary();
        library.load();
        assetManager = new AssetManager();
        assetManager.load("atlas/game_assets.atlas", TextureAtlas.class);
        batch = new SpriteBatch();
        //TODO: switch from hardcoded to dynamic
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("maps/firstmap.tmx", TiledMap.class);
        assetManager.finishLoading();

        this.setScreen(new GameScreen(this, "firstmap"));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
