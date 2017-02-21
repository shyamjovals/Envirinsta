package bluepanther.jiljungjuk;

import java.io.Serializable;

/**
 * Created by SUBASH.M on 11/15/2016.
 */

public class Clickcheck implements Serializable
{
    Boolean igal,icam,vgal,vcam,arec,afile,ffile;

    public Boolean getAfile() {
        return afile;
    }

    public void setAfile(Boolean afile) {
        this.afile = afile;
    }

    public Boolean getArec() {
        return arec;
    }

    public void setArec(Boolean arec) {
        this.arec = arec;
    }

    public Boolean getFfile() {
        return ffile;
    }

    public void setFfile(Boolean ffile) {
        this.ffile = ffile;
    }

    public Boolean getIcam() {
        return icam;
    }

    public void setIcam(Boolean icam) {
        this.icam = icam;
    }

    public Boolean getIgal() {
        return igal;
    }

    public void setIgal(Boolean igal) {
        this.igal = igal;
    }

    public Boolean getVcam() {
        return vcam;
    }

    public void setVcam(Boolean vcam) {
        this.vcam = vcam;
    }

    public Boolean getVgal() {
        return vgal;
    }

    public void setVgal(Boolean vgal) {
        this.vgal = vgal;
    }
}
