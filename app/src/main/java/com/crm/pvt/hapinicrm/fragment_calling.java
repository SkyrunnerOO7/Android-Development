package com.crm.pvt.hapinicrm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_calling#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_calling extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView calling_fragment;
    private EditText name,city,phone;

    public fragment_calling() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_calling.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_calling newInstance(String param1, String param2) {
        fragment_calling fragment = new fragment_calling();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_calling,container,false);
        calling_fragment=view.findViewById(R.id.calling_fragment);
        name=view.findViewById(R.id.name_calling_fragment);
        city=view.findViewById(R.id.city_calling_fragment);
        phone=view.findViewById(R.id.phone_calling_fragment);

        calling_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1,city1,phone1;
                name1=name.getText().toString().trim();
                city1=city.getText().toString().trim();
                phone1=phone.getText().toString().trim();

                if (name1.isEmpty())
                {
                    name.setError("Please Enter name");
                    name.requestFocus();
                    return;
                }
                else if (city1.isEmpty())
                {
                    city.setError("Please Enter city");
                    city.requestFocus();
                    return;
                }
                else if (phone1.isEmpty())
                {
                    phone.setError("Please Enter phone number");
                    phone.requestFocus();
                    return;
                }
                else{
                    Toast.makeText(getActivity(),"Calling",Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
}