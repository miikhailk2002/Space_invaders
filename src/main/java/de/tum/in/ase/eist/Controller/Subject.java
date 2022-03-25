package main.java.de.tum.in.ase.eist.Controller;

import main.java.de.tum.in.ase.eist.Model.Observer;

import java.util.ArrayList;

public abstract class Subject<T> {
    ArrayList<Observer<T>> observers;

    public Subject(){
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer<T> observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        boolean removedSuccessfully = this.observers.remove(observer);
        if(!removedSuccessfully) {
            throw new RuntimeException("tried to remove an observer that doesn't exist");
        }
    }

    public void notifyObservers(final T newState) {
        this.observers.forEach(o -> o.update(newState));
    }
}
