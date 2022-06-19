package cofh.uneffectual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.EffectRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mod ("uneffectual")
public class Uneffectual {

    public Uneffectual() {

        Config.register();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
    }

    private EffectRenderer NO_RENDER = new EffectRenderer() {

        @Override
        public void renderInventoryEffect(MobEffectInstance effectInstance, EffectRenderingInventoryScreen<?> gui, PoseStack poseStack, int x, int y, float z) {

        }

        @Override
        public void renderHUDEffect(MobEffectInstance effectInstance, GuiComponent gui, PoseStack poseStack, int x, int y, float z, float alpha) {

        }

        @Override
        public boolean shouldRender(MobEffectInstance effect) {

            return false;
        }

        @Override
        public boolean shouldRenderHUD(MobEffectInstance effect) {

            return false;
        }
    };

    private void clientSetup(final FMLClientSetupEvent event) {

        for (String effectLoc : Config.getEffects()) {
            try {
                var effect = ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(effectLoc));
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
