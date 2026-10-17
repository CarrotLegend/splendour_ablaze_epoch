package net.zi_jian.splendourablazeepoch.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public final class LegacyParticleProvider implements ParticleProvider<SimpleParticleType> {
    public enum Kind {
        WUTONG,
        WUTONG_0,
        OSMANTHUS,
        CHAIN,
        LOC,
        FLOWING
    }

    private final SpriteSet sprites;
    private final Kind kind;

    public LegacyParticleProvider(SpriteSet sprites, Kind kind) {
        this.sprites = sprites;
        this.kind = kind;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        return switch (kind) {
            case WUTONG -> new WutongLeavesFallingParticle(level, x, y, z, xd, yd, zd, sprites, false);
            case WUTONG_0 -> new WutongLeavesFallingParticle(level, x, y, z, xd, yd, zd, sprites, true);
            case OSMANTHUS -> new OsmanthusFlowerFallParticle(level, x, y, z, xd, yd, zd, sprites);
            case CHAIN -> new ChainParticle(level, x, y, z, xd, yd, zd, sprites);
            case LOC -> new LocParticle(level, x, y, z, xd, yd, zd, sprites);
            case FLOWING -> new FlowingParticle(level, x, y, z, xd, yd, zd, sprites);
        };
    }
}
