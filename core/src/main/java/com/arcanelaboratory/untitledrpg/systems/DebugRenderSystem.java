package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CollisionComponent;
import com.arcanelaboratory.untitledrpg.components.FacingComponent;
import com.arcanelaboratory.untitledrpg.components.HealthComponent;
import com.arcanelaboratory.untitledrpg.utils.MapManager;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DebugRenderSystem extends IteratingSystem {
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    private MapManager manager;
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<FacingComponent> fm = ComponentMapper.getFor(FacingComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public DebugRenderSystem(OrthographicCamera camera, MapManager manager){
        super(Family.all(CollisionComponent.class).get());
        this.camera = camera;
        this.manager = manager;
        this.renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
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
        super.update(delta);
        renderer.end();
    }

    @Override
    protected void processEntity(Entity e, float delta){
        renderer.setColor(Color.RED);
        CollisionComponent col = cm.get(e);
        renderer.rect(col.bounds.x, col.bounds.y, col.bounds.width, col.bounds.height);
        renderer.setColor(Color.WHITE);
        FacingComponent facing = fm.get(e);
        if(facing != null){
            float startX = col.bounds.x + col.bounds.width/2;
            float startY = col.bounds.y + col.bounds.height/2;
            renderer.line(startX, startY, startX + MathUtils.cos(facing.angle), startY + MathUtils.sin(facing.angle));
        }
        HealthComponent health = hm.get(e);
        if(health != null){
            float healthAmount = health.current / health.max;
            renderer.setColor(Color.GRAY);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(col.bounds.x, col.bounds.y + col.bounds.height + 0.1f, col.bounds.width, 0.1f);
            renderer.setColor(Color.RED);
            renderer.rect(col.bounds.x, col.bounds.y + col.bounds.height + 0.1f, col.bounds.width * healthAmount, 0.1f);
            renderer.set(ShapeRenderer.ShapeType.Line);
        }
    }
}
