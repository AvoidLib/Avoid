package pl.olafcio.avoid.listeners;

import com.destroystokyo.paper.event.entity.EntityZapEvent;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.A4Paper;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.effect.instance.EffectInstance;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.*;
import pl.olafcio.avoid.net.entity_server.event.llama.ServerLlamaSpitEvent;
import pl.olafcio.avoid.net.id.Identification;

public final class EntityListener implements Listener {
    @EventHandler
    public void onEntityZap(EntityZapEvent bukkitEvent) {
        var event = new ServerEntityStruckByBoltEvent(
                EntityNative.convertFrom(Util.convertEntity(bukkitEvent.getEntity())),
                EntityNative.convertFrom(Util.convertEntity(bukkitEvent.getBolt()))
        );

        EventManager.fire(event);

        if (!event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onLlamaSpit(ProjectileLaunchEvent bukkitEvent) {
        if (bukkitEvent.getEntity() instanceof LlamaSpit llamaSpit && llamaSpit.getShooter() instanceof Llama llama && llama.getTarget() != null) {
            var event = new ServerLlamaSpitEvent(
                    EntityNative.convertFrom(Util.convertEntity(llama.getTarget())),
                    EntityNative.convertFrom(Util.convertEntity(llama))
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityKnockback(EntityKnockbackEvent bukkitEvent) {
        var event = new ServerEntityVelocityEvent(
                EntityNative.convertFromTry(Util.convertEntity(bukkitEvent.getEntity())),
                Util.convert(bukkitEvent.getKnockback())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
        else if (event.isVelocityChanged())
            bukkitEvent.setKnockback(Util.convertFrom(event.getVelocity()));
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent bukkitEvent) {
        if (bukkitEvent.getAmount() > 0) {
            var entity = (LivingEntity) bukkitEvent.getEntity();
            var current = entity.getHealth();

            var event = new ServerEntitySetHealthEvent(
                    EntityNative.convertFromTry(Util.convertEntity(entity)),
                    (float) Math.min(entity.getMaxHealth(), current + bukkitEvent.getAmount()),
                    (float) current
            );

            EventManager.fire(event);

            if (event.isCancelled()) {
                bukkitEvent.setCancelled(true);
            } else if (event.isLevelChanged()) {
                var level = event.getLevel();
                if (level >= current)
                    bukkitEvent.setAmount(level - current);
                else {
                    bukkitEvent.setAmount(0);
                    Bukkit.getScheduler().runTask(A4Paper.INSTANCE, () -> {
                        entity.setHealth(level);
                    });
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent bukkitEvent) {
        if (bukkitEvent.getFinalDamage() > 0) {
            var entity = (LivingEntity) bukkitEvent.getEntity();
            var current = entity.getHealth();

            var event = new ServerEntitySetHealthEvent(
                    EntityNative.convertFromTry(Util.convertEntity(entity)),
                    (float) Math.max(0, current - bukkitEvent.getFinalDamage()),
                    (float) current
            );

            EventManager.fire(event);

            if (event.isCancelled()) {
                bukkitEvent.setCancelled(true);
            } else if (event.isLevelChanged()) {
                var level = event.getLevel();
                if (level <= current)
                    bukkitEvent.setDamage(current - level);
                else {
                    bukkitEvent.setDamage(0);
                    Bukkit.getScheduler().runTask(A4Paper.INSTANCE, () -> {
                        entity.setHealth(level);
                    });
                }
            }
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent bukkitEvent) {
        if (bukkitEvent.getAction() == EntityPotionEffectEvent.Action.ADDED) {
            var event = new ServerEntityEffectAddEvent(
                    EntityNative.convertFromTry(Util.convertEntity(bukkitEvent.getEntity())),
                    convert(bukkitEvent.getNewEffect())
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        } else if (bukkitEvent.getAction() == EntityPotionEffectEvent.Action.CHANGED) {
            var event = new ServerEntityEffectUpdateEvent(
                    EntityNative.convertFromTry(Util.convertEntity(bukkitEvent.getEntity())),
                    convert(bukkitEvent.getNewEffect())
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        } else {
            var event = new ServerEntityEffectRemoveEvent(
                    EntityNative.convertFromTry(Util.convertEntity(bukkitEvent.getEntity())),
                    convert(bukkitEvent.getOldEffect())
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        }
    }

    private static EffectInstance convert(@Nullable PotionEffect effect) {
        var key = effect.getType().key();

        return new EffectInstance(
                new Identification(key.namespace(), key.value()),
                effect.getDuration(),
                effect.getAmplifier() + 1
        );
    }
}
