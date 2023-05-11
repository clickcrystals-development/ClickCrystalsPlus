package io.github.itzispyder.clickcrystals.modules;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static io.github.itzispyder.clickcrystals.ClickCrystals.MOD_ID;

public abstract class Categories {

    public static final Category CRYSTALLING = new Category("Crystalling");
    public static final Category ANCHORING = new Category("Anchoring");
    public static final Category MISC = new Category("Misc");
    public static final Category CHAT = new Category("Chat");
    public static final Category OPTIMIZATION = new Category("Optimization");
    public static final Category RENDERING = new Category("Rendering");
    private static final Map<String,Category> categories = new HashMap<>();
    static {
        categories.put("Crystalling",CRYSTALLING);
        categories.put("Anchoring",ANCHORING);
        categories.put("Rendering", RENDERING);
        categories.put("Misc",MISC);
        categories.put("Chat",CHAT);
        categories.put("Optimization",OPTIMIZATION);
    }

    public static Map<String, Category> getCategories() {
        return new HashMap<>(categories);
    }

    public static void forEach(Consumer<Category> consumer) {
        for (Category category : getCategories().values()) consumer.accept(category);
    }
}
