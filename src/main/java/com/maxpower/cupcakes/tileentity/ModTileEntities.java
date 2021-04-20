package com.maxpower.cupcakes.tileentity;

import com.maxpower.cupcakes.block.ModBlocks;
import com.maxpower.cupcakes.tileentity.tileEntityClasses.CupcakeShopTile;
import com.maxpower.cupcakes.tileentity.tileEntityClasses.MixerTile;
import com.maxpower.cupcakes.util.Registration;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntities {

    public static final RegistryObject<TileEntityType<MixerTile>> MIXER_TILE_ENTITY =
            Registration.TILE_ENTITY_TYPES.register("mixer_tile_entity", () -> TileEntityType.Builder.create(
                    () -> new MixerTile(), ModBlocks.MIXER.get()).build(null)
    );

    public static final RegistryObject<TileEntityType<CupcakeShopTile>> CUPCAKE_SHOP_TILE_ENTITY =
            Registration.TILE_ENTITY_TYPES.register("cupcake_shop_tile_entity", () -> TileEntityType.Builder.create(
                    () -> new CupcakeShopTile(), ModBlocks.CUPCAKE_SHOP.get()).build(null)
    );

    public static void register() { }

}
