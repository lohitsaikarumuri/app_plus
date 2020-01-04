package com.example.deliveryboy;

import  android.graphics.Bitmap;

public class Model {
   // private String Image;

    private String pid,cid,id,did;

//    public String getFid() {
//        return fid;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {

        return pid;
    }

    public String getCid() {
        return cid;
    }


    public Model() {
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Model(String pid, String cid, String id,String did) {
        this.pid=pid;
        this.cid=cid;
        this.id=id;
        this.did=did;
    }


}



