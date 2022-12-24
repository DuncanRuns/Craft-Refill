package me.duncanruns.craftrefill.mixin;

import me.duncanruns.craftrefill.CraftRefill;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {CraftingScreen.class, InventoryScreen.class})
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> {
    public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Check if shift-space
        if (keyCode == GLFW.GLFW_KEY_SPACE && (modifiers & 0x1) == 1) {
            // Fill recipe if it exists
            if (CraftRefill.lastRecipe != null) {
                client.getNetworkHandler().sendPacket(new CraftRequestC2SPacket(client.player.currentScreenHandler.syncId, CraftRefill.lastRecipe, true));
            }
            // Return
            return true;
        }
        // Otherwise do regular keypress checks
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
