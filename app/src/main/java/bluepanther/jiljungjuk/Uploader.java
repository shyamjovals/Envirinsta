package bluepanther.jiljungjuk;

import android.net.Uri;

/**
 * Created by SUBASH.M on 11/9/2016.
 */

public class Uploader
{
    static Uri iuri,vuri,auri,furi;
    static TextDesc textDesc;
    static VideoDesc videoDesc;
    static Audiodesc audiodesc;
    static FileDesc fileDesc;
    static ImageDesc imageDesc;
    static String ifname,afname,vfname,ffname;

    public static Boolean istate=false,astate=false,vstate=false,fstate=false;
    public static String getAfname() {
        return afname;
    }

    public static void setAfname(String afname) {
        Uploader.afname = afname;
        Uploader.astate=true;
    }

    public static Audiodesc getAudiodesc() {
        return audiodesc;
    }

    public static void setAudiodesc(Audiodesc audiodesc) {
        Uploader.audiodesc = audiodesc;
    }

    public static Uri getAuri() {
        return auri;
    }

    public static void setAuri(Uri auri) {
        Uploader.auri = auri;
    }

    public static String getFfname() {
        return ffname;
    }

    public static void setFfname(String ffname) {
        Uploader.ffname = ffname;
        Uploader.fstate=true;
    }

    public static FileDesc getFileDesc() {
        return fileDesc;
    }

    public static void setFileDesc(FileDesc fileDesc) {
        Uploader.fileDesc = fileDesc;
    }

    public static Uri getFuri() {
        return furi;
    }

    public static void setFuri(Uri furi) {
        Uploader.furi = furi;
    }

    public static String getIfname() {
        return ifname;
    }

    public static void setIfname(String ifname) {
        Uploader.ifname = ifname;
        Uploader.istate=true;
    }

    public static ImageDesc getImageDesc() {
        return imageDesc;
    }

    public static void setImageDesc(ImageDesc imageDesc) {
        Uploader.imageDesc = imageDesc;
    }

    public static Uri getIuri() {
        return iuri;
    }

    public static void setIuri(Uri iuri) {
        Uploader.iuri = iuri;
    }

    public static TextDesc getTextDesc() {
        return textDesc;
    }

    public static void setTextDesc(TextDesc textDesc) {
        Uploader.textDesc = textDesc;
    }

    public static String getVfname() {
        return vfname;
    }

    public static void setVfname(String vfname) {
        Uploader.vfname = vfname;
        Uploader.vstate=true;
    }

    public static VideoDesc getVideoDesc() {
        return videoDesc;
    }

    public static void setVideoDesc(VideoDesc videoDesc) {
        Uploader.videoDesc = videoDesc;
    }

    public static Uri getVuri() {
        return vuri;
    }

    public static void setVuri(Uri vuri) {
        Uploader.vuri = vuri;
    }
}
