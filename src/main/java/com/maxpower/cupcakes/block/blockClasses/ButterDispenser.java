package com.maxpower.cupcakes.block.blockClasses;


import com.maxpower.cupcakes.container.containerClasses.MixerContainer;
import com.maxpower.cupcakes.item.ModItems;
import com.maxpower.cupcakes.tileentity.tileEntityClasses.MixerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ButterDispenser extends Block {

    public ButterDispenser(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            if (player.getHeldItemMainhand().getItem() == ModItems.COIN.get() &&
                    player.getHeldItemMainhand().getCount() >= 2) {
                player.getHeldItemMainhand().shrink(2);

                player.addItemStackToInventory(new ItemStack(ModItems.BUTTER.get(), 1));
            }
        }

        return ActionResultType.SUCCESS;
    }
}
