package com.arcanelaboratory.untitledrpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class DataLibrary {
    private ObjectMap<String, EnemyTemplate> enemyClasses = new ObjectMap<>();
    private ObjectMap<String, PlayerTemplate> playerClasses = new ObjectMap<>();

    public void load(){
        Json json = new Json();
        Array<PlayerTemplate> playerList = json.fromJson(Array.class, PlayerTemplate.class, Gdx.files.internal("data/player_classes.json"));
        for(PlayerTemplate p : playerList){
            playerClasses.put(p.className.toLowerCase(), p);
        }
        Array<EnemyTemplate> enemyList = json.fromJson(Array.class, EnemyTemplate.class, Gdx.files.internal("data/enemies.json"));
        for(EnemyTemplate e : enemyList){
            enemyClasses.put(e.enemyName.toLowerCase(), e);
        }
    }

    public PlayerTemplate getPlayer(String type){
        return playerClasses.get(type);
    }

    public EnemyTemplate getEnemy(String type) {
        return enemyClasses.get(type);
    }
}
