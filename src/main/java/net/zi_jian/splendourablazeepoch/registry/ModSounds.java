package net.zi_jian.splendourablazeepoch.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zi_jian.splendourablazeepoch.SplendourAblazeEpochMod;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SplendourAblazeEpochMod.MOD_ID);

    public static final RegistryObject<SoundEvent> MAGPIE_1 = register("magpie1");
    public static final RegistryObject<SoundEvent> MAGPIE_2 = register("magpie2");
    public static final RegistryObject<SoundEvent> MAGPIE_3 = register("magpie3");
    public static final RegistryObject<SoundEvent> MUSK_DEER_1 = register("musk_deer1");
    public static final RegistryObject<SoundEvent> MUSK_DEER_2 = register("musk_deer2");
    public static final RegistryObject<SoundEvent> PEACOCK_1 = register("peacock1");
    public static final RegistryObject<SoundEvent> PEACOCK_2 = register("peacock2");
    public static final RegistryObject<SoundEvent> PEACOCK_3 = register("peacock3");
    public static final RegistryObject<SoundEvent> WORM_1 = register("worm1");
    public static final RegistryObject<SoundEvent> WORM_2 = register("worm2");
    public static final RegistryObject<SoundEvent> PAGE_1 = register("page1");
    public static final RegistryObject<SoundEvent> PAGE_2 = register("page2");
    public static final RegistryObject<SoundEvent> PAGE_3 = register("page3");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(
                id,
                () -> SoundEvent.createVariableRangeEvent(
                        new ResourceLocation(SplendourAblazeEpochMod.MOD_ID, id)
                )
        );
    }

    private ModSounds() {
    }
}
