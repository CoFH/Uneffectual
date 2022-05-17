package cofh.uneffectual;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        refreshClientConfig();
    }

    private static void refreshClientConfig() {

    }

    public static List<String> getEffects() {

        return effects == null ? Collections.emptyList() : effects.get();
    }

    // region CONFIGURATION
    @SubscribeEvent
    public static void configLoading(ModConfigEvent.Loading event) {

        if (event.getConfig().getType() == CLIENT) {
            refreshClientConfig();
        }
    }

    @SubscribeEvent
    public static void configReloading(ModConfigEvent.Reloading event) {

        if (event.getConfig().getType() == CLIENT) {
            refreshClientConfig();
        }
    }
    // endregion

    // region VARIABLES
    private static ForgeConfigSpec.ConfigValue<List<String>> effects;
    // endregion
}