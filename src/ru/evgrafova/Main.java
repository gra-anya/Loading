package ru.evgrafova;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void openZip(String filePath, String dirPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(dirPath + "\\" + entry.getName());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        String path = "C:\\Java\\Games\\savegames\\";
        openZip(path + "zip.zip", path);
        for (String file : new File(path).list()) {
            if (file.contains("zip")) continue;
            GameProgress gameProgress = openProgress(path  + file);
            System.out.println(gameProgress);
        }
    }
}
