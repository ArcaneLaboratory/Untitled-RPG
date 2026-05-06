package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class CollisionComponent implements Component, Pool.Poolable {
    public final Rectangle bounds = new Rectangle();
    public float width, height;
    public float offsetX, offsetY;

    @Override
    public void reset(){
        bounds.set(0,0,0,0);
        width = height = offsetX = offsetY = 0;
    }
}
