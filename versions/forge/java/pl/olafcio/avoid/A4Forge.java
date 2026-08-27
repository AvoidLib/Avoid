package pl.olafcio.avoid;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

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

//        // Register ourselves for server and other game events we are interested in.
//        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
//        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//        NeoForge.EVENT_BUS.register(this);

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
//
//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }
}
