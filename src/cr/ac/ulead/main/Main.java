package cr.ac.ulead.main;

import cr.ac.ulead.logic.Logic;
import cr.ac.ulead.ui.Display;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Display display = new Display();
        Logic logic = new Logic();

        int menuSelection;
        do {
            display.mainMenu();
            menuSelection = display.readValue();
            logic.execute(menuSelection);
        } while (menuSelection != 3);
    }
}
