package com.arcanelaboratory.untitledrpg.utils;

import com.arcanelaboratory.untitledrpg.components.VelocityComponent;

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
}
