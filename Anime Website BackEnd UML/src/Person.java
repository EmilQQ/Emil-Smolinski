public abstract class Person extends ObjectPlus{
    protected String name;
    protected String surname;

    public Person(String name, String surname){
        setName(name);
        setSurname(surname);
    }

    public abstract void introduce();


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
