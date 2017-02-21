package bluepanther.jiljungjuk.Notifications;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import bluepanther.jiljungjuk.RowItem;

/**
 * Created by SUBASH.M on 11/5/2016.
 */

public class Filenoti2 implements Serializable {
    public static List<RowItem> row;
    public static String members[];
    public static Boolean state=false;

    public Filenoti2(List<RowItem> row, String[]members)
    {
        this.row=row;
        this.members= Arrays.copyOf(members,members.length);
        this.state = true;
    }
}
