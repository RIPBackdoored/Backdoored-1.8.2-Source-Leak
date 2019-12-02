package a.a.g.b.g;

import com.backdoored.hacks.player.*;
import java.awt.event.*;

class r$a implements ActionListener {
    public int tl = 0;
    public final /* synthetic */ int tm;
    public final /* synthetic */ String[] tn;
    public final /* synthetic */ Spammer to;
    
    public r$a(final Spammer to, final int tm, final String[] tn) {
        this.to = to;
        this.tm = tm;
        this.tn = tn;
        super();
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        if (this.tl < this.tm) {
            Spammer.e().player.sendChatMessage(this.tn[this.tl]);
            ++this.tl;
            if (this.tl == this.tm) {
                this.tl = 0;
            }
        }
    }
}