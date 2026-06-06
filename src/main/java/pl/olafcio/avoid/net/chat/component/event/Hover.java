package pl.olafcio.avoid.net.chat.component.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateFormattedComponent;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.item.stack.ItemStack;

import java.util.*;

@NullMarked
@NeverRemoval
public sealed interface Hover {
    Type type();

    enum Type {
        VIEW_TEXT("show_text", true),
        VIEW_ITEM("show_item", true),
        VIEW_ENTITY("show_entity", true);

        public final String id;
        public final boolean allowInNetworking;

        Type(String id, boolean allowInNetworking) {
            this.id = id;
            this.allowInNetworking = allowInNetworking;
        }
    }

    record ViewEntity(EntityTooltip entity) implements Hover {
        @Override
        public Type type() {
            return Type.VIEW_ENTITY;
        }
    }

    record ViewItem(ItemStack item) implements Hover {
        public ViewItem {
            item = item.copy();
        }

        @Override
        public Type type() {
            return Type.VIEW_ITEM;
        }
    }

    record ViewText(BaseComponent<?> value) implements Hover {
        @Override
        public Type type() {
            return Type.VIEW_TEXT;
        }
    }

    @ApiStatus.Internal
    class EntityTooltip {
        public final EntityType type;
        public final UUID uuid;
        @Nullable public final BaseComponent<?> name;
        @Nullable private List<BaseComponent<?>> linesCache;

        public EntityTooltip(EntityType entityType, UUID uuid, @Nullable BaseComponent<?> name) {
            this.type = entityType;
            this.uuid = uuid;
            this.name = name;
        }

        public List<BaseComponent<?>> getTooltipLines() {
            if (this.linesCache == null) {
                this.linesCache = new ArrayList<>();

                if (this.name != null)
                    this.linesCache.add(this.name);

                this.linesCache.add(TranslateFormattedComponent.of("gui.entity_tooltip.type", this.type.getDescription()));
                this.linesCache.add(TextComponent.of(this.uuid.toString()));
            }

            return this.linesCache;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            } else if (object != null && this.getClass() == object.getClass()) {
                var entityTooltipInfo = (EntityTooltip)object;
                return this.type.equals(entityTooltipInfo.type) &&
                       this.uuid.equals(entityTooltipInfo.uuid) &&
                        Objects.equals(this.name, entityTooltipInfo.name);
            } else {
                return false;
            }
        }

        public int hashCode() {
            int i = this.type.hashCode();
            i = 31 * i + this.uuid.hashCode();
            return 31 * i + this.name.hashCode();
        }
    }
}
