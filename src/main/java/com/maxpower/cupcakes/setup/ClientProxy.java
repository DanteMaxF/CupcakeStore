package com.maxpower.cupcakes.setup;

import com.maxpower.cupcakes.CupcakesMod;
import com.maxpower.cupcakes.container.ModContainers;
import com.maxpower.cupcakes.screen.MixerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CupcakesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy{
    @Override
    public void init() {

        ScreenManager.registerFactory(ModContainers.MIXER_CONTAINER.get(), MixerScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
}