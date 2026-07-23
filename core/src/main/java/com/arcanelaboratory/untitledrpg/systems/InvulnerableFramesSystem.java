package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.HealthComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class InvulnerableFramesSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public InvulnerableFramesSystem(){
        super(Family.all(HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity e, float delta){
        HealthComponent health = hm.get(e);
        if(health.invulnerableTimer > 0f){
            health.invulnerableTimer -= delta;
        }
        if(health.invulnerableTimer < 0f){
            health.invulnerableTimer = 0f;
        }
    }
}
