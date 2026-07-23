package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.CollisionComponent;
import com.arcanelaboratory.untitledrpg.components.CombatComponent;
import com.arcanelaboratory.untitledrpg.components.TransformComponent;
import com.arcanelaboratory.untitledrpg.components.VelocityComponent;
import com.arcanelaboratory.untitledrpg.components.ai.EnemyAIComponent;
import com.arcanelaboratory.untitledrpg.components.stats.StatType;
import com.arcanelaboratory.untitledrpg.components.stats.StatsComponent;
import com.arcanelaboratory.untitledrpg.entity.EntityFactory;
import com.arcanelaboratory.untitledrpg.utils.AStarPathfinder;
import com.arcanelaboratory.untitledrpg.utils.MoveUtils;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.arcanelaboratory.untitledrpg.components.ai.EnemyState;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class EnemyControlSystem extends IteratingSystem {
    public ComponentMapper<EnemyAIComponent> aim = ComponentMapper.getFor(EnemyAIComponent.class);
    public ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    public ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    public ComponentMapper<CombatComponent> cm = ComponentMapper.getFor(CombatComponent.class);
    public ComponentMapper<CollisionComponent> com = ComponentMapper.getFor(CollisionComponent.class);
    public ComponentMapper<StatsComponent> sm = ComponentMapper.getFor(StatsComponent.class);
    private Entity playerEntity;
    private EntityFactory factory;
    private AStarPathfinder aStar;

    public EnemyControlSystem(Entity playerEntity, EntityFactory factory, AStarPathfinder aStar){
        super(Family.all(EnemyAIComponent.class).get());
        this.playerEntity = playerEntity;
        this.factory = factory;
        this.aStar = aStar;
    }

    @Override
    protected void processEntity(Entity e, float delta){
        EnemyAIComponent ai = aim.get(e);
        CombatComponent combat = cm.get(e);
        CollisionComponent col = com.get(e);
        StatsComponent stats = sm.get(e);

        // Optimization: Only think 5 times a second, not 60
        ai.decisionTimer += delta;
        if (ai.decisionTimer < ai.decisionInterval) return;
        ai.decisionTimer = 0f;

        TransformComponent enemyPos = tm.get(e);
        TransformComponent playerPos = tm.get(playerEntity);
        VelocityComponent vel = vm.get(e);

        float distance = MoveUtils.distance(enemyPos, playerPos); // Distance math
        float facing = MoveUtils.direction(enemyPos, playerPos);

        // The FSM Logic Gate
        switch (ai.currentState) {
            case IDLE:
                vel.dx = 0;
                vel.dy = 0;
                if (distance <= ai.sensorRadius) ai.currentState = EnemyState.CHASE;
                break;

            case CHASE:
                // --- STEP 1: Path Refresh ---
                // Convert world coordinates to discrete tile coordinates
                int startX = MathUtils.floor(enemyPos.x);
                int startY = MathUtils.floor(enemyPos.y);
                int targetX = MathUtils.floor(playerPos.x);
                int targetY = MathUtils.floor(playerPos.y);

                // Calculate and cache the new path
                ai.currentPath = aStar.findPath(startX, startY, targetX, targetY);
                ai.currentWaypointIndex = 0;

                // --- STEP 2: Path Following ---
                if (ai.currentPath != null && ai.currentPath.size > 0) {
                    // If we haven't reached the end of the breadcrumbs...
                    Vector2 waypoint = ai.currentPath.get(ai.currentWaypointIndex);

                    float enemyCenterX = enemyPos.x + (col.bounds.width / 2f);
                    float enemyCenterY = enemyPos.y + (col.bounds.height / 2f);

                    float targetWorldX = waypoint.x;
                    float targetWorldY = waypoint.y;

                    float distanceToWaypoint = MoveUtils.distance(enemyCenterX, enemyCenterY, targetWorldX, targetWorldY);
                    float direction = MathUtils.atan2(targetWorldY - enemyPos.y, targetWorldX - enemyPos.x);

                    // If we are close enough to the waypoint (e.g., 0.1 world units), switch to next node
                    if (distanceToWaypoint < 0.1f) {
                        ai.currentWaypointIndex++;
                    } else {
                        // Otherwise, keep steering toward this node
                        vel.set(MathUtils.cos(direction) * stats.baseStats.get(StatType.SPEED), MathUtils.sin(direction) * stats.baseStats.get(StatType.SPEED));
                    }
                } else {
                    // Fallback: If no path found (player trapped behind walls), stand still
                    vel.set(0, 0);
                }

                // Check if close enough to transition to ATTACK state
                if (distance <= ai.attackRadius) {
                    ai.currentState = EnemyState.ATTACK;
                }
                break;
                /*
                if (distance > ai.sensorRadius * 1.5f) { // Lose aggro range
                    ai.currentState = EnemyState.IDLE;
                } else if (distance <= ai.attackRadius) {
                    ai.currentState = EnemyState.ATTACK;
                } else {
                    // Head towards player (Beeline method)
                    if(playerPos.x > enemyPos.x){
                        vel.dx = 1;
                    }else{
                        vel.dx = -1;
                    }
                    if(playerPos.y > enemyPos.y){
                        vel.dy = 1;
                    }else{
                        vel.dy = -1;
                    }
                }
                break;
                 */

            case ATTACK:
                vel.dx = 0;
                vel.dy = 0;
                if (distance > ai.attackRadius) {
                    ai.currentState = EnemyState.CHASE;
                } else if(combat.canAttack()){
                    float reach = 1.0f; //TODO: data driven
                    float xPos = enemyPos.x + MathUtils.cos(facing) * reach;
                    float yPos = enemyPos.y + MathUtils.sin(facing) * reach;

                    factory.createAttack(xPos, yPos, 0.2f, e, 10, facing); //TODO: data driven
                    combat.timer = combat.attackDelay;
                }
                break;
        }
    }
}
