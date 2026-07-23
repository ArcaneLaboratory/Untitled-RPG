package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.*;
import com.arcanelaboratory.untitledrpg.components.stats.StatType;
import com.arcanelaboratory.untitledrpg.components.stats.StatsComponent;
import com.arcanelaboratory.untitledrpg.entity.EntityFactory;
import com.arcanelaboratory.untitledrpg.utils.MoveUtils;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class PlayerControlSystem extends IteratingSystem {
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<StatsComponent> sm = ComponentMapper.getFor(StatsComponent.class);
    private ComponentMapper<FacingComponent> fm = ComponentMapper.getFor(FacingComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<CombatComponent> cm = ComponentMapper.getFor(CombatComponent.class);

    private EntityFactory factory;
    private OrthographicCamera camera;
    private Vector3 mouseVector = new Vector3();

    public PlayerControlSystem(OrthographicCamera camera, EntityFactory factory) {
        super(Family.all(PlayerComponent.class, VelocityComponent.class).get());
        this.camera = camera;
        this.factory = factory;
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent vel = vm.get(entity);
        StatsComponent stats = sm.get(entity);
        FacingComponent facing = fm.get(entity);
        TransformComponent pos = tm.get(entity);

        // Get speed from our generalized StatType system
        float speed = stats.baseStats.get(StatType.SPEED);
        float attackDelay = stats.baseStats.get(StatType.ATTACK_SPEED);

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveY -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveX += 1;

        MoveUtils.normalizeVelocity(vel, moveX, moveY, speed);

        mouseVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseVector);

        float distanceX = mouseVector.x - (pos.x + 0.5f);
        float distanceY = mouseVector.y - (pos.y + 0.5f);

        facing.angle = MathUtils.atan2(distanceY, distanceX);

        CombatComponent combat = cm.get(entity);
        combat.attackDelay = attackDelay;
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(combat.canAttack()){
                mainAttack(entity, pos, facing.angle);
                combat.timer = combat.attackDelay;
            }else{
                //System.out.println("Can't attack right now");
            }
        }
    }

    private void mainAttack(Entity player, TransformComponent position, float angle){
        float reach = 1.0f; //TODO: data driven
        float xPos = position.x + MathUtils.cos(angle) * reach;
        float yPos = position.y + MathUtils.sin(angle) * reach;

        factory.createAttack(xPos, yPos, 0.2f, player, 10, angle); //TODO: data driven
//        System.out.println("Spawned attack at" + xPos + ", " + yPos);
    }
}
