package com.example.FoodPlanner.food;

import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Kleino
 * Date: 10/30/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordList {
    ArrayList<FoodItem> foodList;
    ArrayList<String> foodListStrings;
    ArrayList<String> timeOfDay;
    ArrayList<String> days;
    AssetManager assetManager;
    public WordList(AssetManager assetManager) {
        this.assetManager = assetManager;
        //foodList = new ArrayList<FoodItem>();
        //foodList.add(new FoodItem("Brot",10));
        //foodList.add(new FoodItem("Milch",20));
        //foodList.add(new FoodItem("Wiener Schnitzel",30));
        readFile();
        String[] test = {"morgens", "mittags", "abends", "vormittags","am vormittag", "Abend", "Vormittag", "Mittag"};

        timeOfDay = new ArrayList<String>();
        days = new ArrayList<String>();
        Collections.addAll(timeOfDay, test);
        String[] d = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
        Collections.addAll(days, d);
    }
    private String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
    }
    public void readFile() {
        String files[];
        try {
            files = assetManager.list("lebensmittel.csv");
        } catch (IOException e) {
            System.out.println("");
        }

        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("lebensmittel.csv");

        } catch (Exception e) {                 }

        String lebensmitteldaten = readTextFile(inputStream);
        storeDataToArray(lebensmitteldaten.split("\n"));

    }
    private void storeDataToArray(String[] record) {
        foodList = new ArrayList();
        for (int i = 0; i < record.length; i++) {
            String[] tempRecord = record[i].split(",");
            FoodItem temp = new FoodItem(tempRecord[0], Integer.parseInt(tempRecord[1]));
            foodList.add(temp);
        }
    }


    public ArrayList<FoodItem> getFoodList() {
        return foodList;
    }

    public ArrayList<String> getFoodListStrings() {
        return foodListStrings;
    }

    public ArrayList<String> getTimeOfDay() {
        return timeOfDay;
    }

    public ArrayList<String> getDays() {
        return days;
    }
}
