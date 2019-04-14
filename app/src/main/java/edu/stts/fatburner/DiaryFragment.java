package edu.stts.fatburner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class DiaryFragment extends Fragment implements View.OnClickListener{
    private LinearLayout linearBreakfast;
    private LinearLayout linearLunch;
    private LinearLayout linearDinner;
    private LinearLayout linearSnacks;
    public DiaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        linearBreakfast = v.findViewById(R.id.linear_breakfast);
        linearLunch = v.findViewById(R.id.linear_lunch);
        linearDinner = v.findViewById(R.id.linear_dinner);
        linearSnacks = v.findViewById(R.id.linear_snack);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearBreakfast.setOnClickListener(this);
        linearLunch.setOnClickListener(this);
        linearDinner.setOnClickListener(this);
        linearSnacks.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.linear_breakfast ||v.getId() == R.id.linear_lunch||v.getId() == R.id.linear_dinner||v.getId() == R.id.linear_snack){
            startActivity(new Intent(getActivity(),FoodActivity.class));
        }
    }
}
