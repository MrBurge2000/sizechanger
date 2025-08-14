package com.example.sizechanger;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Handles the networking for changing player scale.  A client can send a
 * single float representing the desired scale to the server, which will
 * validate the cost, consume slimeballs, update the stored size and
 * notify the player.
 */
public class ModNetworking {
    /** Packet identifier for scale requests. */
    public static final Identifier REQ_SET_SCALE = SizechangerMod.id("req_set_scale");

    /**
     * Register the server-side global receiver for scale change requests.
     * This should be called during mod initialization.
     */
    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(REQ_SET_SCALE, (payload, context) -> {
            float target = payload.readFloat();
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                float current = SizeState.get(player);
                int cost = SizeState.cost(current, target);
                // Count available slimeballs
                int have = player.getInventory().count(Items.SLIME_BALL);
                if (have < cost) {
                    player.sendMessage(Text.translatable("msg.sizechanger.need_slime", cost), true);
                    return;
                }
                // Consume slimeballs
                int remaining = cost;
                for (int i = 0; i < player.getInventory().size() && remaining > 0; i++) {
                    ItemStack st = player.getInventory().getStack(i);
                    if (st.isOf(Items.SLIME_BALL)) {
                        int take = Math.min(st.getCount(), remaining);
                        st.decrement(take);
                        remaining -= take;
                    }
                }
                // Apply the new scale
                SizeState.set(player, target);
                // Notify the player
                player.sendMessage(Text.translatable("msg.sizechanger.set", String.format("%.2f", target)), true);
            });
        });
    }

    /**
     * Send a request to change the player's scale to the given value.  This
     * should only be called on the client.
     *
     * @param to the desired scale
     */
    public static void sendScaleRequest(float to) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        buf.writeFloat(to);
        ClientPlayNetworking.send(REQ_SET_SCALE, buf);
    }
}