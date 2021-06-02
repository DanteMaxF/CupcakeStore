package com.maxpower.cupcakes.tileentity.tileEntityClasses;

import com.maxpower.cupcakes.item.ModItems;
import com.maxpower.cupcakes.tileentity.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stat;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ShopTile extends TileEntity implements ITickableTileEntity {

    static enum Status {
        WAITING,
        PROCESSING,
        DONE
    }

    private Status shopStatus = Status.WAITING;
    private int price = 0;
    private int demandFunction = 0;
    private int demand = 0;

    private int cupcakesInput = 0;
    private int cupcakesToSell = 0;
    private int moneyToGive = 0;

    private int tick = 0;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public ShopTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ShopTile() {
        this(ModTileEntities.SHOP_TILE_ENTITY.get());
    }

    public void setupDemand() {
        Random rand = new Random();
        this.demandFunction = rand.nextInt(3);
        this.demand = rand.nextInt(6) + 1;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
        switch (this.demandFunction){
            case 0:
                this.cupcakesToSell = this.demandCurve0();
                break;
            case 1:
                this.cupcakesToSell = this.demandCurve1();
                break;
            default:
                this.cupcakesToSell = this.demandCurve2();
        }
    }

    private int demandCurve0() {
        System.out.println("F0. (-" + this.price + "+64)/" + this.demand + " = " + (-this.price+64)/this.demand);

        return (-this.price+64)/this.demand;
    }

    private int demandCurve1() {
        System.out.println("F1. (-" + this.price + "+64)/" + this.demand + " = " + (-this.price+64)/this.demand);
        return (-this.price+64)/this.demand;
    }

    private int demandCurve2() {
        System.out.println("F2. (-" + this.price + "+64)/" + this.demand + " = " + (-this.price+64)/this.demand);
        return (-this.price+64)/this.demand;
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }

    @Override
    public void tick() {

        if (
            this.itemHandler.getStackInSlot(0).getItem() == ModItems.CUPCAKE.get() &&
            this.itemHandler.getStackInSlot(2).getCount() > 0 &&
            this.shopStatus == Status.WAITING
            ) {

                this.cupcakesInput = this.itemHandler.getStackInSlot(0).getCount();
                this.setPrice(this.itemHandler.getStackInSlot(2).getCount());
                if (this.cupcakesToSell > this.cupcakesInput) {
                    this.cupcakesToSell = this.cupcakesInput;
                }
                System.out.println("TIENDA ABIERTA!!! CUPCAKES EN VENTA: " + this.cupcakesInput + " " + this.cupcakesToSell);

                itemHandler.extractItem(0, cupcakesInput, false);
                this.shopStatus = Status.PROCESSING;

        }

        if (this.shopStatus == Status.PROCESSING) {
            if (this.itemHandler.getStackInSlot(2).getCount() > 0) {
                tick++;
            } else {
                tick = 0;
                itemHandler.insertItem(0, new ItemStack(ModItems.CUPCAKE.get(), cupcakesInput), false);
                this.shopStatus = Status.WAITING;
            }
        }

        if (tick > 230 && this.shopStatus == Status.PROCESSING && this.itemHandler.getStackInSlot(2).getCount() > 0) {
            tick = 0;
            this.shopStatus = Status.DONE;


            itemHandler.insertItem(0, new ItemStack(ModItems.CUPCAKE.get(), cupcakesInput - cupcakesToSell), false);

            // Distribute the money on the money slots

            moneyToGive = cupcakesToSell * price;
            if (moneyToGive <= 64) {
                itemHandler.insertItem(1, new ItemStack(ModItems.COIN.get(), moneyToGive), false);
            } else if (moneyToGive <= 128) {
                itemHandler.insertItem(1, new ItemStack(ModItems.COIN.get(), 64), false);
                itemHandler.insertItem(3, new ItemStack(ModItems.COIN.get(), moneyToGive - 64), false);
            } else if (moneyToGive <= 192) {
                itemHandler.insertItem(1, new ItemStack(ModItems.COIN.get(), 64), false);
                itemHandler.insertItem(3, new ItemStack(ModItems.COIN.get(), 64), false);
                itemHandler.insertItem(4, new ItemStack(ModItems.COIN.get(), moneyToGive - 128), false);
            }
            System.out.println("TIENDA CERRADA!!!!! CUPCAKES VENDIDOS: " + cupcakesToSell + " = $ " + moneyToGive);
        }

        if (this.shopStatus == Status.DONE && this.itemHandler.getStackInSlot(0).getCount() == 0) {
            this.shopStatus = Status.WAITING;
        }
    }

    public int getProgress() {
        return tick / 10;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));

        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());

        return super.write(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == ModItems.CUPCAKE.get();
                    case 1:
                    case 3:
                    case 4:
                        return stack.getItem() == ModItems.COIN.get();
                    case 2: return stack.getItem() == stack.getItem();
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

}
