package com.arcanelaboratory.untitledrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    public TextureRegion region;
    public Animation<TextureRegion> animation;
    public float animationTime = 0f;
}
