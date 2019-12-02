package a.a.g.b.i.c;

import com.backdoored.commands.*;
import com.backdoored.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import com.backdoored.hacks.ui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.b.d.a.*;
import a.a.g.b.*;
import com.backdoored.hacks.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class b
{
    private JFrame xq;
    private JTextArea xr;
    private JTextField xs;
    private JTextArea xt;
    private JPanel xu;
    private JSplitPane xv;
    private JPanel xw;
    private JPanel xx;
    private JScrollPane xy;
    
    public b() {
        super();
        this.c();
        this.xq = new JFrame("SwingImpl");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.xq.setSize(screenSize.width / 2, screenSize.height / 2);
        this.xq.setContentPane(this.xu);
        this.xq.pack();
        this.xq.setVisible(true);
        this.xs.addActionListener(p0 -> {
            if (this.xs.getText().startsWith(Command.x)) {
                Command.a(this.xs.getText());
            }
            else {
                Globals.mc.player.sendChatMessage(this.xs.getText());
            }
            this.xs.setText("");
            return;
        });
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void a(final ClientChatReceivedEvent clientChatReceivedEvent) {
        if (Swing.xn.getValInt()) {
            this.xr.append(clientChatReceivedEvent.getMessage().getUnformattedText() + "\n");
        }
        final JScrollBar verticalScrollBar = this.xy.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
    @SubscribeEvent
    public void a(final a a) {
        this.xr.append(a.iTextComponent.getUnformattedText() + "\n");
        final JScrollBar verticalScrollBar = this.xy.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
    public void a() {
        final StringBuilder sb = new StringBuilder();
        for (final BaseHack baseHack : c.hacks()) {
            if (baseHack.getEnabled()) {
                sb.append(baseHack.name).append("\n");
            }
        }
        this.xt.setText(sb.toString());
    }
    
    public void b() {
        this.xq.setVisible(false);
        this.xq.dispose();
    }
    
    private void c() {
        (this.xu = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.xu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        this.xv = new JSplitPane();
        this.xu.add(this.xv, "Center");
        (this.xw = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.xv.setLeftComponent(this.xw);
        this.xs = new JTextField();
        this.xw.add(this.xs, "South");
        (this.xy = new JScrollPane()).setHorizontalScrollBarPolicy(31);
        this.xy.setVerticalScrollBarPolicy(20);
        this.xw.add(this.xy, "Center");
        (this.xr = new JTextArea()).setLineWrap(true);
        this.xr.setText("");
        this.xy.setViewportView(this.xr);
        (this.xx = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.xv.setRightComponent(this.xx);
        this.xt = new JTextArea();
        this.xx.add(this.xt, "Center");
    }
    
    public JComponent d() {
        return this.xu;
    }
    
    private /* synthetic */ void a(final ActionEvent actionEvent) {
        if (this.xs.getText().startsWith(Command.x)) {
            Command.a(this.xs.getText());
        }
        else {
            Globals.mc.player.sendChatMessage(this.xs.getText());
        }
        this.xs.setText("");
    }
}
