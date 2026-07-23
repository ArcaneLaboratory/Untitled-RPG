package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class VelocityComponent implements Component, Pool.Poolable {
    public float dx = 0;
    public float dy = 0;

    public void set(float x, float y){
        dx = x; dy = y;
    }

    @Override
    public void reset(){
        dx = 0; dy = 0;
    }
}
