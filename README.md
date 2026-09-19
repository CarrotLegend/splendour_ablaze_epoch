# splendour_ablaze_epoch

棠煌纪元从 MCreator 发布 JAR 向独立 Forge Java 工程迁移的第一阶段工作目录。

- Minecraft 1.20.1
- Forge 47.4.20
- Java 17
- official mappings 1.20.1

构建：

```bat
gradlew.bat build
```

当前工程包含从源 JAR 原样迁移的全部 `data/**` 与 `assets/**`，以及干净的 Java 入口和 registry 骨架。旧 MCreator 游戏逻辑尚未迁移，详情见 `MIGRATION_STAGE_1.md`。
