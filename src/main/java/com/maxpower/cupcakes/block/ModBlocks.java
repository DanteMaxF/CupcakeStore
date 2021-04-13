package com.maxpower.cupcakes.block;

import com.maxpower.cupcakes.CupcakesMod;
import com.maxpower.cupcakes.block.blockClasses.Mixer;
import com.maxpower.cupcakes.util.Registration;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> MIXER =
            register(
                    "mixer",
                    () -> new Mixer(
                            AbstractBlock.Properties.create(Material.IRON)
                                .hardnessAndResistance(4f)
                                .harvestTool(ToolType.PICKAXE)
                    )
            );

    public static void register() { }

    private static <T extends Block>RegistryObject<T> register (String name, Supplier<T> block) {
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().group(CupcakesMod.CUPCAKES_TAB)));

        return toReturn;
    }

}
