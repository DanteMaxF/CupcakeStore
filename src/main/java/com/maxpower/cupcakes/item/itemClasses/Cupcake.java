package com.maxpower.cupcakes.item.itemClasses;

import com.maxpower.cupcakes.CupcakesMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Cupcake extends Item {
    public Cupcake() {
        super(
                new Properties()
                    .group(CupcakesMod.CUPCAKES_TAB)
                    .food(
                            new Food.Builder()
                                .hunger(2)
                                .saturation(1.5f)
                                .build()
                    )
        );
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Vende este pastelillo para ganar dinero.\nTambien se puede comer ;)"));

        super.addInformation(stack, world, tooltip, flag);
    }
}
