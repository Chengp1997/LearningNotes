package com.company;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Files {
    public Map<String,Files> subMap=new HashMap<String,Files>();/*use map to store the directory or files */
    private String fileName;//fileName/directoryName
    private String permission;//permisiion
    private String userName="public";
    private String group;
    private int size;//size of the file
    private String date;
    private String directory;//current position
    private boolean isFile;//it's a directory or file
    private Files parent=null;//the file/directory's parent

    public Files(String fileName){
        this.fileName=fileName;
        this.permission="drwxrwxrwx";
        this.group="owner";
        this.date=getFormatDate();
        isFile=false;
        this.size=1;
    }//创建文件夹
    public Files(String fileName,int size){
        this.fileName=fileName;
        this.size=size;
        isFile=true;
        this.permission="-rwxrwxrwx";
        this.group="owner";
        this.date=getFormatDate();
    }
    //format date output
    public String getFormatDate(){
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cc=sdf.format(d);
        return cc;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setPermission(String permission){
        this.permission=permission;
    }
    public String getPermission(){
        return permission;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setGroup(String group){
        this.group=group;
    }
    public String getGroup(){
        return group;
    }
    public void setSize(int size){
        this.size=size;
    }

    public double getSize() {
        return size;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getDirectory(){
        return  directory;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setParent(Files parent) {
        this.parent = parent;
    }

    public Files getParent() {
        return parent;
    }
}
