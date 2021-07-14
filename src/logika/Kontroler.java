/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import forme.Decode;
import forme.Encode;
import forme.Menu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author DarkForce
 */
public class Kontroler {

    private static Kontroler instance;
    private final Operacije operacija;

    private Kontroler() {
        operacija = new Operacije();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public void otvoriSliku(Encode forma) {
        BufferedImage ulazna = forma.getUlaznaSlika();
        File fajl = operacija.prikaziFajlDijalog(true, forma);

        if (fajl == null) {
            return;
        }

        try {
            ulazna = ImageIO.read(fajl);
            forma.setUlaznaSlika(ulazna);
            forma.getLblUlaznaSlika().setIcon(new ImageIcon(ulazna));
            forma.validate();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(forma, "Doslo je dogreske prilikom ubacivanja slike");
        }
    }

    public void sakriPoruku(String poruka, Encode forma) {
        BufferedImage ulazna = forma.getUlaznaSlika();
        BufferedImage izlazna = ulazna.getSubimage(0, 0, ulazna.getWidth(), ulazna.getHeight());

        izlazna = operacija.ubaciPorukuUSliku(izlazna, poruka, forma);

        forma.setIzlaznaSlika(izlazna);
        forma.getLblIzlaznaSlika().setIcon(new ImageIcon(izlazna));
        forma.validate();
    }

    public File sacuvajSliku(Encode forma) {
        return operacija.prikaziFajlDijalog(false, forma);
    }

    
    public void otvoriSliku(Decode forma) {
        BufferedImage slika = forma.getSlika();
        File fajl = operacija.prikaziFajlDijalog(true, forma);

        if (fajl == null) {
            return;
        }

        try {
            slika = ImageIO.read(fajl);
            forma.setSlika(slika);
            forma.getLblSlika().setIcon(new ImageIcon(slika));
            forma.validate();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(forma, "Doslo je dogreske prilikom ubacivanja slike");
        }
    }

    public String dekodirajSliku(Decode forma) {
        BufferedImage slika = forma.getSlika();
        int duzina = operacija.extractujInteger(slika, 0, 0);
        byte b[] = new byte[duzina];
        for (int i = 0; i < duzina; i++) {
            b[i] = operacija.extractujByte(slika,i * 8 + 32, 0);
        }
        return new String(b);
    }

}
