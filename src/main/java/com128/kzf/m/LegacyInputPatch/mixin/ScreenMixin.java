package com128.kzf.m.LegacyInputPatch.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.OperatingSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.lwjgl.input.Keyboard;

@Mixin(Screen.class)
public class ScreenMixin extends DrawableHelper {
  @Shadow
  public static final boolean field_5093 = Minecraft.method_2940() == OperatingSystem.MACOS;
  @Shadow
  protected void keyPressed(char character, int code) {}
  @Shadow
  protected Minecraft field_1229;

  @Overwrite
  public void method_1040() {
    int var1 = Keyboard.getEventKey();
    char var2 = Keyboard.getEventCharacter();
    if (var1 == 0 && Character.isDefined(var2))
      this.keyPressed(var2, var1);
    if (Keyboard.getEventKeyState()) {
      if (var1 == 87) {
        this.field_1229.toggleFullscreen();
        return;
      }
      if (field_5093 && var1 == 28 && var2 == 0) {
        var1 = 29;
      }
      this.keyPressed(var2, var1);
    }
  }
}
