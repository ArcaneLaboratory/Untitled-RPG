package com.arcanelaboratory.untitledrpg.components.stats;

public class StatModifier {
    public StatType stat;
    public ModifierType type;
    public float amount;
    public float duration; // -1 for permanent effects
    public String sourceID;
}
