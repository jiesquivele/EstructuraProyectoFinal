package cr.ac.ulead.entities;

import com.sun.deploy.perf.PerfRollup;

public class PersonQueue {

    private Person[] queue;
    private int size = 0;
    private int totalSize;
    private int head;
    private int tail;


    public PersonQueue(int size) {
        this.totalSize = size;
        this.queue = new Person[totalSize];
    }

    public void enQueue(Person person) {
        queue[tail] = person;
        this.tail = (tail+1) % totalSize;
        this.size++;
    }

    public Person deQueue() {
        Person person = queue[head];
        this.head = (head+1) % totalSize;
        this.size--;
        return person;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }
}
