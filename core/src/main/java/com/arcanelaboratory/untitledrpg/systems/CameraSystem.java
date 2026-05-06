package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.PlayerComponent;
import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    private float smoothing = 0.2f;

    public CameraSystem(OrthographicCamera camera){
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity e, float delta){
        TransformComponent target = tm.get(e);

        camera.position.x += (target.x - camera.position.x) * smoothing;
        camera.position.y += (target.y - camera.position.y) * smoothing;

        camera.update();
    }
}
