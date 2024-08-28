package lk.ijse.possystemapi.utill;

import java.sql.ResultSet;

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
