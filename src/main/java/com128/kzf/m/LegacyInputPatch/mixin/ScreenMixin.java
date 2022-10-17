package com128.kzf.m.LegacyInputPatch.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.lwjgl.input.Keyboard;

@Mixin(Screen.class)
public class ScreenMixin extends DrawableHelper {
  @Shadow
  protected void keyPressed(char character, int code) {
  }

  ;
  @Shadow
  protected MinecraftClient client;

  @Overwrite
  public void method_1040() {
    int var1 = Keyboard.getEventKey();
    char var2 = Keyboard.getEventCharacter();
    if (var1 == 0 && Character.isDefined(var2))
      this.keyPressed(var2, var1);
    if (Keyboard.getEventKeyState()) {
      if (var1 == 87) {
        this.client.toggleFullscreen();
        return;
      }
      this.keyPressed(var2, var1);
    }
  }
}
