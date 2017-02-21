package bluepanther.jiljungjuk.Notifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bluepanther.jiljungjuk.RowItem;

/**
 * Created by SUBASH.M on 11/5/2016.
 */

public class Textnoti implements Serializable {
    public static List<RowItem> row;
    public static String members[];
    public static ArrayList<String>contentt;
    public static boolean state=false;
    public Textnoti(List<RowItem> row, String[]members, ArrayList<String>contentt)
    {
        this.row=row;
        this.members= Arrays.copyOf(members,members.length);
        this.contentt=new ArrayList<>(contentt);
        this.state=true;
    }
}
