package com.arcanelaboratory.untitledrpg.components.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemyAIComponent implements Component {
    public EnemyState currentState = EnemyState.IDLE;
    public Entity targetEntity = null;

    public float sensorRadius = 5.0f; // Range at which they notice the player
    public float attackRadius = 2.0f; // Range at which they can swing a weapon

    // Cooldown for AI recalculations so they don't "think" every frame
    public float decisionTimer = 0f;
    public float decisionInterval = 0.1f; // Check every 100ms
    // The sequence of tile coordinates to follow to reach the player
    public Array<Vector2> currentPath = new Array<>();
    public int currentWaypointIndex = 0;
}
