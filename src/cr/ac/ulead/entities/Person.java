package cr.ac.ulead.entities;

public class Person {

    private int waitingLineStartTime = 0;
    private int waitingLineEndTime = 0;
    private int totalServiceTime = 0;
    private int serviceTimeEndTime = 0;

    public int getWaitingLineStartTime() {
        return waitingLineStartTime;
    }

    public void setWaitingLineStartTime(int waitingLineStartTime) {
        this.waitingLineStartTime = waitingLineStartTime;
    }

    public int getWaitingLineEndTime() {
        return waitingLineEndTime;
    }

    public void setWaitingLineEndTime(int waitingLineEndTime) {
        this.waitingLineEndTime = waitingLineEndTime;
    }

    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    public void setTotalServiceTime() {
        double random = (Math.random() * ((1 - 0) + 1)) + 0;
        if (random <= 0.2) {
            this.totalServiceTime = 1;
        }
        if (random > 0.2 && random <= 0.4) {
            this.totalServiceTime = 2;
        }
        if (random > 0.4 && random <= 0.6) {
            this.totalServiceTime = 3;
        }
        if (random > 0.6 && random <= 0.8) {
            this.totalServiceTime = 5;
        }
        if (random > 0.8 && random <= 0.9) {
            this.totalServiceTime = 8;
        }
        if (random > 0.9 && random <= 0.95) {
            this.totalServiceTime = 13;
        }
        if (random > 0.95) {
            this.totalServiceTime = 13 + (int) (13 * Math.random());
        }
    }

    public int getServiceTimeEndTime() {
        return serviceTimeEndTime;
    }

    public void setServiceTimeEndTime(int serviceTimeEndTime) {
        this.serviceTimeEndTime = serviceTimeEndTime;
    }
}
