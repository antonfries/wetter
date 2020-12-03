package wetter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final String DATEI = "wetterdaten_feldberg";
    private static final String CSV = ".csv";
    private static final String TXT = ".txt";
    // TODO: Dynamische Zeilen und Spaltenangabe
    private static final int SPALTEN = 8;
    private static final int ZEILEN = 31;
    private static final String DATUM_STRING = "Datum";

    public static void main(String[] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(DATEI + TXT));
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(DATEI + CSV));
        String line;
        String monat = inputReader.readLine();
        String header = inputReader.readLine();
        String meta = inputReader.readLine();
        String[] metaListe = meta.split(" +");
        String metaCsv = String.join(";", metaListe);
        outputWriter.write(metaCsv);
        outputWriter.newLine();
        Float[][] aussen = new Float[100][];
        int counter = 0;
        while ((line = inputReader.readLine()) != null) {
            ArrayList<String> zeile = new ArrayList<>();
            String[] ergebnis = line.split(" +");
            Float[] innen = new Float[100];
            for (int i = 0; i < ergebnis.length; i++) {
                String s = ergebnis[i];
                s = s.replaceAll(DATUM_STRING, monat);
                s = s.replaceAll(".10.2019", "");
                try {
                    innen[i] = Float.parseFloat(s);
                } catch (NumberFormatException ignored) {
                }
                s = s.replace('.', ',');
                zeile.add(s);
            }
            aussen[counter] = innen;
            counter++;
            String einzelneZeile = String.join(";", zeile);
            outputWriter.write(einzelneZeile);
            outputWriter.newLine();
        }
        inputReader.close();
        outputWriter.close();
        // Aufgabenteil b) Struktur bereits passend vorbereitet
        ArrayList<Float> minimumListe = new ArrayList<>();
        ArrayList<Float> mittelListe = new ArrayList<>();
        ArrayList<Float> maximumListe = new ArrayList<>();
        for (int i = 1; i <= SPALTEN; i++) {
            ArrayList<Float> wert = new ArrayList<>();
            double sum = 0.0;
            for (int j = 0; j < ZEILEN; j++) {
                sum += aussen[j][i];
                wert.add(aussen[j][i]);
            }
            minimumListe.add(Collections.min(wert));
            mittelListe.add((float) (sum / ZEILEN));
            maximumListe.add(Collections.max(wert));
        }
        System.out.println(monat);
        System.out.println(header);
        for (String s : metaListe) {
            if (s.equals("Datum")) {
                System.out.format("%10s", "");
            } else {
                System.out.format("%10s", s);
            }
        }
        System.out.println();
        System.out.format("%10s", "Minimum");
        for (Float f : minimumListe) {
            System.out.format("%10.1f", f);
        }
        System.out.println();
        System.out.format("%10s", "Mittel");
        for (Float f : mittelListe) {
            System.out.format("%10.1f", f);
        }
        System.out.println();
        System.out.format("%10s", "Maximum");
        for (Float f : maximumListe) {
            System.out.format("%10.1f", f);
        }
        System.out.println();
    }
}
