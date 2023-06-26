package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Manage manager = new Manage();// new a system
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Command Line: ");
            /* choose to execute  */
            String command = input.nextLine();
            if (command.equals("pwd")) {
                String pwd=manager.pwd();
                System.out.println(pwd);
            } else if (command.startsWith("cd")) {
                if(command.substring(2).equals("")){
                    System.out.println("error input");
                }else{
                    String direc = command.substring(3);
                    manager.cd(direc);
                }
            } else if (command.equals("ls-l")) {
                System.out.println(command.substring(0,4));
                manager.ls_l();
            } else if (command.equals("ls")) {
                manager.ls();
            } else if (command.startsWith("mkdir")) {
                if(command.substring(5).equals("")){
                    System.out.println("error input");
                }else{
                    String directoryName=command.substring(6);
                    manager.mkdir(directoryName);
                }
            } else if (command.startsWith("rmdir")) {
                if(command.substring(5).equals("")){
                    System.out.println("error input");
                }else{
                    String directoryName=command.substring(6);
                    manager.rmdir(directoryName);
                }
            } else if (command.startsWith("touch")) {
                if(command.substring(5).equals("")){
                    System.out.println("error input");
                }else{
                    String fileName=command.substring(6);
                    manager.touch(fileName);
                }
            } else if (command.startsWith("rm")) {
                if(command.substring(2).equals("")){
                    System.out.println("error input");
                }else{
                    String fileName=command.substring(3);
                    manager.rm(fileName);
                }
            } else if (command.startsWith("chmod")) {
                String a[]=command.split(" ");
                if(a.length!=3){
                    System.out.println("error input!");
                }else{
                    String permission=a[1];
                    String fileName=a[2];
                    manager.grandPermission(permission,fileName);
                }
            } else if (command.startsWith("exit")) {
                manager.exit();
            }
        }
    }
}
