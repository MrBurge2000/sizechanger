package com.example.sizechanger;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier REQ_SET_SCALE = SizechangerMod.id("req_set_scale");

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(REQ_SET_SCALE, (payload, context) -> {
            float target = payload.readFloat();
            context.server().execute(() -> {
                ServerPlayerEntity p = context.player();
                float current = SizeState.get(p);
                int cost = SizeState.cost(current, target);
                int have = p.getInventory().count(Items.SLIME_BALL);
                if (have < cost) {
                    p.sendMessage(Text.translatable("msg.sizechanger.need_slime", cost), true);
                    return;
                }
                int remaining = cost;
                for (int i = 0; i < p.getInventory().size() && remaining > 0; i++) {
                    var st = p.getInventory().getStack(i);
                    if (st.isOf(Items.SLIME_BALL)) {
                        int take = Math.min(st.getCount(), remaining);
                        st.decrement(take);
                        remaining -= take;
                    }
                }
                SizeState.set(p, target);
                p.sendMessage(Text.translatable("msg.sizechanger.set", String.format("%.2f", target)), true);
            });
        });
    }

    public static void sendScaleRequest(float to) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        buf.writeFloat(to);
        ClientPlayNetworking.send(REQ_SET_SCALE, buf);
    }
}
