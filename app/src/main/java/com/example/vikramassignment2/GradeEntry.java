package com.example.vikramassignment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class GradeEntry extends Fragment implements AdapterView.OnItemSelectedListener {

    String[] programs = {"PROG 8450", "PROG 8460", "PROG 8470", "PROG 8480"};
    private RadioGroup rbGroup;
    private RadioButton rbCredit;
    EditText firstname, lastname, marks;
    private DatabaseHelper db;
    String Course = "";
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate this fragment
        view = inflater.inflate(R.layout.fragment_grade_entry, container, false);

        db = new DatabaseHelper(getActivity());

        //setting view to variables
        rbGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        Button submit = (Button) view.findViewById(R.id.btnSubmit);
        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        marks = (EditText) view.findViewById(R.id.marks);
        Spinner spin = (Spinner) view.findViewById(R.id.courseSpinner);


        //created spinner to select course
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, programs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for validating user input data
                validateData();

                //to store data in database
                storeData();
            }
        });

        return view;

    }


    private void storeData() {

        int selectedId = rbGroup.getCheckedRadioButtonId();
        rbCredit = (RadioButton) view.findViewById(selectedId);

        Modal modal = new Modal();
        modal.setfName(firstname.getText().toString());
        modal.setlName(lastname.getText().toString());
        modal.setCourse(Course);
        modal.setCredit(Integer.parseInt(rbCredit.getText().toString()));
        modal.setGrades(Double.parseDouble(marks.getText().toString()));

        boolean check = db.addGrade(modal);
        if (check) {
            Toast.makeText(getActivity(), "Data Added Successfully.", Toast.LENGTH_SHORT).show();
            firstname.setText("");
            lastname.setText("");
            marks.setText("");
        } else {
            Toast.makeText(getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Course = programs[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void validateData() {
        if (firstname.getText().toString().equals(" ")) {
            Toast.makeText(getActivity(), "Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lastname.getText().toString().equals(" ")) {
            Toast.makeText(getActivity(), "Enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (marks.getText().toString().equals(" ")) {
            Toast.makeText(getActivity(), "Enter Grades", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}