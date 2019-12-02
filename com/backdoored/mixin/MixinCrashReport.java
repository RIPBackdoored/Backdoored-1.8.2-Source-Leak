package com.backdoored.mixin;

import net.minecraft.crash.*;
import org.spongepowered.asm.mixin.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.backdoored.*;
import javax.swing.border.*;
import a.a.a.*;
import com.google.common.base.*;
import java.awt.*;
import javax.swing.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ CrashReport.class })
public class MixinCrashReport
{
    @Final
    @Shadow
    private Throwable field_71511_b;
    
    public MixinCrashReport() {
        super();
    }
    
    @Redirect(method = { "getCompleteReport" }, at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    public String interceptReport(final StringBuilder a1) {
        try {
            return DrmManager.a(a1);
        }
        catch (Throwable t) {
            return a1.toString();
        }
    }
    
    @Inject(method = { "saveToFile" }, at = { @At("RETURN") })
    private void showDialog(final File v-7, final CallbackInfoReturnable<Boolean> v-6) {
        if (Globals.mc.isFullScreen()) {
            Globals.mc.toggleFullscreen();
        }
        final Frame frame = new Frame();
        frame.setAlwaysOnTop(true);
        frame.setState(1);
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        final String completeReport = ((CrashReport)this).getCompleteReport();
        String message = null;
        try {
            final String a1 = c.a("http://paste.dimdev.org", "mccrash", completeReport);
        }
        catch (Exception a2) {
            message = a2.getMessage();
        }
        final JTextArea textArea = new JTextArea("Uploaded crash report: " + message + "\n" + Throwables.getStackTraceAsString(this.cause));
        textArea.setEditable(false);
        final JScrollPane v0 = new JScrollPane(textArea, 22, 32);
        panel.add(v0);
        StackTraceElement v2;
        if (this.cause.getStackTrace().length > 0) {
            v2 = this.cause.getStackTrace()[0];
        }
        else {
            v2 = new StackTraceElement("", "", "", -1);
        }
        JOptionPane.showMessageDialog(frame, panel, "ERROR encountered at " + v2.toString(), 0);
        frame.requestFocus();
    }
}
