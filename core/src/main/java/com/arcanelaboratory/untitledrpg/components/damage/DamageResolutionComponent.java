package com.arcanelaboratory.untitledrpg.components.damage;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class DamageResolutionComponent implements Component {
    public float damage;
    public Entity source;

    public DamageResolutionComponent(float damage, Entity source){
        this.damage = damage;
        this.source = source;
    }
}
