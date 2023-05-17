package com.example.proyectofinderado.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinderado.R;

public class AddHolder extends RecyclerView.ViewHolder {

    TextView txtvName, txtvLocalidad,txtvProvince, txtvPhone, txtvDescription,
    txtvSubject1;

    ImageView imgTablon;

    public AddHolder(@NonNull View itemView) {
        super(itemView);

        txtvName = itemView.findViewById(R.id.addsListAct_txtv_teacherName);
        txtvLocalidad = itemView.findViewById(R.id.addsListAct_txtv_Localidad);
        txtvProvince = itemView.findViewById(R.id.addsListAct_txtv_province);
        txtvPhone = itemView.findViewById(R.id.addsListAct_txtv_phone);
        txtvDescription = itemView.findViewById(R.id.addsListAct_txtv_description);
        txtvSubject1 = itemView.findViewById(R.id.addsListAct_txtv_subject1);




    }
}
