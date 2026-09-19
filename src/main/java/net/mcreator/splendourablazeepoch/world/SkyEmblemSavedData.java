package net.mcreator.splendourablazeepoch.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public final class SkyEmblemSavedData extends SavedData {
    public static final String FILE_NAME = "splendour_ablaze_epoch_mapvars";
    public double yes;
    public boolean say;
    public boolean normal;
    public double ticking;
    public boolean fly;

    public static SkyEmblemSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(SkyEmblemSavedData::load, SkyEmblemSavedData::new, FILE_NAME);
    }

    public static SkyEmblemSavedData load(CompoundTag tag) {
        SkyEmblemSavedData data = new SkyEmblemSavedData();
        data.yes = tag.getDouble("yes");
        data.say = tag.getBoolean("say");
        data.normal = tag.getBoolean("normal");
        data.ticking = tag.getDouble("ticking");
        data.fly = tag.getBoolean("fly");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putDouble("yes", yes);
        tag.putBoolean("say", say);
        tag.putBoolean("normal", normal);
        tag.putDouble("ticking", ticking);
        tag.putBoolean("fly", fly);
        return tag;
    }
}
