package club.bluezenith.module.modules.render.targethuds.components.provider;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface ProgressProvider {
    float getProgress(EntityPlayer target);
}
