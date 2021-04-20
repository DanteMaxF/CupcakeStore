package com.maxpower.cupcakes.tileentity.tileEntityClasses;

import com.maxpower.cupcakes.item.ModItems;
import com.maxpower.cupcakes.screen.MixerScreen;
import com.maxpower.cupcakes.tileentity.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MixerTile extends TileEntity implements ITickableTileEntity {

    static enum Status {
            WAITING,
            PROCESSING,
    }

    private int tick = 0;
    private Status mixerStatus = Status.WAITING;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public MixerTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MixerTile() {

        this(ModTileEntities.MIXER_TILE_ENTITY.get());
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }

    @Override
    public void tick() {

            if (
                this.itemHandler.getStackInSlot(0).getItem() == ModItems.BUTTER.get() &&
                this.itemHandler.getStackInSlot(1).getItem() == Items.WHEAT &&
                this.itemHandler.getStackInSlot(2).getItem() == Items.SUGAR &&
                this.mixerStatus == Status.WAITING
            ) {
                this.mixerStatus = Status.PROCESSING;


            }

            if (this.mixerStatus == Status.PROCESSING) {
                if (
                    this.itemHandler.getStackInSlot(0).getItem() == ModItems.BUTTER.get() &&
                    this.itemHandler.getStackInSlot(1).getItem() == Items.WHEAT &&
                    this.itemHandler.getStackInSlot(2).getItem() == Items.SUGAR
                ) {
                    tick++;
                } else {
                    tick = 0;
                    this.mixerStatus = Status.WAITING;
                }

            }

            if (tick > 230) {
                itemHandler.extractItem(0, 1, false);
                itemHandler.extractItem(1, 1, false);
                itemHandler.extractItem(2, 1, false);

                itemHandler.insertItem(3, new ItemStack(ModItems.CUPCAKE_DOUGH.get(), 1), false);
                tick = 0;
                this.mixerStatus = Status.WAITING;
            }
    }

    public int getProgress() {
        return this.tick / 10;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());

        return super.write(tag);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {

                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == ModItems.BUTTER.get();
                    case 1: return stack.getItem() == Items.WHEAT;
                    case 2: return stack.getItem() == Items.SUGAR;
                    case 3: return stack.getItem() == ModItems.CUPCAKE_DOUGH.get();
                    default: return false;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {

                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side){
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(capability, side);
    }


}
