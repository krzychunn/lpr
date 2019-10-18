package lpr;

import java.io.IOException;

public class Main {

    public static void main(String args[]){
        System.out.println("");
        System.out.println("------------------------------------------License Plate Recognition Race------------------------------------------");
        System.out.println("Ten program rozpoznaje numery rejestracyjne na nagraniach wideo umieszczonych w katalogu //video//.");
        System.out.println("");
        System.out.println("Do analiz jest wykorzystywana metoda,");
        System.out.println("której nazwa została przypisana do parametru 'detector' (42 linia pliku openalpr.conf).");
        System.out.println("Ścisłość wykrywania jest określona wartością parametru 'detection_strictness' (29 linia pliku openalpr.conf).");
        System.out.println("Liczba skanowań obrazu jest określona wartością parametru 'analysis_count' (58 linia pliku openalpr.conf).");
        System.out.println("");

        LicensePlateRecognition licensePlateRecognition = null;
        try {
            licensePlateRecognition = new LicensePlateRecognition();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        System.out.println("Trwa analizowanie nagrań...");
        System.out.println("...");
        licensePlateRecognition.run();
        long endTime = System.currentTimeMillis();
        long millisecondsInSecond = 1000l;
        long durationTime = (endTime - startTime)/millisecondsInSecond;
        System.out.println("Analizowanie nagrań zakończone.");
        System.out.println("");
        System.out.println("Czas działania programu: " + durationTime + " [s].");
        System.out.println("");
        System.out.println("Raporty z analiz zostały zapisane w katalogu //frames//.");
        System.out.println("Statystyki z analiz zostały zapisane w katalogu //statistics//.");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
}
