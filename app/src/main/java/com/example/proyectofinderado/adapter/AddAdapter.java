package com.example.proyectofinderado.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinderado.R;
import com.example.proyectofinderado.model.AddDto;
import com.example.proyectofinderado.model.AddToShow;

import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddHolder> {


    private List<AddToShow> addsList;
    //Recibir anuncios desde la api

    public AddAdapter(List<AddToShow> addsList){

        this.addsList = addsList;
    }

    @NonNull
    @Override
    public AddHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_add_item,parent,false);
        return new AddHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddHolder holder, int position) {

        AddToShow add = addsList.get(position);
        holder.txtvName.setText(add.getName());
        holder.txtvDescription.setText(add.getDescription());
        holder.txtvPhone.setText(add.getPhone());
        holder.txtvProvince.setText(add.getProvince());
        holder.txtvLocalidad.setText(add.getTown());
        holder.txtvSubject1.setText(add.getSubject());

    }

    @Override
    public int getItemCount() {

        return addsList.size();
    }
}
