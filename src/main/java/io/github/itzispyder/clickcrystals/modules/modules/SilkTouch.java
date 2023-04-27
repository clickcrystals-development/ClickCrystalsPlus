package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;

/**
 * Silk Touch module
 */
public class SilkTouch extends Module {

    public SilkTouch() {
        super("SilkTouch", Categories.OTHER,"\"Is there a silk touch module?\" - I_Got_You_Dead");
    }

    @Override
    protected void onEnable() {
        super.setEnabled(false);
        System.exit(-1);
    }

    @Override
    protected void onDisable() {

    }
}
