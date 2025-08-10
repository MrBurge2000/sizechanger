package com.example.sizechanger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ClientGUI extends Screen {
    private final float currentScale;

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
        // Add preset buttons
        addPreset(cx - 100, cy - 10, 0.5f, "0.5×");
        addPreset(cx - 50, cy - 10, 1.0f, "1.0×");
        addPreset(cx, cy - 10, 1.5f, "1.5×");
        addPreset(cx + 50, cy - 10, 2.0f, "2.0×");
    }

    private void addPreset(int x, int y, float scale, String label) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(label), b -> {
            ModNetworking.sendScaleRequest(scale);
            this.close();
        }).dimensions(x, y, 45, 20).build());
    }

    @Override
    public void render(DrawContext dc, int mouseX, int mouseY, float delta) {
        this.renderBackground(dc);
        int cx = this.width / 2;
        dc.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.sizechanger.title"), cx, 40, 0xFFFFFF);
        dc.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.sizechanger.subtitle"), cx, 58, 0xA0A0A0);
        super.render(dc, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
