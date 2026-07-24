package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.*;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDRenderSystem extends IteratingSystem {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Table table;
    private float health = 0f;
    private float mana = 0f;
    private int kills = 0;

    private Label healthLabel;
    private Label manaLabel;
    private Label killsLabel;

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<ManaComponent> mm = ComponentMapper.getFor(ManaComponent.class);
    private ComponentMapper<CombatComponent> cm = ComponentMapper.getFor(CombatComponent.class);

    public HUDRenderSystem(SpriteBatch batch){
        super(Family.all(PlayerComponent.class, HealthComponent.class).get());
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(atlas);

        table = new Table();
        table.top();
        table.setFillParent(true);

        healthLabel = new Label("HP: " + health, new Label.LabelStyle(new BitmapFont(), Color.RED));
        healthLabel.setFontScale(2f);
        killsLabel = new Label("Mana: " + mana, new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        killsLabel.setFontScale(2f);
        manaLabel = new Label("Mana: " + mana, new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        manaLabel.setFontScale(2f);
    }

    @Override
    public void update(float deltaTime) {
        stage.clear();
        table.clear();

        healthLabel.setText("HP: " + health);
        manaLabel.setText("Mana: " + mana);
        killsLabel.setText("Kills: " + kills);

        table.add(healthLabel).expandX().padTop(10);
        table.add(killsLabel).expandX().padTop(10);
        table.add(manaLabel).expandX().padTop(10);

//        Image testImage = new Image(new Texture(new FileHandle("ui/testbar.png")));
//        table.bottom();
//        table.add(testImage).bottom();

        stage.addActor(table);
        stage.draw();
        super.update(deltaTime);
        stage.dispose();

    }

    @Override
    protected void processEntity(Entity e, float delta){
        health = hm.get(e).current;
        mana = mm.get(e).current;
        kills = cm.get(e).kills;
    }
}
