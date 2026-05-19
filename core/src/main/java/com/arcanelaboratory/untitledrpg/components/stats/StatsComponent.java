package com.arcanelaboratory.untitledrpg.components.stats;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import java.util.EnumMap;

public class StatsComponent implements Component, Pool.Poolable {
    public EnumMap<StatType, Float> baseStats = new EnumMap<>(StatType.class);

//    public StatsComponent set(String type, float value){
//        baseStats.put(StatType.valueOf(type), value);
//        return this;
//    }

    @Override
    public void reset(){
        baseStats.clear();
    }
}
