package com128.kzf.m.LegacyInputPatch.mixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.IdentifibleBooleanConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.lwjgl.input.Keyboard;

@Mixin(Screen.class)
public abstract class ScreenMixin extends DrawableHelper implements IdentifibleBooleanConsumer {
  @Shadow
  protected void keyPressed(char character, int code) {};
  @Shadow
  protected MinecraftClient client;

  @Overwrite
  public void method_6755() {
    int code = Keyboard.getEventKey();
    char character = Keyboard.getEventCharacter();
    if (Keyboard.getEventKeyState() || (code == 0 && Character.isDefined(character)))
      this.keyPressed(character, code);
    this.client.handleKeyInput();
  }
}
