package io.github.itzispyder.clickcrystals.modules.modules.crystaling;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.EventPriority;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

public class SwordBlock extends Module implements Listener {

    public SwordBlock() {
        super("SwordBlocking", Categories.MISC, "Block like 1.8");
    }

    @Override
    protected void onEnable() {
        system.addListener(this);
    }

    @Override
    protected void onDisable() {
        system.removeListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onClick(PacketSendEvent e) {
        if (e.getPacket() instanceof PlayerInteractItemC2SPacket packet) {
            if (!holdingWeapon()) return;
            HotbarUtils.search(Items.SHIELD);
        }
    }

    private boolean holdingWeapon() {
        return HotbarUtils.nameContains("sword") || (!HotbarUtils.nameContains("pickaxe") && HotbarUtils.nameContains("axe"));
    }
}
