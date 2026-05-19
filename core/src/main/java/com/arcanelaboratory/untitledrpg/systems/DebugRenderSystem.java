package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CollisionComponent;
import com.arcanelaboratory.untitledrpg.utils.MapManager;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class DebugRenderSystem extends IteratingSystem {
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    private MapManager manager;
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);

    public DebugRenderSystem(OrthographicCamera camera, MapManager manager){
        super(Family.all(CollisionComponent.class).get());
        this.camera = camera;
        this.manager = manager;
        this.renderer = new ShapeRenderer();
    }

    @Override
    public void update(float delta){
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        // Walls
        renderer.setColor(Color.CYAN);
        for(Rectangle wall : manager.getCollisionRects()){
            renderer.rect(wall.x, wall.y, wall.width, wall.height);
        }
        // Entities
        renderer.setColor(Color.RED);
        super.update(delta);
        renderer.end();
    }

    @Override
    protected void processEntity(Entity e, float delta){
        CollisionComponent col = cm.get(e);
        renderer.rect(col.bounds.x, col.bounds.y, col.bounds.width, col.bounds.height);
    }
}
