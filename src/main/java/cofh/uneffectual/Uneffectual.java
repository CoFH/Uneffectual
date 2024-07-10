package cofh.uneffectual;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod ("uneffectual")
public class Uneffectual {

    public Uneffectual(ModContainer modContainer, IEventBus modEventBus) {

        Config.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
    }

    private IClientMobEffectExtensions NO_RENDER = new IClientMobEffectExtensions() {

        @Override
        public boolean isVisibleInInventory(MobEffectInstance instance) {

            return false;
        }

        @Override
        public boolean isVisibleInGui(MobEffectInstance instance) {

            return false;
        }

    };

    private void clientSetup(final FMLClientSetupEvent event) {

        for (String effectLoc : Config.getEffects()) {
            try {
                var effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(effectLoc));
                Field effectRenderer = MobEffect.class.getDeclaredField("effectRenderer");
                effectRenderer.setAccessible(true);
                effectRenderer.set(effect, NO_RENDER);

            } catch (Throwable t) {
                // pokemon
            }
        }

    }

    private List<Field> getAllFields(Class clazz) {

        if (clazz == null) {
            return Collections.emptyList();
        }
        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers())).toList();
        result.addAll(filteredFields);
        return result;
    }

}
