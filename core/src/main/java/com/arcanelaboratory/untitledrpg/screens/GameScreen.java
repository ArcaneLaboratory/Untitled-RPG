package com.arcanelaboratory.untitledrpg.screens;

import com.arcanelaboratory.untitledrpg.Main;
import com.arcanelaboratory.untitledrpg.entity.EntityFactory;
import com.arcanelaboratory.untitledrpg.systems.*;
import com.arcanelaboratory.untitledrpg.utils.GlobalConstants;
import com.arcanelaboratory.untitledrpg.utils.MapManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private final Main GAME;
    private final OrthographicCamera CAMERA;
    private final FitViewport VIEWPORT;
    private final Engine ENGINE;
    private final EntityFactory FACTORY;
    private final MapManager MAP_MANAGER;
    private final TextureAtlas ATLAS;
    //private final HUD HUD;

    private TiledMap map;
    private OrthogonalTiledMapRenderer otmp;

    public GameScreen(Main game, String mapName){
        this.GAME = game;
        this.ENGINE = new Engine();
        this.CAMERA = new OrthographicCamera();
        this.VIEWPORT = new FitViewport(GlobalConstants.MAP_WIDTH_TILES, GlobalConstants.MAP_HEIGHT_TILES, CAMERA);

        this.MAP_MANAGER = new MapManager();
        //this.HUD = new HUD(GAME.batch);

        this.ATLAS = GAME.assetManager.get("atlas/game_assets.atlas", TextureAtlas.class);
        this.FACTORY = new EntityFactory(ENGINE, GAME.library, ATLAS);
        this.map = GAME.assetManager.get(String.format("maps/%s.tmx", mapName));
        this.otmp = new OrthogonalTiledMapRenderer(map, GlobalConstants.MAP_SCALE);

        MAP_MANAGER.loadCollisions(map);

        ENGINE.addSystem(new PlayerControlSystem());
        ENGINE.addSystem(new MovementSystem());
        ENGINE.addSystem(new CollisionSystem(MAP_MANAGER.getCollisionRects()));
        ENGINE.addSystem(new CameraSystem(CAMERA));
        ENGINE.addSystem(new RenderSystem(GAME.batch, CAMERA, otmp));
        ENGINE.addSystem(new DebugRenderSystem(CAMERA, MAP_MANAGER));
        ENGINE.addSystem(new HUDRenderSystem(GAME.batch));

        ENGINE.addEntity(FACTORY.createPlayer("default",5, 5));
        ENGINE.addEntity(FACTORY.createEnemy("placeholder", 10, 10));
    }

    @Override
    public void show(){
        //CAMERA.position.set(20, 10, 0);
        //TODO: load map when screen becomes active
    }

    @Override
    public void render(float delta){
        //clear the screen
        ScreenUtils.clear(Color.BLACK);
        //render the map
//        CAMERA.update();
//        otmp.setView(CAMERA);
//        otmp.render();
        //update the engine's time by delta
        ENGINE.update(delta);

        //GAME.batch.setProjectionMatrix(HUD.getStage().getCamera().combined);
        //HUD.draw();
    }

    @Override
    public void dispose(){
        // clean up
        ENGINE.removeAllEntities();

    }

    @Override
    public void resize(int width, int height){
        VIEWPORT.update(width, height);
    }

    @Override
    public void pause(){
        //TODO: logic for pauses
    }

    @Override
    public void resume(){
        //TODO: logic for resumption
    }

    @Override
    public void hide(){
        //TODO: figure out what this is for
    }
}
