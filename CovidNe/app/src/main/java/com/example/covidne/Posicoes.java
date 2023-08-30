package com.example.covidne;


import android.content.res.Resources;

public class Posicoes {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    double width;
    double height;

    public static final double WIDTH_PADRAO = 720;
    public static final double HEIGHT_PADRAO = 1515;

    public Posicoes(int width, int height){
        this.width = width;
        this.height = height;
        this.LEFT = (int) ( 330 *  width/WIDTH_PADRAO);
        this.TOP = (int) (480 * height/HEIGHT_PADRAO);
        this.RIGHT = (int) (370 * width/WIDTH_PADRAO);
        this.LEFT_RIGHT = RIGHT-LEFT;
        this.BOTTOM = (int) (740 * height/HEIGHT_PADRAO);
        this.TOP_BOTTOM = (int) (BOTTOM-TOP * height/HEIGHT_PADRAO);
        this.TOPC = (int) (520 * height/HEIGHT_PADRAO);
        this.BOTTOMC = (int) (540 * height/HEIGHT_PADRAO);
        this.TOPC_BOTTOMC = BOTTOMC-TOPC;
        this.TOPT = (int) (580 * height/HEIGHT_PADRAO);
        this.BOTTOMT = (int) (600 * height/HEIGHT_PADRAO);
        this.TOPT_BOTTOMT = BOTTOMT-TOPT;
    }
    public int LEFT;
    public int TOP ;
    public  int RIGHT ;

    public int LEFT_RIGHT ;
    public int BOTTOM ;

    public int TOP_BOTTOM;

    public int TOPC ;
    public int BOTTOMC ;

    public int TOPC_BOTTOMC;

    public int TOPT ;

    public int BOTTOMT;

    public int TOPT_BOTTOMT;



}