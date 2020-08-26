package cr.ac.ulead.logic;

import cr.ac.ulead.entities.Person;
import cr.ac.ulead.entities.PersonQueue;
import cr.ac.ulead.entities.Teller;
import cr.ac.ulead.ui.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Logic {

    Display display = new Display();
    ArrayList<Integer> normalQueueSimulationData = new ArrayList<>();
    ArrayList<Integer> priorityQueueSimulationData = new ArrayList<>();
    PersonQueue personQueue = new PersonQueue(2000);
    PersonQueue priorityPersonQueue = new PersonQueue(2000);
    int numberOfTellers = 8; // Sets the number of tellers.
    Teller[] servicePoint = new Teller[numberOfTellers];

    int currentTime = 0;
    int maxTime = 0;
    int totalWaitingTimePeopleServed = 0;
    int totalServiceTime = 0;
    int servedPersonCounter = 0;
    int totalWaitingTimePeopleUnServed = 0;
    int unServedPersonCounter = 0;

    public void execute(int menuSelection) throws FileNotFoundException {
        switch (menuSelection) {
            case 1:
                loadSimulationData();
                break;
            case 2:
                runSimulation();
                break;
            case 3:
                display.showGoodbyeMessage();
                break;
            default:
                display.showWrongSelection();
                break;
        }
    }

    private void loadSimulationData() throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File("C:\\dev\\estructuras\\proyecto\\simulacion.txt"));
        while (fileReader.hasNextLine()) {
            String currentLine = fileReader.nextLine();
            String[] data = currentLine.split(",");
            normalQueueSimulationData.add(Integer.parseInt(data[0]));
            priorityQueueSimulationData.add(Integer.parseInt(data[1]));
            maxTime++;
        }
        fileReader.close();
    }

    private void runSimulation() {
        addTellers();
        while (currentTime < maxTime) {
            getPeopleServed();
            matchPersonToTeller();
            loadQueues();
            matchPersonToTeller();
            this.currentTime++;
        }
        getStatistics();
    }

    private void addTellers() {
        for (int i = 0; i < servicePoint.length; i++) {
            servicePoint[i] = new Teller();
            servicePoint[i].setPerson(null);
        }
    }

    private void getPeopleServed() {
        for (int i = 0; i < servicePoint.length; i++) {
            if (servicePoint[i].getPerson() != null) {
                if (servicePoint[i].getPerson().getServiceTimeEndTime() == currentTime) {
                    savePersonData(servicePoint[i].getPerson());
                    servicePoint[i].setPerson(null);
                }
            }
        }
    }

    private void savePersonData(Person person) {
        this.totalWaitingTimePeopleServed = totalWaitingTimePeopleServed + (person.getWaitingLineEndTime() - person.getWaitingLineStartTime());
        this.totalServiceTime = totalServiceTime + person.getTotalServiceTime();
        this.servedPersonCounter++;
    }

    private void matchPersonToTeller() {
        for (int i = 0; i < servicePoint.length; i++) {
            if (servicePoint[i].isFree()) {
                if (!priorityPersonQueue.isEmpty()) {
                    Person person = priorityPersonQueue.deQueue();
                    person.setWaitingLineEndTime(currentTime);
                    person.setTotalServiceTime();
                    person.setServiceTimeEndTime(currentTime + person.getTotalServiceTime());
                    servicePoint[i].setPerson(person); // Set 1 priority person to a teller.
                }
                while (i < numberOfTellers-1) {
                    if (servicePoint[i].isFree()) {
                        if (!personQueue.isEmpty()) {
                            Person person = personQueue.deQueue();
                            person.setWaitingLineEndTime(currentTime);
                            person.setTotalServiceTime();
                            person.setServiceTimeEndTime(currentTime + person.getTotalServiceTime());
                            servicePoint[i].setPerson(person); // Set 1 person to a teller.
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void loadQueues() {
        int currentIndex = currentTime;
        if (currentTime < maxTime) {
            int normalCounter = 0;
            while (normalCounter < normalQueueSimulationData.get(currentIndex)) {
                Person person = new Person();
                person.setWaitingLineStartTime(currentTime);
                personQueue.enQueue(person);
                normalCounter++;
            }
            int priorityCounter = 0;
            while (priorityCounter < priorityQueueSimulationData.get(currentIndex)) {
                Person person = new Person();
                person.setWaitingLineStartTime(currentTime);
                priorityPersonQueue.enQueue(person);
                priorityCounter++;
            }
        }
    }

    private void getStatistics() {
        getDataPeopleInService();
        getDataPeopleServed();
        getDataPeopleUnServed();
        getTotalData();
    }

    private void getDataPeopleInService(){
        for (int i = 0; i < servicePoint.length; i++) {
            if(!servicePoint[i].isFree()) {
                this.totalWaitingTimePeopleServed = totalWaitingTimePeopleServed + (servicePoint[i].getPerson().getWaitingLineEndTime() - servicePoint[i].getPerson().getWaitingLineStartTime());
                this.totalServiceTime = totalServiceTime + (servicePoint[i].getPerson().getTotalServiceTime() - (servicePoint[i].getPerson().getServiceTimeEndTime()-currentTime));
            }
        }
    }

    private void getDataPeopleServed() {
        int averageWaitingTime = totalWaitingTimePeopleServed / servedPersonCounter;
        display.showAverageWaitingTime(averageWaitingTime);
        int averageServiceTime = totalServiceTime / servedPersonCounter;
        display.showAverageServiceTime(averageServiceTime);
        int averageTotalTime = (totalServiceTime + totalWaitingTimePeopleServed) / servedPersonCounter;
        display.showAverageTotalTime(averageTotalTime);
        System.out.println("La cantidad de personas atentidas fue de: " + servedPersonCounter);
    }

    private void getDataPeopleUnServed() {
        while (!personQueue.isEmpty()) {
            Person person = personQueue.deQueue();
            this.totalWaitingTimePeopleUnServed = totalWaitingTimePeopleUnServed + (currentTime - person.getWaitingLineStartTime());
            this.unServedPersonCounter++;
        }
        while (!priorityPersonQueue.isEmpty()) {
            Person person = priorityPersonQueue.deQueue();
            this.totalWaitingTimePeopleUnServed = totalWaitingTimePeopleUnServed + (currentTime - person.getWaitingLineStartTime());
            this.unServedPersonCounter++;
        }
        int averageWaitingTimeUnServedPeople = totalWaitingTimePeopleUnServed / unServedPersonCounter;
        display.showWaitingPeople(unServedPersonCounter);
        display.showAverageWaitingTimeUnServedPeople(averageWaitingTimeUnServedPeople);
    }

    private void getTotalData() {
        int allPeopleTotalWaitingTime = (this.totalWaitingTimePeopleServed + totalWaitingTimePeopleUnServed)/(servedPersonCounter+unServedPersonCounter);
        System.out.println("El tiempo promedio de espera de TODAS las personas (1638) fue de: " + allPeopleTotalWaitingTime + " min.");
        int allPeopleTotalTime = ((this.totalWaitingTimePeopleServed + totalWaitingTimePeopleUnServed)+this.totalServiceTime)/(servedPersonCounter+unServedPersonCounter);
        System.out.print("El tiempo promedio total (espera + servicio) de TODAS las personas (1638) fue de: " + allPeopleTotalTime + " min." );
    }

}
