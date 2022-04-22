package com.example.vikramassignment2;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

    //variable declaration
    private RadioGroup radioGroup;
    private RadioButton radioID, radioCode;
    String[] users = {"PROG 8450", "PROG 8460", "PROG 8470", "PROG 8480"};
    String Course = "";
    String selection = "";
    private Button btnSubmit;
    LinearLayout layoutID, layoutCode;
    GradeAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    List<Modal> list;
    EditText etIds;
    Modal modal;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        //setting view to variables
        radioGroup = (RadioGroup) view.findViewById(R.id.rbGroup);
        btnSubmit = (Button) view.findViewById(R.id.search);
        layoutID = (LinearLayout) view.findViewById(R.id.idsLayout);
        layoutCode = (LinearLayout) view.findViewById(R.id.programCodeLayout);
        etIds = (EditText) view.findViewById(R.id.etIds);

        modal = new Modal();

        //spinner to select course
        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        selection = "id";


        //creating list
        list = new ArrayList<>();
        db = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        this.adapter = new GradeAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(this.adapter);

        if (list.size() > 0) {
            this.adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "No Data found.", Toast.LENGTH_SHORT).show();
        }


        //visibility changing as per the selection
        radioID = (RadioButton) view.findViewById(R.id.rbIds);
        radioCode = (RadioButton) view.findViewById(R.id.rbCode);

        if (radioID.isChecked()) {
            layoutID.setVisibility(View.VISIBLE);
            layoutCode.setVisibility(View.GONE);
        } else {
            layoutID.setVisibility(View.GONE);
            layoutCode.setVisibility(View.VISIBLE);
        }

        radioID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection = "id";
                    layoutID.setVisibility(View.VISIBLE);
                    layoutCode.setVisibility(View.GONE);

                    if (list.size() > 0) {
                        list.clear();
                        Search.this.adapter.notifyDataSetChanged();
                    }

                }

            }
        });

        radioCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selection = "code";
                if (isChecked) {
                    selection = "code";
                    layoutID.setVisibility(View.GONE);
                    layoutCode.setVisibility(View.VISIBLE);

                    if (list.size() > 0) {
                        list.clear();
                        Search.this.adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selection.equals("id")) {
                    if (!etIds.getText().toString().equals("")) {

                        //cursor to get data from db
                        Cursor c = db.getData(Integer.parseInt(etIds.getText().toString()));

                        //if there is no such data in db
                        if (c.getCount() == 0) {
                            Toast.makeText(getActivity(), "Invalid ID", Toast.LENGTH_SHORT).show();
                            list.clear();
                            Search.this.adapter.notifyDataSetChanged();
                            return;
                        }

                        if (c != null) {
                            c.moveToFirst();
                        }

                        Modal grade = new Modal(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)), Double.parseDouble(c.getString(5)));
                        list.clear();
                        list.add(grade);
                        Search.this.adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Invalid ID", Toast.LENGTH_SHORT).show();
                    }

                } else if(selection.equals("code")){

                    Cursor c = db.getAllGradesWithSubjectCode(spin.getSelectedItem().toString());

                    if (c.getCount() == 0) {
                        Toast.makeText(getActivity(), "Data does not Found", Toast.LENGTH_SHORT).show();
                        list.clear();
                        Search.this.adapter.notifyDataSetChanged();
                        return;
                    }

                    if (c.moveToFirst()) {
                        do {

                            Modal grade = new Modal(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), Integer.parseInt(c.getString(4)), Double.parseDouble(c.getString(5)));
                            list.add(grade);
                            Log.e("list", "onClick: " + list);
                        } while (c.moveToNext());
                    }

                    if (list.size() == 0) {
                        Toast.makeText(getActivity(), "Data does not Found", Toast.LENGTH_SHORT).show();
                        list.clear();
                        Search.this.adapter.notifyDataSetChanged();
                        return;
                    }

                    Search.this.adapter.notifyDataSetChanged();
                }


            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}