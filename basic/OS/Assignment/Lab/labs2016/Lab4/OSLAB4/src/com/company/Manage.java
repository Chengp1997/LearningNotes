package com.company;

import java.util.*;

public class Manage {
    public String root= "root";
    private Files rootDirectory=new Files(root);
    private Files currentDirectory=rootDirectory;

    Manage(){
        rootDirectory.setParent(null);
    }
    public String pwd(){//pwd   print current working directory
        String pwd="/"+currentDirectory.getFileName();
        Files cursor=currentDirectory;
        while(cursor.getParent()!=null){
            pwd="/"+cursor.getParent().getFileName()+pwd;
            cursor=cursor.getParent();
        }
        return  pwd;
    }
    public void cd(String direc){//cd<...|sub_directory>   change directory
        if(direc.equals("..")){//the parent directory
            if (currentDirectory.equals(rootDirectory)){
                System.out.println("it's root right now, can not get back to the father directory");
                return;
            }
            currentDirectory=currentDirectory.getParent();
            System.out.println("change successfully");
        }else{//enter the sub directory
            String []a=direc.split("/");
            if(currentDirectory.subMap.containsKey(a[a.length-1])){
                if(!currentDirectory.subMap.get(a[a.length-1]).isFile()){
                    currentDirectory=currentDirectory.subMap.get(a[a.length-1]);
                    System.out.println("change successfully!");
                    return;
                }else{
                    System.out.println("no such folder in current directory");
                }
            }
        }
    }
    public void ls(){//ls   print the current directory content
        if(!currentDirectory.subMap.isEmpty()){
            for(Files value: currentDirectory.subMap.values()){
                System.out.println(value.getFileName());
            }
        }
    }
    public void ls_l(){//ls_l print the current directory contents in long format
        if(!currentDirectory.subMap.isEmpty()){
            for(Files value: currentDirectory.subMap.values()){
                System.out.print(value.getPermission() + "   " +
                        value.getUserName() + "   " + value.getGroup() +
                        "   " + value.getSize() + "   " + value.getDate()
                        + "   " + value.getDirectory() + "\n");
            }
        }
    }

    public void mkdir(String directoryName){//mkdir   make new directory

        Files exist=currentDirectory.subMap.get(directoryName);
        if(exist!=null){//no such file exits
            if(exist.isFile()){
                Files dir=new Files(directoryName);
                dir.setParent(currentDirectory);
                dir.setDirectory("/"+currentDirectory.getFileName());
                dir.setPermission(currentDirectory.getPermission());
                currentDirectory.subMap.put(directoryName,dir);
                System.out.println("create directory successfully");
            }else{
                System.out.println("make new directory failed. Same directory exists");
            }
        }else{
            Files dir=new Files(directoryName);
            dir.setParent(currentDirectory);
            dir.setDirectory("/"+currentDirectory.getFileName());
            dir.setPermission(currentDirectory.getPermission());
            currentDirectory.subMap.put(directoryName,dir);
            System.out.println("create directory successfully!");
        }
    }
    public void rmdir(String directoryName){//rmdir   remove the directory
        //判断是否存在此文件夹
        Files file = currentDirectory.subMap.get(directoryName);
        if(file.getPermission().indexOf("w")>=0){//if we have permission to write ,which means we can delete the directory
            if (file == null) {
                System.out.println("no such directory exist");
            }else {
                if(!file.isFile()){
                    currentDirectory.subMap.remove(directoryName);
                    System.out.println("delete successfully");
                }else{
                    System.out.println("no such directory in current directory");
                }
            }
        }else{
            System.out.println("you have no right to delete");
        }
    }
    public void touch(String fileName){//touch   touch to create a new file
        Files exist=currentDirectory.subMap.get(fileName);
        if(exist!=null){
            if(!exist.isFile()){
                Files dir=new Files(fileName,0);
                dir.setParent(currentDirectory);
                dir.setDirectory("program");
                dir.setPermission("-"+currentDirectory.getPermission().substring(1));//set the permission as the parent
                currentDirectory.subMap.put(fileName,dir);
                System.out.println("create file successfully");
            }else{
                System.out.println("make new directory failed. Same directory exists");
            }
        }else{
            Files dir=new Files(fileName,0);
            dir.setParent(currentDirectory);//set the parent
            dir.setDirectory("program");//set current directory
            dir.setPermission("-"+currentDirectory.getPermission().substring(1));
            currentDirectory.subMap.put(fileName,dir);
            System.out.println("create file successfully!");
        }
    }


    public void rm(String fileName) {//rm   remove a file
        //whether exist
        Files file = currentDirectory.subMap.get(fileName);
        if(file.getPermission().indexOf("w")>=0){
            if (file == null) {
                System.out.println("no such file exist");
            }else {
                if(file.isFile()){
                    currentDirectory.subMap.remove(fileName);
                    System.out.println("delete successfully");
                }else{
                    System.out.println("no such file in current directory.");
                }
            }
        }else{
            System.out.println("you have no right to delete the file.");
        }
    }
    public void grandPermission(String permission,String fileName){//chmod  grand permission <fileName/dir Name>
        char p[]= permission.toCharArray();
        String returnable=null;
        Files f=currentDirectory.subMap.get(fileName);
        if(f!=null){
            if(f.isFile()==true){
                returnable="-";
            }else{
                returnable="d";
            }
            for(int i=0;i<p.length;i++){
                if(p[i]=='7'){//111
                    permission="rwx";
                }else if (p[i]=='6'){//110
                    permission="rw-";
                }else if(p[i]=='5'){//101
                    permission="r-x";
                }else if(p[i]=='4'){//100
                    permission="r--";
                }else if(p[i]=='3'){//011
                    permission="-wx";
                }else if(p[i]=='2'){//010
                    permission="-w-";
                } else if (p[i]=='1'){//001
                    permission="--x";
                }else if (p[i]=='0'){//000
                    permission="---";
                }else{
                    System.out.println("error input! try again");
                    break;
                }
                returnable+=permission;
            }
        }else{
            System.out.println("no such file/directory");
            return;
        }
        //System.out.println(returnable);
        f.setPermission(returnable);
    }
    public void exit(){//exit
        System.out.println("exit successfully!");
        System.exit(0);
    }
}
