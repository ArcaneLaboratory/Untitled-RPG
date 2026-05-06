package com.arcanelaboratory.untitledrpg.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MapManager {
    private TiledMap map;
    private Array<Rectangle> collisionRects = new Array<>();
    private Vector2 playerSpawn = new Vector2();
    private float unitScale = GlobalConstants.MAP_SCALE;

    public void loadCollisions(TiledMap tmap){
        this.map = tmap;
        collisionRects.clear();

        MapLayer collisions = map.getLayers().get("collisions");
        if(collisions != null){
            for(MapObject obj : collisions.getObjects()){
                if(obj instanceof RectangleMapObject){
                    Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                    collisionRects.add(new Rectangle(
                        rect.x * unitScale, rect.y * unitScale,
                        rect.width * unitScale, rect.height * unitScale
                    ));
                }
            }
        }else{
            System.out.println("NO COLLISIONS FOUND");
        }
    }

    public Array<Rectangle> getCollisionRects(){ return collisionRects;}
}
