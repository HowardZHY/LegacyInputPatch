package com128.kzf.m.LegacyInputPatch.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.SignUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerPacketListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPacketListener.class)
public abstract class ServerPacketListenerMixin extends PacketListener {
    @Shadow
    private ServerPlayerEntity player;
    @Shadow
    private MinecraftServer server;

    @Overwrite
    public void onSignUpdate(SignUpdateS2CPacket packet) {
        ServerWorld var2 = this.server.getWorld(this.player.dimension);
        if (var2.isPosLoaded(packet.x, packet.y, packet.z)) {
            BlockEntity var3 = var2.method_3781(packet.x, packet.y, packet.z);
            if (var3 instanceof SignBlockEntity) {
                SignBlockEntity var4 = (SignBlockEntity)var3;
                if (!var4.isEditable()) {
                    this.server.warn("Player " + this.player.username + " just tried to change non-editable sign");
                    return;
                }
            }
            int var6;
            int var8;
            for(var8 = 0; var8 < 4; ++var8) {
                boolean var5 = true;
                if (packet.text[var8].length() > 15) {
                    var5 = false;
                } else {
                    for(var6 = 0; var6 < packet.text[var8].length(); ++var6) {
                        if (!SharedConstants.isValidChar(packet.text[var8].charAt(var6))){
                            var5 = false;
                        }
                    }
                }
                if (!var5) {
                    packet.text[var8] = "!?";
                }
            }
            if (var3 instanceof SignBlockEntity) {
                var8 = packet.x;
                int var9 = packet.y;
                var6 = packet.z;
                SignBlockEntity var7 = (SignBlockEntity)var3;
                System.arraycopy(packet.text, 0, var7.field_560, 0, 4);
                var7.markDirty();
                var2.method_3709(var8, var9, var6);
            }
        }
    }

}