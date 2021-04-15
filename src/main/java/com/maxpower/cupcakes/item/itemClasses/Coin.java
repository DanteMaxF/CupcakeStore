package com.maxpower.cupcakes.item.itemClasses;

import com.maxpower.cupcakes.CupcakesMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Coin extends Item {

    public Coin() {
        super(
                new Properties()
                    .group(CupcakesMod.CUPCAKES_TAB)
        );
    }

}
