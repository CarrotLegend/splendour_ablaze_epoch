package net.mcreator.splendourablazeepoch.event;

import net.mcreator.splendourablazeepoch.SplendourAblazeEpochMod;
import net.mcreator.splendourablazeepoch.entity.SkyDoorEntity;
import net.mcreator.splendourablazeepoch.registry.ModItems;
import net.mcreator.splendourablazeepoch.world.SkyEmblemSavedData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SplendourAblazeEpochMod.MOD_ID)
public final class SkyEmblemEvents {
    private static final String GRANT_KEY = "splendourablazecompass";
    private static final Map<String, String> PICKUP_TIPS = Map.ofEntries(
            Map.entry("rawlead", "铅是个好东西，在铸造青铜和白镴的时候也需要铅"),
            Map.entry("rawtin", "锡矿是重要的合金材料"),
            Map.entry("classics", "用典籍可以制作顶世界的书架，有更强的附魔增益效果"),
            Map.entry("magnetite", "一种新的铁矿，不过要打造磁钢，你还需要普通的铁"),
            Map.entry("typefirst", "集齐六种铅字，就可以印刷复制出附魔书了"),
            Map.entry("typesecond", "集齐六种铅字，就可以印刷复制出附魔书了"),
            Map.entry("typethird", "集齐六种铅字，就可以印刷复制出附魔书了"),
            Map.entry("typefourth", "集齐六种铅字，就可以印刷复制出附魔书了"),
            Map.entry("typefifth", "集齐六种铅字，就可以印刷复制出附魔书了"),
            Map.entry("typesixth", "集齐六种铅字，就可以印刷复制出附魔书了")
    );

    @SubscribeEvent
    public static void joined(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) grantOnFirstEntry(player);
    }

    @SubscribeEvent
    public static void changedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) grantOnFirstEntry(player);
    }

    private static void grantOnFirstEntry(ServerPlayer player) {
        if (player.level().dimension() != SkyDoorEntity.TOPWORLD || player.getPersistentData().getBoolean(GRANT_KEY)) return;
        if (player.getInventory().add(new ItemStack(ModItems.SKY_EMBLEM.get()))) {
            player.getPersistentData().putBoolean(GRANT_KEY, true);
            player.sendSystemMessage(Component.literal("你好，穿越天空之人，欢迎来到顶世界......"));
            player.sendSystemMessage(Component.literal("如果需要提示请把我放在副手，我会帮助你在棠煌纪元里活下去....."));
        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.level.isClientSide || event.level.getServer() == null) return;
        SkyEmblemSavedData data = SkyEmblemSavedData.get(event.level.getServer());
        data.yes++;
        if (data.yes == 800.0D) {
            data.yes = 0.0D;
            data.say = true;
            data.normal = true;
        }
        data.setDirty();
    }

    @SubscribeEvent
    public static void pickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer) || player.level().dimension() != SkyDoorEntity.TOPWORLD
                || !player.getOffhandItem().is(ModItems.SKY_EMBLEM.get())) return;
        SkyEmblemSavedData data = SkyEmblemSavedData.get(serverPlayer.server);
        if (!data.say) return;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(event.getItem().getItem().getItem());
        String tip = id.getNamespace().equals(SplendourAblazeEpochMod.MOD_ID) ? PICKUP_TIPS.get(id.getPath()) : null;
        if (tip == null && id.equals(new ResourceLocation("minecraft", "raw_copper"))) tip = "铜在顶世界相当有用";
        if (tip == null && (id.equals(new ResourceLocation("minecraft", "raw_iron")) || id.equals(new ResourceLocation("minecraft", "iron_ingot")))) tip = "是你的老朋友啊";
        if (tip != null) player.sendSystemMessage(Component.literal(tip));
        data.say = false;
        data.setDirty();
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)
                || player.level().dimension() != SkyDoorEntity.TOPWORLD || !player.getOffhandItem().is(ModItems.SKY_EMBLEM.get())) return;
        SkyEmblemSavedData data = SkyEmblemSavedData.get(player.server);
        if (!data.normal || player.getRandom().nextInt(11) != 1) return;
        ResourceLocation biome = player.level().getBiome(player.blockPosition()).unwrapKey().map(key -> key.location()).orElse(null);
        if (biome == null) return;
        String tip = switch (biome.getPath()) {
            case "battlefieldwasteland" -> "小心，那些漂浮的信炮，被抓住了要尽快潜行脱离";
            case "bambusmulberrywetland" -> "这些竹子也能代替木材，额，我想你大概也知道......";
            case "grazingmoonfield" -> "牧月田野相对比较安全，很适合建造基地";
            case "descenjadeshoal" -> "需要一些线吗，生长在这里的黄麻能帮你大忙";
            case "splendourablazehills" -> "这里不是个旅行的好地方";
            default -> "找不到轩辕台吗？试试用四个玄铁石和典籍合成定位器卷轴";
        };
        player.sendSystemMessage(Component.literal(tip));
    }

    private SkyEmblemEvents() {}
}
