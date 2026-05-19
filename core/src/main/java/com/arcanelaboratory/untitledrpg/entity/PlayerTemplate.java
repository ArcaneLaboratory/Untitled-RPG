package com.arcanelaboratory.untitledrpg.entity;

import com.arcanelaboratory.untitledrpg.components.stats.StatType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class PlayerTemplate {
    public String className;
    public String texturePath;
    public String regionName;
    public ObjectMap<String, Float> startingStats;
    //public Array<String> startingItems;
}
