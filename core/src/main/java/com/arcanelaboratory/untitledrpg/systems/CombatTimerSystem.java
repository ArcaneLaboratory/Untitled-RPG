package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CombatComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class CombatTimerSystem extends IteratingSystem {
    private ComponentMapper<CombatComponent> cm = ComponentMapper.getFor(CombatComponent.class);
    public CombatTimerSystem(){
        super(Family.all(CombatComponent.class).get());
    }
    @Override
    protected void processEntity(Entity e, float delta){
        CombatComponent combat = cm.get(e);
        if(combat.timer > 0){
            combat.timer -= delta;
            if(combat.timer < 0){
                combat.timer = 0f;
            }
        }
    }
}
