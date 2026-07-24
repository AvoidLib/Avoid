package pl.olafcio.avoid.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.TestBlock;
import net.minecraft.world.level.block.state.properties.TestBlockMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.creative_tab.CreativeTab;
import pl.olafcio.avoid.net.creative_tab.CreativeTabsNative;
import pl.olafcio.avoid.net.creative_tab.property._title;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {
    @Inject(at = @At("RETURN"), method = "bootstrap")
    private static void bootstrap(Registry<CreativeModeTab> registry, CallbackInfoReturnable<CreativeModeTab> cir) {
        var tabs = CreativeTabsNative.tabs;

        for (var entry : tabs.entrySet())
        {
            var id = entry.getKey();
            var tab = entry.getValue();

            Component title;

            var klass = tab.getClass();
            if (klass.isAnnotationPresent(_title.class))
            {
                title = Component.literal(klass.getDeclaredAnnotation(_title.class)
                                               .value());
            }
            else
            {
                Avoid.LOGGER.warn("Creative Tab doesn't have a @_title property; falling back to ID");

                title = Component.literal(id.toString("."));
            }

            Registry.register(
                    registry,
                    ResourceKey.create(Registries.CREATIVE_MODE_TAB, IdentificationNative.convert(id)),
                    CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 7)
                            .title(title)
                            .icon(() -> ItemStackNative.convert(tab.getIcon()))
                            .alignedRight()
                            .displayItems(
                                    (itemDisplayParameters, output) -> {
                                        if (itemDisplayParameters.hasPermissions()) {
                                            var items = tab.getItems();
                                            for (var item : items)
                                                output.accept(ItemStackNative.convert(item));
                                        }
                                    }
                            )
                            .build()
            );
        }
    }
}
