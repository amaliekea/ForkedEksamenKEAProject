package org.example.eksamenkea;

public class Errorhandling extends Exception { //udvider javas standard exception klasse
    public Errorhandling(String message) {
        super(message);//beskeden bliver sendt videre tilsuperklassen, der gør det muligt at gemme beskeden
    }
}
