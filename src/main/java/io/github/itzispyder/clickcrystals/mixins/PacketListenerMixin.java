package io.github.itzispyder.clickcrystals.mixins;

import io.github.itzispyder.clickcrystals.events.events.EntityDeathEvent;
import io.github.itzispyder.clickcrystals.events.events.PacketReceiveEvent;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.itzispyder.clickcrystals.ClickCrystals.mc;
import static io.github.itzispyder.clickcrystals.ClickCrystals.system;

/**
 * Client connection mixin for sending and handling packets
 */
@Mixin(ClientConnection.class)
public abstract class PacketListenerMixin {

    @Inject(method = "send*", at = @At("HEAD"), cancellable = true)
    public void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static void onPacketRead(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        PacketReceiveEvent event = new PacketReceiveEvent(packet);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();

        if (packet instanceof EntityStatusS2CPacket statusPacket) {
            try {
                EntityDeathEvent deathEvent;
                if (mc.player == null) return;
                if (mc.player.getWorld() == null) return;
                ServerWorld serverWorld = mc.player.getServer().getWorld(mc.player.getWorld().getRegistryKey());
                if (serverWorld == null) return;
                Entity entity = statusPacket.getEntity(serverWorld);
                if (entity == null) return;
                switch (statusPacket.getStatus()) {
                    case 35 -> {
                        deathEvent = new EntityDeathEvent(entity,true);
                        system.eventBus.pass(deathEvent);
                    }
                    case 3 -> {
                        deathEvent = new EntityDeathEvent(entity,false);
                        system.eventBus.pass(deathEvent);
                    }
                }
            }
            catch (Exception ignore) {}
        }
    }
}
