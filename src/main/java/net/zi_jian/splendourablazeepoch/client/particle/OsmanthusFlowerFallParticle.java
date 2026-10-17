package net.zi_jian.splendourablazeepoch.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public final class OsmanthusFlowerFallParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private float angularVelocity = 0.05F;
    private final float angularAcceleration = 0.01F;

    public OsmanthusFlowerFallParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        setSize(0.2F, 0.2F);
        lifetime = Math.max(1, 60 + random.nextInt(4) - 2);
        gravity = 0.1F;
        hasPhysics = true;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        setSpriteFromAge(sprites);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
        oRoll = roll;
        roll += angularVelocity;
        angularVelocity += angularAcceleration;
        if (!removed) {
            setSprite(sprites.get(age / 30 % 4 + 1, 4));
        }
    }
}
