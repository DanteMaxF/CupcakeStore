package com.maxpower.cupcakes.item;

import com.maxpower.cupcakes.item.itemClasses.Cupcake;
import com.maxpower.cupcakes.item.itemClasses.CupcakeDough;
import com.maxpower.cupcakes.util.Registration;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {

    public static final RegistryObject<Item> CUPCAKE =
            Registration.ITEMS.register(
                    "cupcake",
                    () -> new Cupcake()
            );

    public static final RegistryObject<Item> CUPCAKE_DOUGH =
            Registration.ITEMS.register(
                    "cupcake_dough",
                    () -> new CupcakeDough()
            );

    public static void register() {

    }

}
