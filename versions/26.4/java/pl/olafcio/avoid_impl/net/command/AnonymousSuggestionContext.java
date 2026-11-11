package pl.olafcio.avoid_impl.net.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.suggestions.SuggestionContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.permission.PermissionHolder;
import pl.olafcio.avoid_impl.mixinclass.AvoidBasePermissionSet;
import pl.olafcio.avoid_impl.mixinclass.AvoidPermissionSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AnonymousSuggestionContext implements SuggestionContext {
    private final MinecraftServer server;
    private final PermissionHolder source;
    private final CommandSourceStack stack;

    public AnonymousSuggestionContext(MinecraftServer server, PermissionHolder source, CommandSourceStack stack) {
        this.server = server;
        this.source = source;
        this.stack = stack;
    }

    @Override
    public Collection<String> onlinePlayerNames() {
        return Arrays.asList(server.getPlayerNames());
    }

    @Override
    public Collection<String> allTeams() {
        return server.getScoreboard().getTeamNames();
    }

    @Override
    public Stream<Identifier> availableSounds() {
        return BuiltInRegistries.SOUND_EVENT.stream().map(SoundEvent::location);
    }

    @Override
    public Stream<Identifier> availablePostEffects() {
        return Stream.empty();
    }

    @Override
    public @Nullable MinecraftServer server() {
        return server;
    }

    @Override
    public @Nullable Level level() {
        return null;
    }

    @Override
    public Set<ResourceKey<Level>> levels() {
        return Set.of();
    }

    @Override
    public @Nullable Entity entity() {
        return null;
    }

    @Override
    public Vec3 position() {
        return Vec3.ZERO;
    }

    @Override
    public HolderLookup.Provider holderLookups() {
        return server.reloadableRegistries().lookup().filterFeatures(enabledFeatures());
    }

    @Override
    public CompletableFuture<Suggestions> suggestObjectiveNames(CommandContext<?> context, SuggestionsBuilder builder) {
        return SuggestionContext.suggest(server.getScoreboard().getObjectiveNames(), builder);
    }

    @Override
    public CompletableFuture<Suggestions> customSuggestion(CommandContext<?> context) {
        return Suggestions.empty();
    }

    @Override
    public <E> CompletableFuture<Suggestions> suggestRegistryElements(CommandContext<?> context, ResourceKey<? extends Registry<? extends E>> key, ElementSuggestionType elements, SuggestionsBuilder builder, Predicate<E> filter) {
        return this.holderLookups().lookup(key).map((registry) -> {
            this.suggestRegistryElements(registry, elements, builder, filter);
            return builder.buildFuture();
        }).orElseGet(Suggestions::empty);
    }

    @Override
    public FeatureFlagSet enabledFeatures() {
        return server.getWorldData().enabledFeatures();
    }

    @Override
    public PermissionSet permissions() {
        return stack.permissions() instanceof AvoidBasePermissionSet<?> avoid
                ? avoid
                : new AvoidBasePermissionSet<>(
                        stack.permissions(),
                        source
                );
    }
}
