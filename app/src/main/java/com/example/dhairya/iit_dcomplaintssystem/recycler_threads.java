package com.example.dhairya.complaintsystem;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Dhairya on 24-02-2016.
 */
public class recycler_threads {
    public int id;
    public String description,time;

    public recycler_threads(String str2, String str3, int str4) {
        description = str2;
        time = str3;
        id = str4;

    }

    public static ArrayList<recycler_threads> createThreads(Vector<String> descriptions, Vector<String> times,Vector<Integer> ids) {
        ArrayList<recycler_threads> threads = new ArrayList<>();
        recycler_threads temp;
        for(int i=0;i<descriptions.size();i++) {
            temp = new recycler_threads(descriptions.get(i),times.get(i),ids.get(i));
            threads.add(temp);
        }
        return threads;
    }
}
