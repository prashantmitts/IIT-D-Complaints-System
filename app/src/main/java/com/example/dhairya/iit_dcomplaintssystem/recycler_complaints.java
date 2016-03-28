package com.example.dhairya.complaintsystem;

import java.util.ArrayList;

/**
 * Created by Dhairya on 29-03-2016.
 */
public class recycler_complaints {

    public String title,type,createdAt,createdBy;
    public int id;


    public recycler_complaints(String str_title,String str_type,String str_createdAt,String str_createdBy,int int_id ) {
        title = str_title;
        type=str_type;
        createdAt = str_createdAt;
        createdBy = str_createdBy;
        id = int_id;
    }

    public static ArrayList<recycler_complaints> createComplaints(ArrayList<String> Titles, ArrayList<String> Types,ArrayList<String> CreatedAts,ArrayList<String> CreatedBys,ArrayList<Integer> ids) {
        ArrayList<recycler_complaints> complaints = new ArrayList<>();
        recycler_complaints temp;
        for(int i=0;i<Titles.size();i++) {
            temp = new recycler_complaints(Titles.get(i),Types.get(i),CreatedAts.get(i),CreatedBys.get(i),ids.get(i));
            complaints.add(temp);
        }
        return complaints;
    }
}
