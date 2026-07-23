package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class AttackComponent implements Component {
    public Entity source;
    public float damage;
    public Array<Entity> hitEntities = new Array<>();
}
