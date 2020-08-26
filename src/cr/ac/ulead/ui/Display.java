package cr.ac.ulead.ui;

import java.io.PrintStream;
import java.util.Scanner;

public class Display {

    private Scanner input = new Scanner(System.in);
    private PrintStream output = new PrintStream(System.out);

    public void mainMenu() {
        output.println("\n");
        output.println("=======================================================================");
        output.println("========================   MENU PRINCIPAL   ===========================");
        output.println("=======================================================================");
        output.println(" - 1) Cargar datos de la simulacion.");
        output.println(" - 2) Correr simulacion.");
        output.println(" - 3) Salir.");
    }

    public int readValue() {
        output.println("Ingrese una opcion: ");
        int option = input.nextInt();
        this.input.skip("[\r\n]");
        return option;
    }

    public void showAverageWaitingTime(int averageWaitingTime) {
        output.println("El tiempo promedio de espera de las personas atendidas fue de: " + averageWaitingTime + " min.");
    }

    public void showAverageServiceTime(int averageServiceTime) {
        output.println("El tiempo promedio de servicio fue de: " + averageServiceTime + " min.");
    }

    public void showAverageTotalTime(int averageTotalTime) {
        output.println("El tiempo promedio total (espera + servicio) de las personas atendidas fue de: " + averageTotalTime + " min.");
    }

    public void showWaitingPeople(int waitingPeople) {
        output.println("La cantidad de personas que no fueron atentidas fue de: " + waitingPeople + " personas.");
    }

    public  void showAverageWaitingTimeUnServedPeople(int averageWaitingTimeUnServedPeople) {
        output.println("El tiempo promedio de espera de las personas que no fueron atentidas fue de: " + averageWaitingTimeUnServedPeople + " min.");
    }

    public void showGoodbyeMessage() {
        output.println("Gracias por utilizar el simulador. Nos vemos pronto!");
    }

    public void showWrongSelection() {
        output.println("Selecion invalida. Por favor, intente de nuevo.");
    }
}
