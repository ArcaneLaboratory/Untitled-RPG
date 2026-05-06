package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.arcanelaboratory.untitledrpg.components.VelocityComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class MovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    protected void processEntity(Entity e, float delta){
        TransformComponent pos = tm.get(e);
        VelocityComponent vel = vm.get(e);

        pos.x += vel.dx * delta;
        pos.y += vel.dy * delta;
    }
}
