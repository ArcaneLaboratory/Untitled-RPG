package com.arcanelaboratory.untitledrpg.entity;

import com.arcanelaboratory.untitledrpg.components.*;
import com.arcanelaboratory.untitledrpg.components.stats.StatType;
import com.arcanelaboratory.untitledrpg.components.stats.StatsComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFactory {
    private Engine engine;
    private DataLibrary library;
    private TextureAtlas atlas;

    public EntityFactory(Engine engine, DataLibrary library, TextureAtlas atlas){
        this.engine = engine;
        this.library = library;
        this.atlas = atlas;
    }

    public Entity createEnemy(String enemyType, float x, float y){
        EnemyTemplate template = library.getEnemy(enemyType);
        Entity e = engine.createEntity();
        TransformComponent pos = engine.createComponent(TransformComponent.class);
        pos.x = x;
        pos.y = y;
        e.add(pos);
        CollisionComponent col = engine.createComponent(CollisionComponent.class);
        col.bounds.set(0, 0, template.sizeX, template.sizeY);
        e.add(col);
        VelocityComponent vel = engine.createComponent(VelocityComponent.class);
        e.add(vel);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        TextureAtlas.AtlasRegion region = atlas.findRegion(template.regionName);
        if (region == null) {
            Gdx.app.error("Factory", "Could not find texture region: " + template.regionName);
        } else {
            tex.region = region;
        }
        e.add(tex);
        StatsComponent stats = engine.createComponent(StatsComponent.class);
        for(String s : template.startingStats.keys()){
            stats.baseStats.put(StatType.valueOf(s), template.startingStats.get(s));
        }
        e.add(stats);
        HealthComponent hp = engine.createComponent(HealthComponent.class);
        hp.init(stats.baseStats.get(StatType.HEALTH));
        e.add(hp);
        return e;
    }

    public Entity createPlayer(String playerType, float x, float y){
        PlayerTemplate template = library.getPlayer(playerType);
        Entity e = engine.createEntity();
        TransformComponent pos = engine.createComponent(TransformComponent.class);
        pos.x = x;
        pos.y = y;
        e.add(pos);
        CollisionComponent col = engine.createComponent(CollisionComponent.class);
        col.bounds.set(0, 0, 1, 1);
        e.add(col);
        VelocityComponent vel = engine.createComponent(VelocityComponent.class);
        e.add(vel);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        TextureAtlas.AtlasRegion region = atlas.findRegion(template.regionName);
        if (region == null) {
            Gdx.app.error("Factory", "Could not find texture region: " + template.regionName);
        } else {
            tex.region = region;
        }
        e.add(tex);
        StatsComponent stats = engine.createComponent(StatsComponent.class);
        for(String s : template.startingStats.keys()){
            stats.baseStats.put(StatType.valueOf(s), template.startingStats.get(s));
        }
        e.add(stats);
        HealthComponent hp = engine.createComponent(HealthComponent.class);
        hp.init(stats.baseStats.get(StatType.HEALTH));
        e.add(hp);
        ManaComponent mana = engine.createComponent(ManaComponent.class);
        mana.init(stats.baseStats.get(StatType.MAX_MANA));
        e.add(mana);
        e.add(engine.createComponent(PlayerComponent.class));
        //engine.addEntity(e);
        return e;
    }

    public Entity createSlash(float x, float y, float lifeTime){
        Entity e = engine.createEntity();
        TransformComponent pos = engine.createComponent(TransformComponent.class);
        pos.x = x;
        pos.y = y;
        e.add(pos);
        return e;
    }
}
