package igra;

import java.awt.*;

public class Rupa extends Canvas implements Runnable {
	private Basta basta;
	private Zivotinja zivotinja = null;
	private Color boja = new Color(160, 80, 0);
	private Thread nit;
	private boolean radi;
	private int brojKoraka, tajmer;

	public Rupa(Basta basta) {
		this.basta = basta;
	}

	public Basta dohvBastu() {
		return basta;
	}

	public void postZivotinju(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}

	public Zivotinja dohvZivotinju() {
		return zivotinja;
	}

	public void zgazi() {
		if (zivotinja != null)
			zivotinja.udarenaZivotinja();
	}

	private void azuriraj() {
		if (zivotinja != null) {
			zivotinja.azuriraj();
			tajmer--;
		}
	}

	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		g.setColor(boja);
		g.fillRect(0, 0, w, h);

		if (zivotinja != null)
			zivotinja.crtaj(this);
	}

	public void stvori() {
		nit = new Thread(this);
		nit.start();
	}

	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}

	public void prekini() {
		if (nit != null)
			nit.interrupt();
		zivotinja = null;
		tajmer = brojKoraka;
		repaint();
	}

	public boolean pokrenuta() {
		return radi;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					if (!radi)
						wait();
				}
				if (tajmer > 0) {
					repaint();
					azuriraj();
					Thread.sleep(100);
				} else {
//					repaint();
					zivotinja.pobeglaZivotinja();
					Thread.sleep(2000);
					prekini();
				}
			}
		} catch (InterruptedException ie) {
		}
	}

	public int dohvBrojKoraka() {
		return brojKoraka;
	}

	public void postBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		tajmer = brojKoraka;
	}

}
