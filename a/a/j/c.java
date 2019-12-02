package a.a.j;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import com.google.common.base.*;
import com.google.common.io.*;
import a.a.a.*;
import com.mojang.authlib.exceptions.*;
import java.io.*;

public class c extends GuiScreen
{
    private GuiScreen guiScreen;
    private GuiTextField guiTextField;
    private GuiTextField guiTextField;
    private String baq;
    
    public c(final GuiScreen guiScreen) {
        super();
        this.baq = "";
        this.guiScreen = guiScreen;
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.guiTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 4 + 60 + 0, 202, 20);
        this.guiTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, this.height / 4 + 60 + 26, 202, 20);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 60 + 52, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 60 + 76, "Cancel"));
        this.guiTextField.setMaxStringLength(500);
        this.guiTextField.setMaxStringLength(500);
        super.initGui();
        try {
            final String read = Files.asCharSource(new File("Backdoored/accounts.txt"), Charsets.UTF_8).read();
            if (!read.isEmpty()) {
                final String[] split = read.split(":");
                try {
                    if (!a.a(split[0].trim(), split[1].trim())) {
                        System.out.println("Could not log in");
                        this.baq = "Could not log in";
                        return;
                    }
                }
                catch (AuthenticationException ex) {
                    ex.printStackTrace();
                    System.out.println("Could not log in: " + ex.toString());
                    this.baq = "Could not log in: " + ex.toString();
                    return;
                }
                this.mc.displayGuiScreen(this.guiScreen);
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void updateScreen() {
        this.guiTextField.updateCursorCounter();
        this.guiTextField.updateCursorCounter();
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.guiTextField.mouseClicked(n, n2, n3);
        this.guiTextField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            System.out.println("Attempting subguis, username: " + this.guiTextField.getText().trim());
            try {
                if (!a.a(this.guiTextField.getText().trim(), this.guiTextField.getText().trim())) {
                    System.out.println("Could not log in");
                    this.baq = "Could not log in";
                    return;
                }
            }
            catch (AuthenticationException ex) {
                ex.printStackTrace();
                System.out.println("Could not log in: " + ex.toString());
                this.baq = "Could not log in: " + ex.toString();
                return;
            }
            this.mc.displayGuiScreen(this.guiScreen);
        }
        else if (guiButton.id == 2) {
            this.mc.displayGuiScreen(this.guiScreen);
        }
    }
    
    protected void keyTyped(final char c, final int n) {
        this.guiTextField.textboxKeyTyped(c, n);
        this.guiTextField.textboxKeyTyped(c, n);
        if (c == '\t') {
            if (this.guiTextField.isFocused()) {
                this.guiTextField.setFocused(false);
                this.guiTextField.setFocused(true);
            }
            else if (this.guiTextField.isFocused()) {
                this.guiTextField.setFocused(false);
                this.guiTextField.setFocused(false);
            }
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Backdoored Client: Login to Minecraft", this.width / 2, 2, 16777215);
        this.drawString(this.fontRenderer, "Email", this.width / 2 - 100 - 50, this.height / 4 + 60 + 0 + 6, 16777215);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100 - 50, this.height / 4 + 60 + 26 + 6, 16777215);
        this.drawCenteredString(this.fontRenderer, this.baq, this.width / 2, this.height / 4 + 60 + 100, 16711680);
        try {
            this.guiTextField.drawTextBox();
            this.guiTextField.drawTextBox();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.drawScreen(n, n2, n3);
    }
}
