package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Villager.class)
public interface IVillager {
    @Invoker("releaseAllPois")
    void avoid$releaseAllPois();
}
