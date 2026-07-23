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

    private boolean[][] blockedGrid;
    private int mapWidthTiles;
    private int mapHeightTiles;
    private float worldTileWidth;
    private float worldTileHeight;

    public void loadCollisions(TiledMap tmap){
        this.map = tmap;
        collisionRects.clear();

        mapWidthTiles = map.getProperties().get("width", Integer.class);
        mapHeightTiles = map.getProperties().get("height", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        worldTileWidth = tileWidth * unitScale;
        worldTileHeight = tileHeight * unitScale;

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
        bakeNavigationGrid();
    }

    private void bakeNavigationGrid() {
        blockedGrid = new boolean[mapWidthTiles][mapHeightTiles];
        Rectangle tileBounds = new Rectangle(0, 0, worldTileWidth, worldTileHeight);

        // Check every single tile coordinate on the map
        for (int x = 0; x < mapWidthTiles; x++) {
            for (int y = 0; y < mapHeightTiles; y++) {
                // Project this tile coordinate into World Space positions
                tileBounds.setPosition(x * worldTileWidth, y * worldTileHeight);

                // If this tile overlaps ANY collision shape, mark it blocked
                for (Rectangle rect : collisionRects) {
                    if (rect.overlaps(tileBounds)) {
                        blockedGrid[x][y] = true;
                        break;
                    }
                }
            }
        }
    }

    public boolean isCellBlocked(int x, int y) {
        if (x < 0 || x >= mapWidthTiles || y < 0 || y >= mapHeightTiles) return true; // Out of bounds is blocked
        return blockedGrid[x][y];
    }

    public Array<Rectangle> getCollisionRects(){ return collisionRects;}

    public int getMapHeightTiles() {
        return mapHeightTiles;
    }

    public int getMapWidthTiles() {
        return mapWidthTiles;
    }
}
