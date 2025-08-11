package com.example.sizechanger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

// import our networking helper
import com.example.sizechanger.ModNetworking;

@Environment(EnvType.CLIENT)
public class ClientGUI extends Screen {
    private float currentScale;

    private ClientGUI(float currentScale) {
        super(Text.translatable("screen.sizechanger.title"));
        this.currentScale = currentScale;
    }

    public static void open(float currentScale) {
        MinecraftClient.getInstance().setScreen(new ClientGUI(currentScale));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;
        // First row presets: 0.25x, 0.5x, 1.0x
        addPreset(cx - 150, cy - 10, 0.25f, "0.25×");
        addPreset(cx - 50, cy - 10, 0.5f, "0.5×");
        addPreset(cx + 50, cy - 10, 1.0f, "1.0×");
        // Second row presets: 2.0x, 3.0x, 4.0x
        addPreset(cx - 150, cy + 20, 2.0f, "2.0×");
        addPreset(cx - 50, cy + 20, 3.0f, "3.0×");
        addPreset(cx + 50, cy + 20, 4.0f, "4.0×");
    }

    private void addPreset(int x, int y, float scale, String label) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(label), btn -> {
            ModNetworking.sendScaleRequest(scale);
            this.close();
        }).dimensions(x, y, 80, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int cx = this.width / 2;
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, cx, 40, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.sizechanger.subtitle"), cx, 60, 0xA0A0A0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
