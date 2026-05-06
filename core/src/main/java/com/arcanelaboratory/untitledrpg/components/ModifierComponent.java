package com.arcanelaboratory.untitledrpg.components;

import com.arcanelaboratory.untitledrpg.components.stats.StatModifier;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

public class ModifierComponent implements Component {
    public Array<StatModifier> modifiers = new Array<>();
}
