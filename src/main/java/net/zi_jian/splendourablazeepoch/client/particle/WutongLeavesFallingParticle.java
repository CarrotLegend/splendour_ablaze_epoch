package net.zi_jian.splendourablazeepoch.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public final class WutongLeavesFallingParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public WutongLeavesFallingParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites, boolean fast) {
        super(level, x, y, z);
        this.sprites = sprites;
        setSize(0.2F, 0.2F);
        lifetime = Math.max(1, 300 + random.nextInt(fast ? 20 : 40) - (fast ? 10 : 20));
        gravity = fast ? 0.1F : 0.01F;
        hasPhysics = true;
        double scale = fast ? 0.1D : 0.01D;
        this.xd = xd * scale;
        this.yd = yd * scale;
        this.zd = zd * scale;
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
            setSprite(sprites.get(age / 2 % 1 + 1, 1));
        }
    }
}
