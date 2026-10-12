package pl.olafcio.avoid_impl.net.advancement;

import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.advancement.AdvancementType;
import pl.olafcio.avoid.net.advancement.properties._display;
import pl.olafcio.avoid.net.advancement.properties._parent;
import pl.olafcio.avoid.net.advancement.properties._type;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@ApiStatus.Experimental
public final class Advancements {
    @ApiStatus.Internal
    private Advancements() {}

    public static final HashMap<Identifier, Advancement> ADVANCEMENTS
                  = new HashMap<>();

    public static void register(Identification id, Class<? extends pl.olafcio.avoid.net.advancement.Advancement> advancement) {
        var display = advancement.getDeclaredAnnotation(_display.class);
        var type = advancement.getDeclaredAnnotation(_type.class)
                              .value();

        ADVANCEMENTS.put(IdentificationNative.convert(id), new Advancement(
                advancement.isAnnotationPresent(_parent.class)
                    ? IdentificationNative.convertOptional(
                        advancement.getDeclaredAnnotation(_parent.class)
                                   .id()
                      )
                    : Optional.empty(),
                Optional.of(new DisplayInfo(
                        BuiltInRegistries.ITEM.get(IdentificationNative.convert(display.icon())).orElseThrow().value().getDefaultInstance(),
                        COToNative.from(Components.Parse.minimessage(display.title())),
                        COToNative.from(Components.Parse.minimessage(display.description())),
                        IdentificationNative.convertOptional(display.background())
                                            .map(ClientAsset.ResourceTexture::new),
                        type == AdvancementType.GOAL ? net.minecraft.advancements.AdvancementType.GOAL :
                        type == AdvancementType.TASK ? net.minecraft.advancements.AdvancementType.TASK :
                                                       net.minecraft.advancements.AdvancementType.CHALLENGE,
                        display.showToast(),
                        display.announceChat(),
                        display.hidden()
                )),
                AdvancementRewards.EMPTY,
                Map.of("toggle", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance())),
                AdvancementRequirements.anyOf(Collections.singleton("toggle")),
                false
        ));
    }
}
