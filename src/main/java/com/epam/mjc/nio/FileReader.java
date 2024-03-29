package com.epam.mjc.nio;
import java.io.File;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileReader {
    interface Operationable {
        String calculate(String x, String y);
    }

    public static void main(String[] args) {
        String separator = File.separator;
        String path = separator + "Users" + separator + "Admin" + separator + "IdeaProjects" + separator
                + "stage1-module6-io-task01" + separator + "src" + separator + "main" + separator + "resources" + separator +
                "Profile.txt";
        File file = new File(path);
        FileReader fileReader = new FileReader();
        Profile profile = fileReader.getDataFromFile(file);
        Operationable operation;
        operation = (x, y) -> (x + y);

        String name = operation.calculate("Name:", profile.getName());
        String age = operation.calculate("Age:", String.valueOf(profile.getAge()));
        String email = operation.calculate("Email:", profile.getEmail());
        String phone = operation.calculate("Phone:", String.valueOf(profile.getPhone()));
        Logger logger
                = Logger.getLogger(
                FileReader.class.getName());
        logger.log(Level.INFO, name);
        logger.log(Level.INFO, age);
        logger.log(Level.INFO, email);
        logger.log(Level.INFO, phone);
    }

    private  String readFileToString(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private  Profile parseProfileData(String data) {
        String[] lines = data.split("\n");
        String name = null;
        int age = 0;
        String email = null;
        long phone = 0;

        for (String line :
                lines) {
            if (line.startsWith("Name:")) {
                name = line.substring(6).trim();

            } else if (line.startsWith("Age:")) {
                age = Integer.parseInt(line.substring(5).trim());

            } else if (line.startsWith("Email:")) {
                email = line.substring(7).trim();


            } else if (line.startsWith("Phone:")) {
                phone = Long.parseLong(line.substring(7).trim());

            }
        }
        return new Profile(name, age, email, phone);
    }

    public  Profile getDataFromFile(File file) {

        String fileContent = readFileToString(file);
        Profile profile = parseProfileData(fileContent);
        return profile;
    }
}

