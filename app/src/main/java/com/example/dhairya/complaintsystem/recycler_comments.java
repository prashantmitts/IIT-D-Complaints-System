package com.example.dhairya.complaintsystem;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Dhairya on 26-02-2016.
 */
public class recycler_comments {
    public String name,comment,time;

    public recycler_comments(String str1, String str2, String str3) {
        name = str1;
        comment = str2;
        time = str3;

    }

    public static ArrayList<recycler_comments> createComments(Vector<String> names, Vector<String> comments, Vector<String> times){
        ArrayList<recycler_comments> commentArray = new ArrayList<>();
        recycler_comments temp;
        for(int i=0;i<names.size();i++) {
            temp = new recycler_comments(names.get(i),comments.get(i),times.get(i));
            commentArray.add(temp);
        }
        return commentArray;
    }
}
