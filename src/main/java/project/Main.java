package project;

import project.Impl.Session;

/**
 * Run and play Game
 * @author František Holubec
 */
public class Main {
    public static void main(String[] args){
        ISession session = new Session();
        session.start();
    }
}
