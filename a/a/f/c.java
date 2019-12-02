package a.a.f;

import java.util.function.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class c extends JFrame implements ActionListener
{
    private JTextField gq;
    private JFrame gr;
    private JButton gs;
    private JLabel gt;
    private Consumer<String> gu;
    private String gv;
    
    public c(final String s, final String s2, final String gv, final Consumer<String> gu) {
        super();
        this.gu = gu;
        this.gv = gv;
        this.gr = new JFrame(s);
        this.gt = new JLabel(s2);
        (this.gs = new JButton(gv)).addActionListener(this);
        this.gq = new JTextField(16);
        final JPanel panel = new JPanel();
        panel.add(this.gq);
        panel.add(this.gs);
        panel.add(this.gt);
        this.gr.add(panel);
        this.gr.setSize(300, 300);
        this.gr.setVisible(true);
        this.gr.setAlwaysOnTop(true);
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(this.gv)) {
            this.gu.accept(this.gq.getText());
            this.gr.dispatchEvent(new WindowEvent(this.gr, 201));
        }
    }
}
