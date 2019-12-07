package finalProject;

abstract class Employee {
    public static int PART_COLLECTOR = 4;
    public static int ASSEMBLER = 3;
    public static int WELDER = 2;
    public static int PAINTER = 1;

    protected int authorityLevel;
    protected Employee nextEmployee;

    public void setNextEmployee(Employee employee) {
        this.nextEmployee = employee;
    }

    public void doWork(int authorityLevel, String message) {
        if (this.authorityLevel <= authorityLevel) {
            write(message);
        }
        if (nextEmployee != null) {
            nextEmployee.doWork(authorityLevel, message);
        }
    }
    abstract protected void write(String message);
}

class PartCollector extends Employee {
    
    PartCollector() {
        this.authorityLevel = PART_COLLECTOR;
    }

    protected void write(String message) {
        System.out.println(message + " all parts have been gathered");
    }
}

class Assembler extends Employee {

    Assembler() {
        this.authorityLevel = ASSEMBLER;
    }

    protected void write(String message) {
        System.out.println(message + " all parts have been put together");
    }
}

class Welder extends Employee {

    Welder() {
        this.authorityLevel = WELDER;
    }

    protected void write(String message) {
        System.out.println(message + " all parts have been welded together");
    }
}

class Painter extends Employee {
    Painter() {
        this.authorityLevel = PAINTER;
    }

    protected void write(String message) {
        System.out.println(message + " car complete");
    }
}

public class ChainOfResponsibilityDesignPattern {

    public static void main(String[] args) {
        System.out.println("Chain of Responsibility Design Pattern");

        Employee partCollector = new PartCollector();
        Employee assembler = new Assembler();
        Employee welder = new Welder();
        Employee painter = new Painter();

        partCollector.setNextEmployee(assembler);
        assembler.setNextEmployee(welder);
        welder.setNextEmployee(painter);

        partCollector.doWork(4, "Task Complete: ");
    }
}