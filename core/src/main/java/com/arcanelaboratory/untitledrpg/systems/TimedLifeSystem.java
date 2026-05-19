package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.TimedExistenceComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class TimedLifeSystem extends IteratingSystem {
    ComponentMapper<TimedExistenceComponent> tem = ComponentMapper.getFor(TimedExistenceComponent.class);
    Engine engine;

    public TimedLifeSystem(Engine engine){
        super(Family.all(TimedExistenceComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity e, float delta){
        tem.get(e).existenceTime -= delta;
        if(tem.get(e).existenceTime <= 0){
            engine.removeEntity(e);
        }
    }
}
