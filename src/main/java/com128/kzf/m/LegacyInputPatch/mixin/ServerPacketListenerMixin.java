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
    public ServerPlayerEntity player;
    @Shadow
    private MinecraftServer server;

    @Overwrite
    public void onSignUpdate(SignUpdateS2CPacket packet) {
        this.player.updateLastActionTime();
        ServerWorld serverWorld = this.server.getWorld(this.player.dimension);
        if (serverWorld.isPosLoaded(packet.x, packet.y, packet.z)) {
            BlockEntity blockEntity = serverWorld.method_3781(packet.x, packet.y, packet.z);
            if (blockEntity instanceof SignBlockEntity) {
                SignBlockEntity signBlockEntity = (SignBlockEntity)blockEntity;
                if (!signBlockEntity.isEditable() || signBlockEntity.getEditor() != this.player) {
                    this.server.warn("Player " + this.player.getUsername() + " just tried to change non-editable sign");
                    return;
                }
            }
            int n;
            for (n = 0; n < 4; n++) {
                boolean bl = true;
                if (packet.text[n].length() > 15) {
                    bl = false;
                } else {
                    for (int n3 = 0; n3 < packet.text[n].length(); n3++) {
                        if (!SharedConstants.isValidChar(packet.text[n].charAt(n3)))
                            bl = false;
                    }
                }
                if (!bl)
                    packet.text[n] = "BadSignText";
            }
            if (blockEntity instanceof SignBlockEntity) {
                n = packet.x;
                int n2 = packet.y;
                int n3 = packet.z;
                SignBlockEntity signBlockEntity2 = (SignBlockEntity)blockEntity;
                System.arraycopy(packet.text, 0, signBlockEntity2.field_560, 0, 4);
                signBlockEntity2.markDirty();
                serverWorld.method_3709(packet.x, packet.y, packet.z);
            }
        }
    }

}