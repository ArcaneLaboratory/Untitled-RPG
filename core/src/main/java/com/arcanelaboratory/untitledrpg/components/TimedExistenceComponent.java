package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimedExistenceComponent implements Component, Pool.Poolable {
    public float existenceTime;

    public TimedExistenceComponent (float time){
        this.existenceTime = time;
    }

    @Override
    public void reset(){
        existenceTime = 0f;
    }
}
