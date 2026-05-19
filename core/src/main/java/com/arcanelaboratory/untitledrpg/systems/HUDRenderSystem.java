package com.arcanelaboratory.untitledrpg.systems;

import com.arcanelaboratory.untitledrpg.components.HealthComponent;
import com.arcanelaboratory.untitledrpg.components.ManaComponent;
import com.arcanelaboratory.untitledrpg.components.PlayerComponent;
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

    private Label healthLabel;
    private Label manaLabel;

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<ManaComponent> mm = ComponentMapper.getFor(ManaComponent.class);

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
        manaLabel = new Label("Mana: " + mana, new Label.LabelStyle(new BitmapFont(), Color.CYAN));
    }

    @Override
    public void update(float deltaTime) {
        stage.clear();
        table.clear();

        healthLabel.setText("HP: " + health);
        manaLabel.setText("Mana: " + mana);

        table.add(healthLabel).expandX().padTop(10);
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
    }
}
