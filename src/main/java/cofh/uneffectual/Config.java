package cofh.uneffectual;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraftforge.fml.config.ModConfig.Type.CLIENT;

public class Config {

    private static boolean registered = false;

    public static void register() {

        if (registered) {
            return;
        }
        FMLJavaModLoadingContext.get().getModEventBus().register(Config.class);
        registered = true;

        genClientConfig();

        ModLoadingContext.get().registerConfig(CLIENT, clientSpec);
    }

    private Config() {

    }

    // region CONFIG SPEC
    private static final ForgeConfigSpec.Builder CLIENT_CONFIG = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec clientSpec;

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