package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;
import net.zi_jian.splendourablazeepoch.enchantment.InorganicDestructionEnchantment;

public final class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<Enchantment> INORGANIC_DESTRUCTION =
            ENCHANTMENTS.register("inorganicdestruction", InorganicDestructionEnchantment::new);

    private ModEnchantments() {
    }
}
