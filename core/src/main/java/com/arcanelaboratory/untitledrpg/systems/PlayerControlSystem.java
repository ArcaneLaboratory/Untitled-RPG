package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.PlayerComponent;
import com.arcanelaboratory.untitledrpg.components.stats.StatType;
import com.arcanelaboratory.untitledrpg.components.stats.StatsComponent;
import com.arcanelaboratory.untitledrpg.components.VelocityComponent;
import com.arcanelaboratory.untitledrpg.utils.MoveUtils;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerControlSystem extends IteratingSystem {
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<StatsComponent> sm = ComponentMapper.getFor(StatsComponent.class);

    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class, VelocityComponent.class).get());
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent vel = vm.get(entity);
        StatsComponent stats = sm.get(entity);

        // Get speed from our generalized StatType system
        float speed = stats.baseStats.get(StatType.SPEED);

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveY -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveX += 1;

        MoveUtils.normalizeVelocity(vel, moveX, moveY, speed);
    }
}
