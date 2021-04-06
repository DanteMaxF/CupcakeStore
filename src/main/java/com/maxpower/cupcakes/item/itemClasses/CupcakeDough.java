package com.maxpower.cupcakes.item.itemClasses;

import com.maxpower.cupcakes.CupcakesMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CupcakeDough extends Item {
    public CupcakeDough() {
        super(
                new Properties()
                    .group(CupcakesMod.CUPCAKES_TAB)
        );
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Hornea la masa para hacer un pastelillo"));

        super.addInformation(stack, world, tooltip, flag);
    }
}
