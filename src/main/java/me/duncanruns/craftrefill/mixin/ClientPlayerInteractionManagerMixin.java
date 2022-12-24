package me.duncanruns.craftrefill.mixin;

import me.duncanruns.craftrefill.CraftRefill;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "clickRecipe", at = @At("TAIL"))
    private void clickRecipeMixin(int syncId, Recipe<?> recipe, boolean craftAll, CallbackInfo info) {
        // Store recipe on click
        CraftRefill.lastRecipe = recipe;
    }
}
