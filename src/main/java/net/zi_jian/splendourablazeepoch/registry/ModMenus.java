package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.menu.ForgingFurnaceMenu;
import net.zi_jian.splendourablazeepoch.menu.PrintTableMenu;

public final class ModMenus {

    public static final DeferredRegister<MenuType<?>>
            MENU_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.MENU_TYPES,
                    SplendourAblazeEpochMod.MOD_ID
            );

    public static final RegistryObject<
            MenuType<ForgingFurnaceMenu>>
            FORGING_FURNACE_GUI =
            MENU_TYPES.register(
                    "forgingfurnace_gui",
                    () -> IForgeMenuType.create(
                            ForgingFurnaceMenu::new
                    )
            );

    public static final RegistryObject<
            MenuType<PrintTableMenu>>
            PRINT_TABLE_GUI =
            MENU_TYPES.register(
                    "printtable_gui",
                    () -> IForgeMenuType.create(
                            PrintTableMenu::new
                    )
            );

    private ModMenus() {
    }
}
