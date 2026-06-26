package pl.olafcio.avoid.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.player_server.event.block.enchanting_table.ServerPlayerEnchantingTableBookshelfAmountEvent;
import pl.olafcio.avoid.net.world.WorldNative;

import java.util.List;
import java.util.function.BiConsumer;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {
    @Shadow @Final private ContainerLevelAccess access;
    @Shadow @Final private DataSlot enchantmentSeed;
    @Shadow @Final private RandomSource random;

    @Shadow @Final public int[] costs;
    @Shadow @Final public int[] enchantClue;
    @Shadow @Final public int[] levelClue;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Shadow protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack itemStack, int i, int j);

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ContainerLevelAccess;execute(Ljava/util/function/BiConsumer;)V"), method = "slotsChanged")
    private void slotsChanged__execute(ContainerLevelAccess instance, BiConsumer<Level, BlockPos> biConsumer, Container container) {
        ItemStack itemStack = container.getItem(0);

        this.access.execute((level, blockPos) -> {
            IdMap<Holder<Enchantment>> idMap = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).asHolderIdMap();

            int bookshelves = 0;
            for (BlockPos blockPos2 : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                if (!EnchantingTableBlock.isValidBookShelf(level, blockPos, blockPos2)) continue;
                ++bookshelves;
            }

            var event = new ServerPlayerEnchantingTableBookshelfAmountEvent(
                    bookshelves,
                    WorldNative.make(level),
                    BlockPosNative.convert(blockPos)
            );
            EventManager.fire(event);
            bookshelves = event.getAmount();

            this.random.setSeed(this.enchantmentSeed.get());

            for (int i = 0; i < 3; ++i) {
                this.costs[i] = EnchantmentHelper.getEnchantmentCost(this.random, i, bookshelves, itemStack);
                this.enchantClue[i] = -1;
                this.levelClue[i] = -1;
                if (this.costs[i] >= i + 1) continue;
                this.costs[i] = 0;
            }

            for (int i = 0; i < 3; ++i) {
                List<EnchantmentInstance> list;
                if (this.costs[i] <= 0 || (list = this.getEnchantmentList(level.registryAccess(), itemStack, i, this.costs[i])).isEmpty()) continue;
                EnchantmentInstance enchantmentInstance = list.get(this.random.nextInt(list.size()));
                this.enchantClue[i] = idMap.getId(enchantmentInstance.enchantment());
                this.levelClue[i] = enchantmentInstance.level();
            }

            this.broadcastChanges();
        });
    }
}
