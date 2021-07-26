package com.example.hotel_reservation_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HotelGuestListAdapter extends RecyclerView.Adapter<HotelGuestListAdapter.ViewHolder>
{

    private int guestCount;
    private LayoutInflater layoutInflater;

    //Data gets passed in the constructor
    HotelGuestListAdapter(Context context, int guestCount) {
        this.layoutInflater = LayoutInflater.from(context);
        this.guestCount = guestCount;
    }

    @NonNull
    @Override
    public HotelGuestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hotel_guest_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelGuestListAdapter.ViewHolder holder, int position) {
        System.out.println("Binding");
    }

    @Override
    public int getItemCount(){
        return guestCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText guestNameEditText;
        RadioButton genderMale,genderFemale;
        Button submitButton;
        String guestName,gender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guestNameEditText = itemView.findViewById(R.id.guest_name_edit_text_view);
            genderMale = itemView.findViewById(R.id.radio_male);
            genderFemale = itemView.findViewById(R.id.radio_female);

            genderMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gender = "male";
                }
            });
            genderFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gender = "female";
                }
            });

        }
    }

}