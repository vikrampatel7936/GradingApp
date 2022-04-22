package com.example.vikramassignment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ViewGrades extends Fragment {

    //variable declaration
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    List<Modal> list;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate this fragment
        view = inflater.inflate(R.layout.fragment_view_grades, container, false);

        list = new ArrayList<>();
        db = new DatabaseHelper(getActivity());
        list = db.getAllGrades();

        //recyclerview properties
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (list.size() > 0){
            recyclerView.setAdapter(new GradeAdapter(list, getActivity()));
        }else {
            Toast.makeText(getContext(), "Data does not Found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}