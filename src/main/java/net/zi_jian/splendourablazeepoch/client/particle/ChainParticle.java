package net.zi_jian.splendourablazeepoch.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public final class ChainParticle extends TextureSheetParticle {
    private float angularVelocity = 0.2F;

    public ChainParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z);
        setSize(0.2F, 0.2F);
        lifetime = 7;
        gravity = 0.0F;
        hasPhysics = true;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        pickSprite(sprites);
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
    }
}
