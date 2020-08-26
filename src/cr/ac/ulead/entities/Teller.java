package cr.ac.ulead.entities;

public class Teller {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isFree() {
        return this.person == null;
    }
}
