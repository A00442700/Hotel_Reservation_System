package com.example.hotel_reservation_system;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class HotelGuestDetailsFragment extends Fragment {

    View view;
    EditText guestNameEditText;
    ProgressBar progressBar;
    int guestCount = 0;
    //RadioGroup radioGroup;
    Button send;
    RadioButton genderMale,genderFemale;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    Button submitButton;
    String gender,guestName,hotelName,hotelPrice,hotelAvailability,check_in_date,check_out_date;

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String name = "nameKey";
    public static final String guestsCount = "guestsCount";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_guest_details_fragment, container, false);
        //view = inflater.inflate(R.layout.hotel_guest_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView hotelRecapTextView = view.findViewById(R.id.hotel_recap_text_view);

        //guestsCountEditText = view.findViewById(R.id.guests_count_edit_text);

        hotelName = getArguments().getString("hotel name");
        hotelPrice = getArguments().getString("hotel price");
        hotelAvailability = getArguments().getString("hotel availability");
        check_in_date = getArguments().getString("check in date");
        check_out_date = getArguments().getString("check out date");
        submitButton = view.findViewById(R.id.submit_button);
        hotelRecapTextView.setText("You have selected " + hotelName + " from " + check_in_date + " to " + check_out_date + ". The cost will be $ " + hotelPrice + " and availability is " + hotelAvailability);

        setupRecyclerView();

        submitButton.setOnClickListener(new View.OnClickListener() {
            JSONObject jsonObj;
            int f=0;
            TypedInput in;
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                recyclerView = view.findViewById(R.id.guest_details_list_recyclerView);
                JSONArray array = new JSONArray();

                for(int i = 0; i< guestCount; i++){
                    guestNameEditText = view.findViewById(R.id.guest_name_edit_text_view);
                    guestName = guestNameEditText.getText().toString();
                    genderMale = view.findViewById(R.id.radio_male);
                    genderFemale = view.findViewById(R.id.radio_female);
                    genderMale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gender = "male";
                            System.out.println("mallllllllllllll");
                        }
                    });
                    genderFemale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("hhhhhhhhhhhhhhhhhh");
                            gender = "female";
                        }
                    });
                    jsonObj = new JSONObject();
                    try {
                        gender = "male";
                        jsonObj.put("guest_name",guestName);
                        jsonObj.put("gender",gender);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    array.put(jsonObj);
                    }
                jsonObj = new JSONObject();
                try {
                    jsonObj.put("hotel_name",hotelName);
                    jsonObj.put("check_in_date",check_in_date);
                    jsonObj.put("check_out_date",check_out_date);
                    jsonObj.put("guests_list",array);
                    in = new TypedByteArray("application/json", jsonObj.toString().getBytes("UTF-8"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                if (f==0) {
                    Api.getClient().reserveHotel(in, new Callback<HotelConfirmationData>() {

                        @Override
                        public void success(HotelConfirmationData confirmationNumber, Response response) {
                            Bundle bundle = new Bundle();
                            bundle.putString("reservation number", confirmationNumber.getReservationNumber());
                            HotelConfirmationFragment confirmationFragment=new HotelConfirmationFragment();
                            confirmationFragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_layout, confirmationFragment);
                            fragmentTransaction.remove(HotelGuestDetailsFragment.this);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }
    private void setupRecyclerView() {
        //progressBar.setVisibility(View.GONE);
        sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        int guestNumber = 0;
        if (sharedPreferences.contains(guestsCount)) {
            guestNumber= Integer.parseInt(sharedPreferences.getString(guestsCount, ""));
        }
        System.out.println("GuestCount" +guestNumber);
        RecyclerView recyclerView = view.findViewById(R.id.guest_details_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelGuestListAdapter guestListAdapter=new HotelGuestListAdapter(getActivity(),guestNumber);
        recyclerView.setAdapter(guestListAdapter);

    }

}
