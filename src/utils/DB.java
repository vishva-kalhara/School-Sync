/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import config.Env;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vishv
 */
public class DB {

    public boolean backup(String dumpPath, String backupPath) throws Exception {

        Process process;
        String BackUpName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        System.out.println(BackUpName);
        String toSavePath = backupPath + "/backup_" + BackUpName + ".sql";

        Runtime runtime = Runtime.getRuntime();

        String exec = dumpPath + " -u" + Env.MYSQL_USERNAME + " -p" + Env.MYSQL_PASSWORD + " --add-drop-database -B " + Env.MYSQL_DB + " --routines --triggers --events -r \"" + toSavePath;
        process = runtime.exec(exec);
        int exitCode = process.waitFor();

        return exitCode == 0;
    }

    private boolean drop(String mySqlPath) throws Exception {

        Process process;
        Runtime runtime = Runtime.getRuntime();

        String exec = mySqlPath + " -u" + Env.MYSQL_USERNAME + " -p" + Env.MYSQL_PASSWORD + " -e \"DROP DATABASE " + Env.MYSQL_DB + "\"";
        process = runtime.exec(exec);
        int exitCode = process.waitFor();

        return exitCode == 0;
    }

    private boolean create(String mySqlPath) throws Exception {

        Process process;
        Runtime runtime = Runtime.getRuntime();

        String exec = mySqlPath + " -u" + Env.MYSQL_USERNAME + " -p" + Env.MYSQL_PASSWORD + " -e \"CREATE DATABASE " + Env.MYSQL_DB + "\"";
        process = runtime.exec(exec);
        int exitCode = process.waitFor();

        return exitCode == 0;
    }

    private boolean restore(String mySqlPath, String backupFilePath) throws Exception {

        String newMySqlPath = "\"" + mySqlPath + "\"";

        Process process;
        Runtime runtime = Runtime.getRuntime();

        String exec = "cmd.exe /c \"" + newMySqlPath + " -u" + Env.MYSQL_USERNAME + " -p" + Env.MYSQL_PASSWORD + " " + Env.MYSQL_DB + " < \"" + backupFilePath + "\"\"";
        process = runtime.exec(exec);
        int exitCode = process.waitFor();

        return exitCode == 0;
    }

    public boolean overrideData(String mySqlPath, String backupFilePath) throws Exception {
        
        boolean isDropped = this.drop(mySqlPath);
        if(!isDropped) return false;
        
        boolean isCreated = this.create(mySqlPath);
        if(!isCreated) return false;
        
        boolean isRestored = this.restore(mySqlPath, backupFilePath);
        return isRestored;
    }
}
