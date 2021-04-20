package com.maxpower.cupcakes.block.blockClasses;

import com.maxpower.cupcakes.container.containerClasses.MixerContainer;
import com.maxpower.cupcakes.tileentity.ModTileEntities;
import com.maxpower.cupcakes.tileentity.tileEntityClasses.MixerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Stream;

public class Mixer extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(3, 11, 7, 6, 12, 10),
            Block.makeCuboidShape(4, 7, 8, 5, 11, 9),
            Block.makeCuboidShape(4, 7, 7, 5, 9, 10),
            Block.makeCuboidShape(1, 0, 5, 15, 2, 12),
            Block.makeCuboidShape(11, 2, 6, 15, 15, 11),
            Block.makeCuboidShape(1, 12, 6, 11, 15, 11),
            Block.makeCuboidShape(4, 1.75, 6, 5, 2.75, 11),
            Block.makeCuboidShape(2, 2, 8, 7, 3, 9),
            Block.makeCuboidShape(4, 3, 11, 5, 4, 12),
            Block.makeCuboidShape(2, 3, 9, 4, 4, 11),
            Block.makeCuboidShape(1, 4, 11, 4, 5, 12),
            Block.makeCuboidShape(1, 4, 5, 4, 5, 6),
            Block.makeCuboidShape(5, 4, 11, 8, 5, 12),
            Block.makeCuboidShape(5, 4, 5, 8, 5, 6),
            Block.makeCuboidShape(1, 5, 12, 4, 6, 13),
            Block.makeCuboidShape(1, 5, 4, 4, 6, 5),
            Block.makeCuboidShape(5, 5, 12, 8, 6, 13),
            Block.makeCuboidShape(5, 5, 4, 8, 6, 5),
            Block.makeCuboidShape(2, 6, 13, 4, 7, 14),
            Block.makeCuboidShape(2, 6, 3, 4, 7, 4),
            Block.makeCuboidShape(5, 6, 3, 7, 7, 4),
            Block.makeCuboidShape(5, 6, 13, 7, 7, 14),
            Block.makeCuboidShape(1, 4, 9, 2, 5, 11),
            Block.makeCuboidShape(1, 4, 6, 2, 5, 8),
            Block.makeCuboidShape(7, 4, 9, 8, 5, 11),
            Block.makeCuboidShape(7, 4, 6, 8, 5, 8),
            Block.makeCuboidShape(0, 5, 9, 1, 6, 12),
            Block.makeCuboidShape(0, 5, 5, 1, 6, 8),
            Block.makeCuboidShape(8, 5, 9, 9, 6, 12),
            Block.makeCuboidShape(8, 5, 5, 9, 6, 8),
            Block.makeCuboidShape(-1, 6, 9, 0, 7, 11),
            Block.makeCuboidShape(-1, 6, 6, 0, 7, 8),
            Block.makeCuboidShape(9, 6, 9, 10, 7, 11),
            Block.makeCuboidShape(9, 6, 6, 10, 7, 8),
            Block.makeCuboidShape(2, 3, 6, 4, 4, 8),
            Block.makeCuboidShape(5, 3, 6, 7, 4, 8),
            Block.makeCuboidShape(5, 3, 9, 7, 4, 11),
            Block.makeCuboidShape(4, 3, 5, 5, 4, 6),
            Block.makeCuboidShape(7, 3, 8, 8, 4, 9),
            Block.makeCuboidShape(1, 3, 8, 2, 4, 9),
            Block.makeCuboidShape(8, 4, 8, 9, 5, 9),
            Block.makeCuboidShape(0, 4, 8, 1, 5, 9),
            Block.makeCuboidShape(9, 5, 8, 10, 8, 9),
            Block.makeCuboidShape(9, 7, 6, 10, 8, 11),
            Block.makeCuboidShape(2, 7, 13, 7, 8, 14),
            Block.makeCuboidShape(2, 7, 3, 7, 8, 4),
            Block.makeCuboidShape(8, 7, 5, 9, 8, 6),
            Block.makeCuboidShape(1, 7, 4, 2, 8, 5),
            Block.makeCuboidShape(7, 7, 12, 8, 8, 13),
            Block.makeCuboidShape(1, 7, 12, 2, 8, 13),
            Block.makeCuboidShape(1, 6, 12, 2, 7, 13),
            Block.makeCuboidShape(1, 6, 4, 2, 7, 5),
            Block.makeCuboidShape(7, 6, 12, 8, 7, 13),
            Block.makeCuboidShape(0, 7, 11, 1, 8, 12),
            Block.makeCuboidShape(0, 6, 11, 1, 7, 12),
            Block.makeCuboidShape(0, 6, 5, 1, 7, 6),
            Block.makeCuboidShape(8, 6, 11, 9, 7, 12),
            Block.makeCuboidShape(8, 6, 5, 9, 7, 6),
            Block.makeCuboidShape(7, 6, 4, 8, 7, 5),
            Block.makeCuboidShape(8, 7, 11, 9, 8, 12),
            Block.makeCuboidShape(7, 7, 4, 8, 8, 5),
            Block.makeCuboidShape(0, 7, 5, 1, 8, 6),
            Block.makeCuboidShape(-1, 7, 6, 0, 8, 11),
            Block.makeCuboidShape(-1, 5, 8, 0, 8, 9),
            Block.makeCuboidShape(4, 4, 4, 5, 5, 5),
            Block.makeCuboidShape(4, 4, 12, 5, 5, 13),
            Block.makeCuboidShape(4, 5, 13, 5, 8, 14),
            Block.makeCuboidShape(4, 5, 3, 5, 8, 4)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(3, 11, 7, 6, 12, 10),
            Block.makeCuboidShape(4, 7, 8, 5, 11, 9),
            Block.makeCuboidShape(4, 7, 7, 5, 9, 10),
            Block.makeCuboidShape(1, 0, 5, 15, 2, 12),
            Block.makeCuboidShape(11, 2, 6, 15, 15, 11),
            Block.makeCuboidShape(1, 12, 6, 11, 15, 11),
            Block.makeCuboidShape(4, 1.75, 6, 5, 2.75, 11),
            Block.makeCuboidShape(2, 2, 8, 7, 3, 9),
            Block.makeCuboidShape(4, 3, 11, 5, 4, 12),
            Block.makeCuboidShape(2, 3, 9, 4, 4, 11),
            Block.makeCuboidShape(1, 4, 11, 4, 5, 12),
            Block.makeCuboidShape(1, 4, 5, 4, 5, 6),
            Block.makeCuboidShape(5, 4, 11, 8, 5, 12),
            Block.makeCuboidShape(5, 4, 5, 8, 5, 6),
            Block.makeCuboidShape(1, 5, 12, 4, 6, 13),
            Block.makeCuboidShape(1, 5, 4, 4, 6, 5),
            Block.makeCuboidShape(5, 5, 12, 8, 6, 13),
            Block.makeCuboidShape(5, 5, 4, 8, 6, 5),
            Block.makeCuboidShape(2, 6, 13, 4, 7, 14),
            Block.makeCuboidShape(2, 6, 3, 4, 7, 4),
            Block.makeCuboidShape(5, 6, 3, 7, 7, 4),
            Block.makeCuboidShape(5, 6, 13, 7, 7, 14),
            Block.makeCuboidShape(1, 4, 9, 2, 5, 11),
            Block.makeCuboidShape(1, 4, 6, 2, 5, 8),
            Block.makeCuboidShape(7, 4, 9, 8, 5, 11),
            Block.makeCuboidShape(7, 4, 6, 8, 5, 8),
            Block.makeCuboidShape(0, 5, 9, 1, 6, 12),
            Block.makeCuboidShape(0, 5, 5, 1, 6, 8),
            Block.makeCuboidShape(8, 5, 9, 9, 6, 12),
            Block.makeCuboidShape(8, 5, 5, 9, 6, 8),
            Block.makeCuboidShape(-1, 6, 9, 0, 7, 11),
            Block.makeCuboidShape(-1, 6, 6, 0, 7, 8),
            Block.makeCuboidShape(9, 6, 9, 10, 7, 11),
            Block.makeCuboidShape(9, 6, 6, 10, 7, 8),
            Block.makeCuboidShape(2, 3, 6, 4, 4, 8),
            Block.makeCuboidShape(5, 3, 6, 7, 4, 8),
            Block.makeCuboidShape(5, 3, 9, 7, 4, 11),
            Block.makeCuboidShape(4, 3, 5, 5, 4, 6),
            Block.makeCuboidShape(7, 3, 8, 8, 4, 9),
            Block.makeCuboidShape(1, 3, 8, 2, 4, 9),
            Block.makeCuboidShape(8, 4, 8, 9, 5, 9),
            Block.makeCuboidShape(0, 4, 8, 1, 5, 9),
            Block.makeCuboidShape(9, 5, 8, 10, 8, 9),
            Block.makeCuboidShape(9, 7, 6, 10, 8, 11),
            Block.makeCuboidShape(2, 7, 13, 7, 8, 14),
            Block.makeCuboidShape(2, 7, 3, 7, 8, 4),
            Block.makeCuboidShape(8, 7, 5, 9, 8, 6),
            Block.makeCuboidShape(1, 7, 4, 2, 8, 5),
            Block.makeCuboidShape(7, 7, 12, 8, 8, 13),
            Block.makeCuboidShape(1, 7, 12, 2, 8, 13),
            Block.makeCuboidShape(1, 6, 12, 2, 7, 13),
            Block.makeCuboidShape(1, 6, 4, 2, 7, 5),
            Block.makeCuboidShape(7, 6, 12, 8, 7, 13),
            Block.makeCuboidShape(0, 7, 11, 1, 8, 12),
            Block.makeCuboidShape(0, 6, 11, 1, 7, 12),
            Block.makeCuboidShape(0, 6, 5, 1, 7, 6),
            Block.makeCuboidShape(8, 6, 11, 9, 7, 12),
            Block.makeCuboidShape(8, 6, 5, 9, 7, 6),
            Block.makeCuboidShape(7, 6, 4, 8, 7, 5),
            Block.makeCuboidShape(8, 7, 11, 9, 8, 12),
            Block.makeCuboidShape(7, 7, 4, 8, 8, 5),
            Block.makeCuboidShape(0, 7, 5, 1, 8, 6),
            Block.makeCuboidShape(-1, 7, 6, 0, 8, 11),
            Block.makeCuboidShape(-1, 5, 8, 0, 8, 9),
            Block.makeCuboidShape(4, 4, 4, 5, 5, 5),
            Block.makeCuboidShape(4, 4, 12, 5, 5, 13),
            Block.makeCuboidShape(4, 5, 13, 5, 8, 14),
            Block.makeCuboidShape(4, 5, 3, 5, 8, 4)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(3, 11, 7, 6, 12, 10),
            Block.makeCuboidShape(4, 7, 8, 5, 11, 9),
            Block.makeCuboidShape(4, 7, 7, 5, 9, 10),
            Block.makeCuboidShape(1, 0, 5, 15, 2, 12),
            Block.makeCuboidShape(11, 2, 6, 15, 15, 11),
            Block.makeCuboidShape(1, 12, 6, 11, 15, 11),
            Block.makeCuboidShape(4, 1.75, 6, 5, 2.75, 11),
            Block.makeCuboidShape(2, 2, 8, 7, 3, 9),
            Block.makeCuboidShape(4, 3, 11, 5, 4, 12),
            Block.makeCuboidShape(2, 3, 9, 4, 4, 11),
            Block.makeCuboidShape(1, 4, 11, 4, 5, 12),
            Block.makeCuboidShape(1, 4, 5, 4, 5, 6),
            Block.makeCuboidShape(5, 4, 11, 8, 5, 12),
            Block.makeCuboidShape(5, 4, 5, 8, 5, 6),
            Block.makeCuboidShape(1, 5, 12, 4, 6, 13),
            Block.makeCuboidShape(1, 5, 4, 4, 6, 5),
            Block.makeCuboidShape(5, 5, 12, 8, 6, 13),
            Block.makeCuboidShape(5, 5, 4, 8, 6, 5),
            Block.makeCuboidShape(2, 6, 13, 4, 7, 14),
            Block.makeCuboidShape(2, 6, 3, 4, 7, 4),
            Block.makeCuboidShape(5, 6, 3, 7, 7, 4),
            Block.makeCuboidShape(5, 6, 13, 7, 7, 14),
            Block.makeCuboidShape(1, 4, 9, 2, 5, 11),
            Block.makeCuboidShape(1, 4, 6, 2, 5, 8),
            Block.makeCuboidShape(7, 4, 9, 8, 5, 11),
            Block.makeCuboidShape(7, 4, 6, 8, 5, 8),
            Block.makeCuboidShape(0, 5, 9, 1, 6, 12),
            Block.makeCuboidShape(0, 5, 5, 1, 6, 8),
            Block.makeCuboidShape(8, 5, 9, 9, 6, 12),
            Block.makeCuboidShape(8, 5, 5, 9, 6, 8),
            Block.makeCuboidShape(-1, 6, 9, 0, 7, 11),
            Block.makeCuboidShape(-1, 6, 6, 0, 7, 8),
            Block.makeCuboidShape(9, 6, 9, 10, 7, 11),
            Block.makeCuboidShape(9, 6, 6, 10, 7, 8),
            Block.makeCuboidShape(2, 3, 6, 4, 4, 8),
            Block.makeCuboidShape(5, 3, 6, 7, 4, 8),
            Block.makeCuboidShape(5, 3, 9, 7, 4, 11),
            Block.makeCuboidShape(4, 3, 5, 5, 4, 6),
            Block.makeCuboidShape(7, 3, 8, 8, 4, 9),
            Block.makeCuboidShape(1, 3, 8, 2, 4, 9),
            Block.makeCuboidShape(8, 4, 8, 9, 5, 9),
            Block.makeCuboidShape(0, 4, 8, 1, 5, 9),
            Block.makeCuboidShape(9, 5, 8, 10, 8, 9),
            Block.makeCuboidShape(9, 7, 6, 10, 8, 11),
            Block.makeCuboidShape(2, 7, 13, 7, 8, 14),
            Block.makeCuboidShape(2, 7, 3, 7, 8, 4),
            Block.makeCuboidShape(8, 7, 5, 9, 8, 6),
            Block.makeCuboidShape(1, 7, 4, 2, 8, 5),
            Block.makeCuboidShape(7, 7, 12, 8, 8, 13),
            Block.makeCuboidShape(1, 7, 12, 2, 8, 13),
            Block.makeCuboidShape(1, 6, 12, 2, 7, 13),
            Block.makeCuboidShape(1, 6, 4, 2, 7, 5),
            Block.makeCuboidShape(7, 6, 12, 8, 7, 13),
            Block.makeCuboidShape(0, 7, 11, 1, 8, 12),
            Block.makeCuboidShape(0, 6, 11, 1, 7, 12),
            Block.makeCuboidShape(0, 6, 5, 1, 7, 6),
            Block.makeCuboidShape(8, 6, 11, 9, 7, 12),
            Block.makeCuboidShape(8, 6, 5, 9, 7, 6),
            Block.makeCuboidShape(7, 6, 4, 8, 7, 5),
            Block.makeCuboidShape(8, 7, 11, 9, 8, 12),
            Block.makeCuboidShape(7, 7, 4, 8, 8, 5),
            Block.makeCuboidShape(0, 7, 5, 1, 8, 6),
            Block.makeCuboidShape(-1, 7, 6, 0, 8, 11),
            Block.makeCuboidShape(-1, 5, 8, 0, 8, 9),
            Block.makeCuboidShape(4, 4, 4, 5, 5, 5),
            Block.makeCuboidShape(4, 4, 12, 5, 5, 13),
            Block.makeCuboidShape(4, 5, 13, 5, 8, 14),
            Block.makeCuboidShape(4, 5, 3, 5, 8, 4)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(7.1985294117647065, 11, 11.198529411764707, 10.198529411764707, 12, 14.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 7, 12.198529411764707, 9.198529411764707, 11, 13.198529411764707),
            Block.makeCuboidShape(7.1985294117647065, 7, 12.198529411764707, 10.198529411764707, 9, 13.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 0, 2.1985294117647065, 12.198529411764707, 2, 16.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 2, 2.1985294117647065, 11.198529411764707, 15, 6.1985294117647065),
            Block.makeCuboidShape(6.1985294117647065, 12, 6.1985294117647065, 11.198529411764707, 15, 16.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 1.75, 12.198529411764707, 11.198529411764707, 2.75, 13.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 2, 10.198529411764707, 9.198529411764707, 3, 15.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 3, 12.198529411764707, 12.198529411764707, 4, 13.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 3, 13.198529411764707, 11.198529411764707, 4, 15.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 4, 13.198529411764707, 12.198529411764707, 5, 16.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 4, 13.198529411764707, 6.1985294117647065, 5, 16.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 4, 9.198529411764707, 12.198529411764707, 5, 12.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 4, 9.198529411764707, 6.1985294117647065, 5, 12.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 5, 13.198529411764707, 13.198529411764707, 6, 16.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 5, 13.198529411764707, 5.1985294117647065, 6, 16.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 5, 9.198529411764707, 13.198529411764707, 6, 12.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 5, 9.198529411764707, 5.1985294117647065, 6, 12.198529411764707),
            Block.makeCuboidShape(13.198529411764707, 6, 13.198529411764707, 14.198529411764707, 7, 15.198529411764707),
            Block.makeCuboidShape(3.1985294117647065, 6, 13.198529411764707, 4.1985294117647065, 7, 15.198529411764707),
            Block.makeCuboidShape(3.1985294117647065, 6, 10.198529411764707, 4.1985294117647065, 7, 12.198529411764707),
            Block.makeCuboidShape(13.198529411764707, 6, 10.198529411764707, 14.198529411764707, 7, 12.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 4, 15.198529411764707, 11.198529411764707, 5, 16.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 4, 15.198529411764707, 8.198529411764707, 5, 16.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 4, 9.198529411764707, 11.198529411764707, 5, 10.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 4, 9.198529411764707, 8.198529411764707, 5, 10.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 5, 16.198529411764707, 12.198529411764707, 6, 17.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 5, 16.198529411764707, 8.198529411764707, 6, 17.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 5, 8.198529411764707, 12.198529411764707, 6, 9.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 5, 8.198529411764707, 8.198529411764707, 6, 9.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 6, 17.198529411764707, 11.198529411764707, 7, 18.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 6, 17.198529411764707, 8.198529411764707, 7, 18.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 6, 7.1985294117647065, 11.198529411764707, 7, 8.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 6, 7.1985294117647065, 8.198529411764707, 7, 8.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 3, 13.198529411764707, 8.198529411764707, 4, 15.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 3, 10.198529411764707, 8.198529411764707, 4, 12.198529411764707),
            Block.makeCuboidShape(9.198529411764707, 3, 10.198529411764707, 11.198529411764707, 4, 12.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 3, 12.198529411764707, 6.1985294117647065, 4, 13.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 3, 9.198529411764707, 9.198529411764707, 4, 10.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 3, 15.198529411764707, 9.198529411764707, 4, 16.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 4, 8.198529411764707, 9.198529411764707, 5, 9.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 4, 16.198529411764707, 9.198529411764707, 5, 17.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 5, 7.1985294117647065, 9.198529411764707, 8, 8.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 7, 7.1985294117647065, 11.198529411764707, 8, 8.198529411764707),
            Block.makeCuboidShape(13.198529411764707, 7, 10.198529411764707, 14.198529411764707, 8, 15.198529411764707),
            Block.makeCuboidShape(3.1985294117647065, 7, 10.198529411764707, 4.1985294117647065, 8, 15.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 7, 8.198529411764707, 6.1985294117647065, 8, 9.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 7, 15.198529411764707, 5.1985294117647065, 8, 16.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 7, 9.198529411764707, 13.198529411764707, 8, 10.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 7, 15.198529411764707, 13.198529411764707, 8, 16.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 6, 15.198529411764707, 13.198529411764707, 7, 16.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 6, 15.198529411764707, 5.1985294117647065, 7, 16.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 6, 9.198529411764707, 13.198529411764707, 7, 10.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 7, 16.198529411764707, 12.198529411764707, 8, 17.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 6, 16.198529411764707, 12.198529411764707, 7, 17.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 6, 16.198529411764707, 6.1985294117647065, 7, 17.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 6, 8.198529411764707, 12.198529411764707, 7, 9.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 6, 8.198529411764707, 6.1985294117647065, 7, 9.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 6, 9.198529411764707, 5.1985294117647065, 7, 10.198529411764707),
            Block.makeCuboidShape(11.198529411764707, 7, 8.198529411764707, 12.198529411764707, 8, 9.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 7, 9.198529411764707, 5.1985294117647065, 8, 10.198529411764707),
            Block.makeCuboidShape(5.1985294117647065, 7, 16.198529411764707, 6.1985294117647065, 8, 17.198529411764707),
            Block.makeCuboidShape(6.1985294117647065, 7, 17.198529411764707, 11.198529411764707, 8, 18.198529411764707),
            Block.makeCuboidShape(8.198529411764707, 5, 17.198529411764707, 9.198529411764707, 8, 18.198529411764707),
            Block.makeCuboidShape(4.1985294117647065, 4, 12.198529411764707, 5.1985294117647065, 5, 13.198529411764707),
            Block.makeCuboidShape(12.198529411764707, 4, 12.198529411764707, 13.198529411764707, 5, 13.198529411764707),
            Block.makeCuboidShape(13.198529411764707, 5, 12.198529411764707, 14.198529411764707, 8, 13.198529411764707),
            Block.makeCuboidShape(3.1985294117647065, 5, 12.198529411764707, 4.1985294117647065, 8, 13.198529411764707)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public Mixer(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {

            case EAST:
                return SHAPE_E;

            case SOUTH:
                return SHAPE_S;

            case WEST:
                return SHAPE_W;

            default:
                return SHAPE_N;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof MixerTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("Mezcladora");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new MixerContainer(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our container provider is missing!");
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.MIXER_TILE_ENTITY.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
