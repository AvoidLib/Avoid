package pl.olafcio.avoid.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.*;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.AvoidManager;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(BuiltInPackSource.class)
public class BuiltInPackSourceMixin {
    @Unique
    private List<Pack> modPacks = null;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/BuiltInPackSource;listBundledPacks(Ljava/util/function/Consumer;)V"), method = "loadPacks")
    public void loadPacks__almostFinished(Consumer<Pack> consumer, CallbackInfo ci) {
        if (modPacks == null) {
            modPacks = new ArrayList<>();

            var addons = AvoidManager.getLoadedAddons();
            for (AvoidModMeta addon : addons)
                modPacks.add(
                        new Pack(
                                new PackLocationInfo(
                                        "avoid_" + addon.id(),
                                        Component.literal("Avoid | " + addon.name()),
                                        PackSource.create(x -> x, true),
                                        Optional.of(new KnownPack("avoidlib_res", addon.id(), "1"))
                                ),
                                new FilePackResources.FileResourcesSupplier(AvoidManager.getLoadedAddonFile(addon)),
                                new Pack.Metadata(Component.literal("(Mod resources)"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                                new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                );
        }

        for (var pack : modPacks)
            consumer.accept(pack);
    }
}
