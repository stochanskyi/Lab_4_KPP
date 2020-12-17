package data.monitor;

public class ThreadState {

    public final String name;
    public final String state;
    public final String priority;
    public final String isAlive;

    public ThreadState(String name, String state, String priority, String isAlive) {
        this.name = name;
        this.state = state;
        this.priority = priority;
        this.isAlive = isAlive;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "State: " + this.state + "\n" +
                "Priority: " + this.priority + "\n" +
                "Is alive: " + this.isAlive + "\n";
    }
}
