package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CollisionComponent;
import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private Array<Rectangle> wallRects;

    public CollisionSystem(Array<Rectangle> walls){
        super(Family.all(TransformComponent.class, CollisionComponent.class).get());
        this.wallRects = walls;
    }

    @Override
    protected void processEntity(Entity e, float delta){
        TransformComponent pos = tm.get(e);
        CollisionComponent col = cm.get(e);

        col.bounds.setPosition(pos.x + col.offsetX, pos.y + col.offsetY);
        for(Rectangle wall : wallRects){
            if(col.bounds.overlaps(wall)){
                resolveCollision(pos, col, wall);
            }
        }
    }

    private void resolveCollision(TransformComponent pos, CollisionComponent col, Rectangle wall){
        float overlapX, overlapY;

        float entityCenterX = col.bounds.x + col.bounds.width/2;
        float entityCenterY = col.bounds.y + col.bounds.height/2;
        float wallCenterX = wall.x + wall.width/2;
        float wallCenterY = wall.y + wall.height/2;

        if(entityCenterX < wallCenterX){
            overlapX = (col.bounds.x + col.bounds.width) - wall.x;
        }else{
            overlapX = (wall.x + wall.width) - col.bounds.x;
        }
        if(entityCenterY < wallCenterY){
            overlapY = (col.bounds.y + col.bounds.height) - wall.y;
        }else{
            overlapY = (wall.y + wall.height) - col.bounds.y;
        }

        // Resolve along the axis with the SMALLEST overlap
        if(overlapX < overlapY){
            if(entityCenterX < wallCenterX){
                pos.x -= overlapX;
            }else{
                pos.x += overlapX;
            }
        }else{
            if(entityCenterY < wallCenterY){
                pos.y -= overlapY;
            }else{
                pos.y += overlapY;
            }
        }
        col.bounds.setPosition(pos.x + col.offsetX, pos.y + col.offsetY);
    }
}
