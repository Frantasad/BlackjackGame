package project.helpers;

/**
 *
 * @author František Holubec
 */
public class IDGenerator {
    private static int idCounter = 0;

    public static int getNewId(){
        return idCounter++;
    }
}
