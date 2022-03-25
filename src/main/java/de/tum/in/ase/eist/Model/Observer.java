package main.java.de.tum.in.ase.eist.Model;

public interface Observer<T> {
    void update(T newState);
}
