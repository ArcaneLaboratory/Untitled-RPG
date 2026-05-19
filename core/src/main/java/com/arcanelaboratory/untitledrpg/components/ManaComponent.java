package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;

public class ManaComponent implements Component {
    public float current;
    public float max;

    public void init(float initialMana){
        current = initialMana;
        max = initialMana;
    }
}
