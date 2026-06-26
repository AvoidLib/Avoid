package pl.olafcio.avoid.net.player_server.event.block.anvil;

import pl.olafcio.avoid.mods.event.Forceable;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.stack.ItemStack;

public final class ServerPlayerAnvilNameValidationEvent extends Forceable {
    private final String name;

    public ServerPlayerAnvilNameValidationEvent(
            boolean valid,

            String name
    ) {
        super(valid);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
