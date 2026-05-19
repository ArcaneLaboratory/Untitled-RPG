package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.TextureComponent;
import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.arcanelaboratory.untitledrpg.utils.GlobalConstants;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer otmp;
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<TextureComponent> txm = ComponentMapper.getFor(TextureComponent.class);

    private static class ZComparator implements Comparator<Entity>{
        private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

        @Override
        public int compare(Entity e1, Entity e2){
            return Float.compare(tm.get(e2).y, tm.get(e1).y);
        }
    }

    public RenderSystem(SpriteBatch batch, OrthographicCamera camera, OrthogonalTiledMapRenderer otmp){
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());
        this.batch = batch;
        this.camera = camera;
        this.otmp = otmp;
    }

    @Override
    public void update(float delta){
        forceSort();
        otmp.setView(camera);
        otmp.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.update(delta);

        batch.end();
    }

    @Override
    protected void processEntity(Entity e, float delta){
        TransformComponent pos = tm.get(e);
        TextureComponent tex = txm.get(e);
        batch.draw(tex.region, pos.x, pos.y, tex.region.getRegionWidth()*GlobalConstants.MAP_SCALE, tex.region.getRegionHeight()*GlobalConstants.MAP_SCALE);
    }
}
