package bluepanther.jiljungjuk;

import java.io.Serializable;

/**
 * Created by SUBASH.M on 11/3/2016.
 */

public class CurrentUser2 implements Serializable
{
    public static String user,pass,sclass,ssec,sidate,sadate,svdate,sfdate,stdate;
    public CurrentUser2(String user, String pass, String sclass, String ssec, String sidate, String sadate, String svdate, String sfdate, String stdate)
    {
        this.user = user;
        this.sclass = sclass;
        this.ssec = ssec;
        this.sidate=sidate;
        this.sadate=sadate;
        this.svdate=svdate;
        this.sfdate=sfdate;
        this.stdate=stdate;
        this.pass=pass;
    }
}
//CurrentUser.user;