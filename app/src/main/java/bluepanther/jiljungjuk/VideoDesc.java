package bluepanther.jiljungjuk;

import java.util.ArrayList;

/**
 * Created by SUBASH.M on 11/4/2016.
 */

public class VideoDesc
{
    public String user;
    public String date;
    public String desc;
    public String maincat;


    public String subcat;
    public String target;
    public ArrayList<String> targetmems;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ArrayList<String> getTargetmems() {
        return targetmems;
    }

    public void setTargetmems(ArrayList<String> targetmems) {
        this.targetmems = targetmems;
    }

    public VideoDesc()
    {

    }
    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
