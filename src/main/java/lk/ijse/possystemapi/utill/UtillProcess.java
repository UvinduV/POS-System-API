package lk.ijse.possystemapi.utill;

public class UtillProcess {
    private static int counter = 0;
    public static String generateCustomerId(){
        counter++;
        return String.format("C%03d", counter);
    }

    public static String generateItemId(){
        counter++;
        return String.format("I%03d",counter);
    }
}
