package name.modid.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import name.modid.JunglesUniversalChallenges;

@Mixin(HungerManager.class)
public class HungerMixin {

	@Inject(method = "eat(Lnet/minecraft/component/type/FoodComponent;)V", at = @At("HEAD"), cancellable = true)
	private void preventEating(FoodComponent foodComponent, CallbackInfo ci) {
		if (JunglesUniversalChallenges.CONFIG.hunger_challenge()) {
			ci.cancel();
		}
	}

}