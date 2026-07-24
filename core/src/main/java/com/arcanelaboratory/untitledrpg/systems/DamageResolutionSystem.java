package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CombatComponent;
import com.arcanelaboratory.untitledrpg.components.HealthComponent;
import com.arcanelaboratory.untitledrpg.components.damage.DamageResolutionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class DamageResolutionSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<DamageResolutionComponent> drm = ComponentMapper.getFor(DamageResolutionComponent.class);
    private Engine engine;

    public DamageResolutionSystem(Engine engine){
        super(Family.all(DamageResolutionComponent.class, HealthComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity e, float delta){
        HealthComponent health = hm.get(e);
        DamageResolutionComponent damage = drm.get(e);
        if(health.invulnerableTimer == 0f){
//            System.out.println("Attack resolved!");
            health.current -= damage.damage;
            if(health.current <= 0){
                engine.removeEntity(e); //TODO: more complicated death logic, perhaps in new system
                damage.source.getComponent(CombatComponent.class).kills++;
            }
            e.remove(DamageResolutionComponent.class);
            health.invulnerableTimer = 0.2f;
        }
    }
}
