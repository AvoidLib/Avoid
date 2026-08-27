package pl.olafcio.avoid;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import pl.olafcio.avoid.mixininterface.IEntity;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.mixininterface.IEntityFluidInteraction;
import pl.olafcio.avoid.net.fluid.properties._unbreatheable;

@Mod("avoidlib")
public final class A4Forge {
    public A4Forge(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

//        // Register the Deferred Register to the mod event bus so blocks get registered
//        BLOCKS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so items get registered
//        ITEMS.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so tabs get registered
//        CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

//        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);

//        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Avoid.INSTANCE.onInitialize();
    }

//    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.accept(EXAMPLE_BLOCK_ITEM);
//        }
//    }

    @SubscribeEvent
    public void onLivingBreathe(LivingBreatheEvent event) {
        if (event.canBreathe()) {
            var ent = event.getEntity();

            for (var fluid : FluidsNative.instances.keySet()) {
                if (fluid.getClass().isAnnotationPresent(_unbreatheable.class)) {
                    if (((IEntityFluidInteraction) ent.fluidInteraction).avoid$isEyeInFluid(fluid)) {
                        event.setCanBreathe(false);

                        if (event.canRefillAir()) {
                            event.setCanRefillAir(false);
                        }

                        // The ignoreColumn effect isn't available on Forge
                        //
                        // (well, it would require reimplementing vanilla.
                        //  which is stupid.)
                    }
                }
            }
        }
    }
}
