package igra;

public abstract class Zivotinja {
	protected boolean udarena;
	protected double w, h;
	protected double korakW,korakH;
	protected Rupa rupa;

	public Zivotinja(Rupa rupa) {
		this.rupa = rupa;
		w = rupa.getWidth();
		h = rupa.getHeight();
		korakW=w/(double)rupa.dohvBrojKoraka();
		korakH=h/(double)rupa.dohvBrojKoraka();
	}

	public abstract void azuriraj();

	public abstract void udarenaZivotinja();

	public abstract void pobeglaZivotinja();

	public abstract void crtaj(Rupa rupa);
}
