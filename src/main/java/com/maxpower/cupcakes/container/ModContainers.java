package com.maxpower.cupcakes.container;

import com.maxpower.cupcakes.container.containerClasses.MixerContainer;
import com.maxpower.cupcakes.util.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {
    public static final RegistryObject<ContainerType<MixerContainer>> MIXER_CONTAINER
            = Registration.CONTAINERS.register(
            "mixer_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new MixerContainer(windowId, world, pos, inv, inv.player);
            }))
    );

    public static void register () {  }
}
