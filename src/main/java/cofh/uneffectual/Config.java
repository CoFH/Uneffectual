package cofh.uneffectual;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static net.neoforged.fml.config.ModConfig.Type.CLIENT;

public class Config {

    private static boolean registered = false;

    public static void register(IEventBus modEventBus) {

        if (registered) {
            return;
        }
        modEventBus.register(Config.class);
        registered = true;

        genClientConfig();

        ModLoadingContext.get().registerConfig(CLIENT, clientSpec);
    }

    private Config() {

    }

    // region CONFIG SPEC
    private static final ModConfigSpec.Builder CLIENT_CONFIG = new ModConfigSpec.Builder();
    private static ModConfigSpec clientSpec;

    private static void genClientConfig() {

        effects = CLIENT_CONFIG
                .comment("The list of effect icons to hide. For example, 'minecraft:night_vision' or 'cofh_core:chilled'")
                .define("Hidden Effects", new ArrayList<>());

        clientSpec = CLIENT_CONFIG.build();
    }

    public static List<String> getEffects() {

        return effects == null ? Collections.emptyList() : effects.get();
    }

    private static Supplier<List<String>> effects;

}