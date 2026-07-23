package com.arcanelaboratory.untitledrpg.utils;

import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.arcanelaboratory.untitledrpg.components.VelocityComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MoveUtils {

    public static void normalizeVelocity(VelocityComponent vel, float moveX, float moveY, float speed){
        if (moveX != 0 || moveY != 0) {
            float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
            vel.dx = (moveX / length) * speed;
            vel.dy = (moveY / length) * speed;
        } else {
            vel.dx = 0;
            vel.dy = 0;
        }
    }
    public static float distance(TransformComponent first, TransformComponent second){
        return (float) Math.sqrt(((first.x - second.x) * (first.x - second.x)) + ((first.y - second.y) * (first.y - second.y)));
    }
    public static float distance(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }
    public static float direction(TransformComponent first, TransformComponent second){
        return MathUtils.atan2(second.y - first.y, second.x - first.x);
    }
}
