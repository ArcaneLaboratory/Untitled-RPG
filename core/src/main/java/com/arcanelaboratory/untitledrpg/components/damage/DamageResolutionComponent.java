package com.arcanelaboratory.untitledrpg.components.damage;

import com.badlogic.ashley.core.Component;

public class DamageResolutionComponent implements Component {
    public float damage;

    public DamageResolutionComponent(float damage){
        this.damage = damage;
    }
}
