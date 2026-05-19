package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.ModifierComponent;
import com.arcanelaboratory.untitledrpg.components.stats.StatsComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class StatModifierSystem extends IteratingSystem {
    ComponentMapper<StatsComponent> sm = ComponentMapper.getFor(StatsComponent.class);

    public StatModifierSystem(){
        super(Family.all(StatsComponent.class, ModifierComponent.class).get());
    }

    @Override
    protected void processEntity(Entity e, float delta){

    }
}
