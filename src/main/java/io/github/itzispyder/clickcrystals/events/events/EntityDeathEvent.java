package io.github.itzispyder.clickcrystals.events.events;

import io.github.itzispyder.clickcrystals.events.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class EntityDeathEvent extends Event {
    private final boolean totemPopped;
    private final Entity entity;

    public EntityDeathEvent(Entity entity, boolean totemPopped) {
        this.entity = entity;
        this.totemPopped = totemPopped;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isTotemPopped() {
        return totemPopped;
    }

    public boolean isDeath() {
        return !totemPopped;
    }

    public BlockPos getLocation() {
        return entity.getBlockPos();
    }
}
