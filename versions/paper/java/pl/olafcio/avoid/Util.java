package pl.olafcio.avoid;

import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.net.world.vect3.Vect3;

import java.util.Objects;

@ApiStatus.Internal
public final class Util {
    private Util() {}

    public static Player convert(org.bukkit.entity.Player player) {
        return AvoidInternal.getServer().getPlayerList().getPlayer(player.getUniqueId());
    }

    public static Entity convertEntity(org.bukkit.entity.Entity entity) {
        return convert(entity.getWorld()).getEntity(entity.getEntityId());
    }

    public static ServerLevel convert(World world) {
        var key = world.getKey();
        var access = AvoidInternal.getServer().registryAccess().lookupOrThrow(Registries.DIMENSION);

        return AvoidInternal.getServer().getLevel(access.get(Identifier.fromNamespaceAndPath(key.namespace(), key.value())).orElseThrow().key());
    }

    public static World convertFrom(Level world) {
        var key = AvoidInternal.getServer().registryAccess().lookupOrThrow(Registries.DIMENSION)
                .getKey(world);

        return Bukkit.getServer().getWorld(Key.key(key.getNamespace(), key.getPath()));
    }

    public static ItemStack convert(org.bukkit.inventory.ItemStack itemStack) {
        var key = itemStack.getType().key();

        var patch = DataComponentPatch.builder();
        var patchBukkit = itemStack.getPersistentDataContainer();

        for (var nskey : patchBukkit.getKeys()) {
            var comp = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(Identifier.fromNamespaceAndPath(nskey.namespace(), nskey.value()));

            System.out.println("COMP: " + comp + " [" + nskey + "]");
//            patch.set(comp, patchBukkit.get(nskey, comp));
        }

        return new ItemStack(
                BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(key.namespace(), key.value())).orElseThrow(),
                itemStack.getAmount(),
                patch.build()
        );
    }

    public static org.bukkit.inventory.ItemStack convertFrom(ItemStack itemStack) {
        return new org.bukkit.inventory.ItemStack(
                Objects.requireNonNull(Material.getMaterial(VResourceKey.identifier(itemStack.getItemHolder().unwrapKey().orElseThrow()).getPath())),
                itemStack.getCount()
        );
    }

    public static IVect3 convert(Vector vec3) {
        if (vec3.getX() == 0 && vec3.getY() == 0 && vec3.getZ() == 0)
            return Vect3.ZERO;

        return new Vect3(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public static Vector convertFrom(IVect3 vec3) {
        return new Vector(vec3.x(), vec3.y(), vec3.z());
    }
}
