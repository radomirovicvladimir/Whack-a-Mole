package igra;

import java.awt.*;

public class Krtica extends Zivotinja { 
	
	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void udarenaZivotinja() {
		rupa.prekini();
	}

	@Override
	public void pobeglaZivotinja() {
		rupa.dohvBastu().smanjiPovrce();
	}

	@Override
	public void crtaj(Rupa rupa) {
		Graphics g = rupa.getGraphics();
		g.setColor(Color.GRAY);
		g.fillOval((int) (rupa.getWidth() - w) / 2, (int) (rupa.getHeight() - h) / 2, (int) w, (int) h);
	}

	@Override
	public void azuriraj() {
		w = w - korakW;
		h = h - korakH;
	}

}
