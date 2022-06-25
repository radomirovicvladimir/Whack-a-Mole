package igra;

import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame implements ItemListener {
	private static Igra igra = null;
	private Basta basta = new Basta(N, N);
	private static int N = 4;
	private int interval = 1000;
	private int brojKoraka = 10;

	private Label labelaTezina = new Label("Tezina:");
	private Label Skor = new Label("Povrce: 100");

	private Checkbox poljezapotvrdu[] = new Checkbox[3];
	private Button dugmeStani = new Button("Kreni");

	private Igra() {
		super("Igra");
		this.setSize(750, 600);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.dodajKomponente();
		this.setResizable(false);
	}

	public static Igra dohvIgru() {
		if (igra == null)
			igra = new Igra();
		return igra;
	}

	public void dodajKomponente() {
		dodajIstok();

	}

	public void dodajIstok() {
		Font font = new Font("Arial", Font.BOLD, 18);
		basta.postaviLabelu(Skor,dugmeStani,poljezapotvrdu);

		labelaTezina.setAlignment(Label.CENTER);
		Skor.setAlignment(Label.CENTER);

		labelaTezina.setFont(font);
		Skor.setFont(font);

		dugmeStani.setPreferredSize(new Dimension(150, 30));
//		dugmeStani.getPreferredSize();
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(2, 1));


		Panel panelTezina = new Panel();
		panelTezina.setLayout(new GridLayout(3, 1));
		CheckboxGroup grupa = new CheckboxGroup();
		poljezapotvrdu[0] = new Checkbox("Lako", grupa, true);
		poljezapotvrdu[1] = new Checkbox("Srednje", grupa, false);
		poljezapotvrdu[2] = new Checkbox("Tesko", grupa, false);
		for (int i = 0; i < 3; i++)
			panelTezina.add(poljezapotvrdu[i]);

		Panel panelMenu = new Panel();
		panelMenu.setLayout(new GridLayout(3, 1));
		panelMenu.add(labelaTezina);
		panelMenu.add(panelTezina);
		panelMenu.add(dugmeStani);

		panel.add(panelMenu);
		panel.add(Skor);

		add(basta, BorderLayout.CENTER);
		add(panel, BorderLayout.EAST);

//		Osluskivaci
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				basta.prekini();
				dispose();
			}
		});

		dugmeStani.addActionListener(e -> {
			if (dugmeStani.getLabel()=="Stani") {
				dugmeStani.setLabel("Kreni");
				for (int i = 0; i < 3; i++)
					poljezapotvrdu[i].setEnabled(true);
				basta.prekini();
			} else {
				dugmeStani.setLabel("Stani");
				for (int i = 0; i < 3; i++)
					poljezapotvrdu[i].setEnabled(false);
				basta.postBrojKoraka(brojKoraka);
				basta.postInterval(interval);
				basta.stvori();
				basta.kreni();
			}
		});

		for (int i = 0; i < 3; i++)
			poljezapotvrdu[i].addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < 3; i++) {
			if (poljezapotvrdu[i].getState())
				switch (poljezapotvrdu[i].getLabel()) {
				case "Lako":
					System.out.println("Lako");
					interval = 1000;
					brojKoraka = 10;
					break;
				case "Srednje":
					System.out.println("Srednje");
					interval = 750;
					brojKoraka = 8;
					break;
				case "Tesko":
					System.out.println("Tesko");
					interval = 500;
					brojKoraka = 6;
					break;
				}
		}
	}

	public static void main(String[] args) {
		Igra.dohvIgru();
	}
}
