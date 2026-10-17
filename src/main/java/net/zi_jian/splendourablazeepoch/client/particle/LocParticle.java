package net.zi_jian.splendourablazeepoch.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public final class LocParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public LocParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        setSize(0.2F, 0.2F);
        lifetime = 60;
        gravity = 0.0F;
        hasPhysics = false;
        this.xd = xd * 0.1D;
        this.yd = yd * 0.1D;
        this.zd = zd * 0.1D;
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
        if (!removed) {
            setSprite(sprites.get(age / 60 % 1 + 1, 1));
        }
    }
}
