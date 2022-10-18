package com128.kzf.m.LegacyInputPatch.mixin;

import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
public class ScreenMixin {

    @Redirect(method = "handleKeyboard", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z"))
    private boolean inputPatch() {
        return Keyboard.getEventKeyState() || (Keyboard.getEventKey() == 0 && Character.isDefined(Keyboard.getEventCharacter()));
    }
}