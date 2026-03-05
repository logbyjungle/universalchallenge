package name.modid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunglesUniversalChallenges implements ModInitializer {
	public static final String MOD_ID = "jungles-universal-challenges";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final name.modid.WrConfigScreen CONFIG = name.modid.WrConfigScreen.createAndLoad();

	@Override
	public void onInitialize() {

		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (!alive && CONFIG.hunger_challenge()) {
				newPlayer.getHungerManager().setFoodLevel(0);
				newPlayer.getHungerManager().setSaturationLevel(0.0f);
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {

			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (CONFIG.air_challenge()) {
					BlockPos pos = new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ());
					if (player.getEntityWorld().getBlockState(pos).isAir()) {
						player.kill(player.getEntityWorld());
					}
				}

				if (CONFIG.xp_challenge()) {
					int currentXp = player.totalExperience;
					if (currentXp > 0) {
						player.kill(player.getEntityWorld());
					}
				}

				if (CONFIG.light_challenge() || CONFIG.light_challenge_pure()) {
					ServerWorld world = player.getEntityWorld();
					BlockPos pos = player.getBlockPos();

					int internalSky = Math.max(0, world.getLightLevel(pos) - world.getLightLevel(LightType.BLOCK, pos));
					int blockLight = world.getLightLevel(LightType.BLOCK, pos);

					float damageAmount = 0.0f;
					if (CONFIG.light_challenge_pure()) {
						damageAmount = (float) Math.max(internalSky,blockLight);
					} else {
						damageAmount = (float) Math.max(internalSky-4,blockLight);
					}

					if (damageAmount > 0) {
						player.damage(world, world.getDamageSources().generic(), damageAmount);
					}

				}
			}







		});

	}
}