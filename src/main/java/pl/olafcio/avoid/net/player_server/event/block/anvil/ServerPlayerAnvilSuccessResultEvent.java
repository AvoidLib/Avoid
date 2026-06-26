package pl.olafcio.avoid.net.player_server.event.block.anvil;

import pl.olafcio.avoid.net.item.stack.ItemStack;

public final class ServerPlayerAnvilSuccessResultEvent {
    private final ItemStack item1;
    private final ItemStack item2;

    private ItemStack result;
    private boolean modified;
    private final ItemStack originalResult;

    public ServerPlayerAnvilSuccessResultEvent(ItemStack item1, ItemStack item2, ItemStack result) {
        this.item1 = item1;
        this.item2 = item2;
        this.result =
        this.originalResult = result;
        this.modified = false;
    }

    public ItemStack getItem1() {
        return item1;
    }

    public ItemStack getItem2() {
        return item2;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }

    public ItemStack getOriginalResult() {
        return originalResult;
    }
}
