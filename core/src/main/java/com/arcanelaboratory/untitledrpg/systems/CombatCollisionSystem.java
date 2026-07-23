package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.AttackComponent;
import com.arcanelaboratory.untitledrpg.components.CollisionComponent;
import com.arcanelaboratory.untitledrpg.components.damage.DamageResolutionComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;

public class CombatCollisionSystem extends IteratingSystem {
    private ComponentMapper<AttackComponent> am = ComponentMapper.getFor(AttackComponent.class);
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private ImmutableArray<Entity> victims;

    public CombatCollisionSystem(){
        super(Family.all(AttackComponent.class, CollisionComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine){
        super.addedToEngine(engine);
        victims = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity e, float delta){
        AttackComponent attack = am.get(e);
        Rectangle attackBounds = cm.get(e).bounds;

        for(Entity victim : victims){
            if(victim == attack.source) continue;
            if(attack.hitEntities.contains(victim, true)) continue;
            Rectangle victimBounds = cm.get(victim).bounds;
            if(attackBounds.overlaps(victimBounds)){
                attack.hitEntities.add(victim);
                victim.add(new DamageResolutionComponent(attack.damage));
            }
        }
    }
}
