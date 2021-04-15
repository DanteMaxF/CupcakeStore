package com.maxpower.cupcakes.events;

import com.maxpower.cupcakes.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

import java.util.Collection;

public class ModEvents {

    // When a player right clicks a butter_dispenser
    @SubscribeEvent
    public void onCopperedSheep(PlayerInteractEvent.RightClickBlock event) {
        if (event.getPlayer().getHeldItemMainhand().getItem() == ModItems.COIN.get() && event.getPlayer().getHeldItemMainhand().getCount() >= 10) {
            PlayerEntity player = event.getPlayer();

            player.getHeldItemMainhand().shrink(10);

            player.addItemStackToInventory(new ItemStack(ModItems.BUTTER.get(), 1));
        }
    }



}
