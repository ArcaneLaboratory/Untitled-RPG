package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;

public class CombatComponent implements Component {
    public float attackDelay = 0.5f;
    public float timer = 0f;
    public int kills = 0;

    public boolean canAttack(){
        return timer <= 0f;
    }
}
