package com128.kzf.m.LegacyInputPatch.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.SharedConstants;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin extends Screen {

    @Shadow
    private SignBlockEntity sign;
    @Shadow
    private int currentRow;
    @Shadow
    private ButtonWidget field_5732;

    @Overwrite
    protected void keyPressed(char character, int code) {
        if (code == 200) {
            this.currentRow = this.currentRow - 1 & 3;
        }
        if (code == 208 || code == 28 || code == 156) {
            this.currentRow = this.currentRow + 1 & 3;
        }
        if (code == 14 && this.sign.field_560[this.currentRow].length() > 0) {
            this.sign.field_560[this.currentRow] = this.sign.field_560[this.currentRow].substring(0, this.sign.field_560[this.currentRow].length() - 1);
        }
        if (SharedConstants.isValidChar(character) && this.sign.field_560[this.currentRow].length() < 15) {
            this.sign.field_560[this.currentRow] = this.sign.field_560[this.currentRow] + character;
        }
        if (code == 1) {
            this.buttonClicked(this.field_5732);
        }
    }
}