package igra;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Basta extends Panel implements Runnable {

	private Rupa[][] matricaRupa;
	private int povrce = 100;
	private int interval;
	private int brojKoraka;

	private int N;
	private int M;

	private boolean radi = false;
	private Thread nit;
	private ArrayList<Rupa> slobodneRupe;
	private Label Skor;
	private Checkbox grupa[];
	private Button dugme;

	public Basta(int n, int m) {
		N = n;
		M = m;
		matricaRupa = new Rupa[N][M];
		slobodneRupe = new ArrayList<Rupa>();
		setLayout(new GridLayout(n, m, 20, 20));
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				add(matricaRupa[i][j] = new Rupa(this));
				slobodneRupe.add(matricaRupa[i][j]);
				matricaRupa[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Object source = e.getSource();
						if (source instanceof Rupa) {
							Rupa rupica = (Rupa) source;
							rupica.zgazi();
						}
						if (source instanceof Zivotinja) {
							Zivotinja zivotinjica = (Zivotinja) source;
							zivotinjica.udarenaZivotinja();
						}
					}
				});
			}
		this.setBackground(Color.GREEN);
	}

	public int dohvBrojKoraka() {
		return brojKoraka;
	}

	public void postBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				matricaRupa[i][j].postBrojKoraka(brojKoraka);
	}
	
	public void postInterval(int interval) {
		this.interval = interval;
	}

	private synchronized void azuriraj() {
		Random r = new Random();
		int i = r.nextInt(N);
		int j = r.nextInt(M);
		while (matricaRupa[i][j].dohvZivotinju() != null) {
			i = r.nextInt(N);
			j = r.nextInt(M);
		}
		matricaRupa[i][j].postZivotinju(new Krtica(matricaRupa[i][j]));
		matricaRupa[i][j].stvori();
		matricaRupa[i][j].kreni();
		interval--;
	}

	public void smanjiPovrce() {
		povrce--;
	}

	public void postaviLabelu(Label labela, Button dugme, Checkbox[] grupa) {
		this.Skor = labela;
		this.dugme = dugme;
		this.grupa = grupa;
	}

	private void azurirajLabele() {
		if (Skor == null)
			return;
		Skor.setText("Povrce: " + povrce);
		if (povrce == 0) {
			prekini();
			dugme.setLabel("Kreni");
			for (int i = 0; i < 3; i++)
				grupa[i].setEnabled(true);
		}
	}


	public void stvori() {
		nit = new Thread(this);
		povrce = 100;
		nit.start();
	}
	
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}

	public void prekini() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				if (matricaRupa[i][j] != null)
					matricaRupa[i][j].prekini();
		if (nit != null)
			nit.interrupt();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!radi)
						wait();
				}
				azuriraj();
				azurirajLabele();
				repaint();
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
		}

	}
}
