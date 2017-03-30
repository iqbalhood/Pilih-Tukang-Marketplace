package com.zone.caritukang.setget;

/**
 * Created by IQBAL on 9/7/2016.
 */
public class Jasa {

    private String id;
    private String nama;
    private String foto;
    private String detail;

    public Jasa(){
        // TODO Auto-generated constructor stub
    }


    public Jasa(String id, String nama, String foto, String detail) {
        super();
        this.id = id;
        this.nama = nama;
        this.foto = foto;
        this.detail = detail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }





}
