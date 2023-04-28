package io.github.itzispyder.clickcrystals;

import io.github.itzispyder.clickcrystals.client.ClickCrystalsSystem;
import io.github.itzispyder.clickcrystals.commands.commands.*;
import io.github.itzispyder.clickcrystals.data.Configuration;
import io.github.itzispyder.clickcrystals.events.events.ClientTickEndEvent;
import io.github.itzispyder.clickcrystals.events.events.ClientTickStartEvent;
import io.github.itzispyder.clickcrystals.events.events.PlayerAttackEntityEvent;
import io.github.itzispyder.clickcrystals.events.listeners.ChatEventListener;
import io.github.itzispyder.clickcrystals.gui.hud.ClickCrystalIconHud;
import io.github.itzispyder.clickcrystals.gui.hud.ClickPerSecondHud;
import io.github.itzispyder.clickcrystals.gui.hud.ModuleListTextHud;
import io.github.itzispyder.clickcrystals.gui.screens.ClickCrystalMenuScreen;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.modules.modules.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

import java.io.File;

/**
 * ClickCrystals main
 */
public final class ClickCrystals implements ModInitializer {

    public static final File configFile = new File("ClickCrystalsClient/game_config.dat");
    public static final Configuration config = Configuration.load(configFile) != null ? Configuration.load(configFile) : new Configuration(configFile);
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final ClickCrystalsSystem system = new ClickCrystalsSystem();
    public static final ClickCrystalMenuScreen mainMenu = new ClickCrystalMenuScreen();

    @SuppressWarnings("unused")
    public static final String modId = "clickcrystals", prefix = "[ClickCrystals] ", starter = "§7[§bClick§3Crystals§7] §r";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        // Mod initialization
        System.out.println(prefix + "Loading ClickCrystals by ImproperIssues");
        this.startTicking();
        this.init();

    }

    /**
     * Start click tick events
     */
    public void startTicking() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            ClientTickStartEvent event = new ClientTickStartEvent();
            system.eventBus.pass(event);
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientTickEndEvent event = new ClientTickEndEvent();
            system.eventBus.pass(event);
        });
        AttackEntityCallback.EVENT.register((player, world, hand, ent, hitResult) -> {
            PlayerAttackEntityEvent event = new PlayerAttackEntityEvent(player, world, hand, ent, hitResult);
            system.eventBus.pass(event);
            return ActionResult.PASS;
        });
    }

    public void init() {
        // Listeners
        system.addListener(new ChatEventListener());

        // Commands
        system.addCommand(new ClickCrystalToggleCommand());
        system.addCommand(new GmcCommand());
        system.addCommand(new GmsCommand());
        system.addCommand(new GmaCommand());
        system.addCommand(new GmspCommand());
        system.addCommand(new OptOutCommand());
        system.addCommand(new SetPMCommand());
        system.addCommand(new SetAutoTotemDelayCommand());
        system.addCommand(new DebugModeCommand());
        system.addCommand(new SetChargeDelayCommand());
        system.addCommand(new SetExplodeDelayCommand());

        // Module
        system.addModule(new ClickCrystal());
        system.addModule(new Anchor2Glowstone());
        system.addModule(new Sword2Pearl());
        system.addModule(new BlockDelayRemover());
        system.addModule(new FullBright());
        system.addModule(new NoHurtCam());
        system.addModule(new SlowHandSwing());
        system.addModule(new TrueSight());
        system.addModule(new NoLoadingScreen());
        system.addModule(new NoGameOverlay());
        system.addModule(new AutoDTap());
        system.addModule(new Sword2Crystal());
        system.addModule(new NoServerParticles());
        system.addModule(new Totem2Pearl());
        system.addModule(new NoResourcePack());
        system.addModule(new ToolSwitcher());
        system.addModule(new Crystal2Anchor());
        system.addModule(new IconHud());
        system.addModule(new ModuleListHud());
        system.addModule(new SilkTouch());
        system.addModule(new PopCounter());
        system.addModule(new AutoEZ());
        system.addModule(new CWCrystal());
        system.addModule(new AutoCharge());
        system.addModule(new AntiWeakness());
        system.addModule(new AutoTotem());
        system.addModule(new AutoCope());
        system.addModule(new AutoPM());
        system.addModule(new ForceSword());
        system.addModule(new InstaAnchor());
        system.addModule(new CrystalPerSecondHud());
        Module.loadConfigModules();

        // Hud
        system.addHud(new ClickCrystalIconHud());
        system.addHud(new ModuleListTextHud());
        system.addHud(new ClickPerSecondHud());
    }

    public void initRpc() {

    }
}
