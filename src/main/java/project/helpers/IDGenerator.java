package project.helpers;

/**
 *
 * @author Richard Šanda
 */
public class IDGenerator {
    private static int idCounter = 0;

    public static int getNewId(){
        return idCounter++;
    }
}
