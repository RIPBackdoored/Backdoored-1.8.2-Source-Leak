package com.backdoored.subguis;

import org.lwjgl.input.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

public class BetterSignGui extends GuiScreen
{
    public final TileEntitySign tileEntitySign;
    private static int bak;
    private List<GuiTextField> bal;
    private String[] bam;
    
    public BetterSignGui(final TileEntitySign tileEntitySign) {
        super();
        this.tileEntitySign = tileEntitySign;
    }
    
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.bal = new LinkedList<GuiTextField>();
        this.bam = new String[4];
        for (int i = 0; i < 4; ++i) {
            final GuiTextField guiTextField = new GuiTextField(i, this.fontRenderer, this.width / 2 + 4, 75 + i * 24, 120, 20);
            guiTextField.setValidator((Predicate)this::a);
            guiTextField.setMaxStringLength(100);
            guiTextField.setText(this.bam[i] = this.tileEntitySign.signText[i].getUnformattedText());
            this.bal.add(guiTextField);
        }
        this.bal.get(BetterSignGui.bak).setFocused(true);
        this.addButton(new GuiButton(4, this.width / 2 + 5, this.height / 4 + 120, 120, 20, "Done"));
        this.addButton(new GuiButton(5, this.width / 2 - 125, this.height / 4 + 120, 120, 20, "Cancel"));
        this.addButton(new GuiButton(6, this.width / 2 - 41, 147, 40, 20, "Shift"));
        this.addButton(new GuiButton(7, this.width / 2 - 41, 123, 40, 20, "Clear"));
        this.tileEntitySign.setEditable(false);
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final int bak = BetterSignGui.bak;
        this.bal.forEach(guiTextField -> guiTextField.mouseClicked(n, n2, n3));
        this.a();
        if (BetterSignGui.bak == bak && !this.bal.get(BetterSignGui.bak).isFocused()) {
            this.bal.get(BetterSignGui.bak).setFocused(true);
        }
    }
    
    protected void keyTyped(final char c, final int n) {
        switch (n) {
            case 1: {
                this.b();
                return;
            }
            case 15: {
                this.a(isShiftKeyDown() ? -1 : 1);
                return;
            }
            case 200: {
                this.a(-1);
                return;
            }
            case 28:
            case 156:
            case 208: {
                this.a(1);
                break;
            }
        }
        this.bal.forEach(guiTextField -> guiTextField.textboxKeyTyped(c, n));
        this.tileEntitySign.signText[BetterSignGui.bak] = (ITextComponent)new TextComponentString(this.bal.get(BetterSignGui.bak).getText());
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);
        switch (guiButton.id) {
            case 5: {
                for (int i = 0; i < 4; ++i) {
                    this.tileEntitySign.signText[i] = (ITextComponent)new TextComponentString(this.bam[i]);
                }
            }
            case 4: {
                this.b();
                break;
            }
            case 6: {
                final String[] array = new String[4];
                for (int j = 0; j < 4; ++j) {
                    array[j] = this.tileEntitySign.signText[this.b(j + (isShiftKeyDown() ? 1 : -1))].getUnformattedText();
                }
                final Object o;
                final int n;
                this.bal.forEach(guiTextField2 -> {
                    guiTextField2.getId();
                    guiTextField2.setText(o[n]);
                    this.tileEntitySign.signText[n] = (ITextComponent)new TextComponentString(o[n]);
                    return;
                });
                break;
            }
            case 7: {
                final int n2;
                this.bal.forEach(guiTextField -> {
                    guiTextField.getId();
                    guiTextField.setText("");
                    this.tileEntitySign.signText[n2] = (ITextComponent)new TextComponentString("");
                    return;
                });
                break;
            }
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2 - 63.0f, 0.0f, 50.0f);
        GlStateManager.scale(-93.75f, -93.75f, -93.75f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        if (this.tileEntitySign.getBlockType() == Blocks.STANDING_SIGN) {
            GlStateManager.rotate(this.tileEntitySign.getBlockMetadata() * 360 / 16.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int blockMetadata = this.tileEntitySign.getBlockMetadata();
            float n4 = 0.0f;
            if (blockMetadata == 2) {
                n4 = 180.0f;
            }
            if (blockMetadata == 4) {
                n4 = 90.0f;
            }
            if (blockMetadata == 5) {
                n4 = -90.0f;
            }
            GlStateManager.rotate(n4, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.7625f, 0.0f);
        }
        this.tileEntitySign.lineBeingEdited = -1;
        TileEntityRendererDispatcher.instance.render((TileEntity)this.tileEntitySign, -0.5, -0.75, -0.5, 0.0f);
        GlStateManager.popMatrix();
        this.bal.forEach(GuiTextField::func_146194_f);
        super.drawScreen(n, n2, n3);
    }
    
    public void a() {
        this.bal.forEach(guiTextField -> {
            if (guiTextField.isFocused()) {
                BetterSignGui.bak = guiTextField.getId();
            }
        });
    }
    
    public void b() {
        this.tileEntitySign.markDirty();
        this.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient connection = this.mc.getConnection();
        if (connection != null) {
            connection.sendPacket((Packet)new CPacketUpdateSign(this.tileEntitySign.getPos(), this.tileEntitySign.signText));
        }
        this.tileEntitySign.setEditable(true);
    }
    
    public void a(final int n) {
        this.bal.get(BetterSignGui.bak).setFocused(false);
        BetterSignGui.bak = this.b(BetterSignGui.bak + n);
        this.bal.get(BetterSignGui.bak).setFocused(true);
    }
    
    public int b(final int n) {
        if (n > 3) {
            return 0;
        }
        if (n < 0) {
            return 3;
        }
        return n;
    }
    
    public boolean a(final String s) {
        if (this.fontRenderer.getStringWidth(s) > 90) {
            return false;
        }
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (!ChatAllowedCharacters.isAllowedCharacter(charArray[i])) {
                return false;
            }
        }
        return true;
    }
    
    private static /* synthetic */ void a(final GuiTextField guiTextField) {
        if (guiTextField.isFocused()) {
            BetterSignGui.bak = guiTextField.getId();
        }
    }
    
    private /* synthetic */ void b(final GuiTextField guiTextField) {
        final int id = guiTextField.getId();
        guiTextField.setText("");
        this.tileEntitySign.signText[id] = (ITextComponent)new TextComponentString("");
    }
    
    private /* synthetic */ void a(final String[] array, final GuiTextField guiTextField) {
        final int id = guiTextField.getId();
        guiTextField.setText(array[id]);
        this.tileEntitySign.signText[id] = (ITextComponent)new TextComponentString(array[id]);
    }
    
    private static /* synthetic */ void a(final char c, final int n, final GuiTextField guiTextField) {
        guiTextField.textboxKeyTyped(c, n);
    }
    
    private static /* synthetic */ void a(final int n, final int n2, final int n3, final GuiTextField guiTextField) {
        guiTextField.mouseClicked(n, n2, n3);
    }
    
    static {
        BetterSignGui.bak = 0;
    }
}
