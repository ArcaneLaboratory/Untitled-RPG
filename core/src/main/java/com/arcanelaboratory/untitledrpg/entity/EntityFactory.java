package com.arcanelaboratory.untitledrpg.entity;

import com.arcanelaboratory.untitledrpg.components.*;
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
        e.add(engine.createComponent(PlayerComponent.class));
        engine.addEntity(e);
        return e;
    }
}
