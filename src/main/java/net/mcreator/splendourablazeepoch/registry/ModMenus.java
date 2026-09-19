package net.mcreator.splendourablazeepoch.registry;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.minecraft.world.inventory.MenuType;
import net.mcreator.splendourablazeepoch.menu.ForgingFurnaceMenu;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SplendourAblazeEpochMod.MOD_ID);
    public static final RegistryObject<MenuType<ForgingFurnaceMenu>> FORGING_FURNACE_GUI =
            MENU_TYPES.register("forgingfurnace_gui", () -> IForgeMenuType.create(ForgingFurnaceMenu::new));

    private ModMenus() {
    }
}
