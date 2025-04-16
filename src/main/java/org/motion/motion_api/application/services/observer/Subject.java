package org.motion.motion_api.application.services.observer;

import jakarta.mail.MessagingException;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object data) throws MessagingException {
        for (Observer observer : observers) {
            observer.update(data);
        }
    }
}