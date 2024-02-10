package com.sp.mini_assignment.FavouriteCarpark;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.mini_assignment.Adapters.Carpark;
import com.sp.mini_assignment.Adapters.CarparkAdapter;
import com.sp.mini_assignment.Adapters.FavouriteCarparkAdapter;
import com.sp.mini_assignment.Adapters.favouriteCarparkHelper;
import com.sp.mini_assignment.Home.Main;
import com.sp.mini_assignment.R;

import java.util.ArrayList; // Import ArrayList if needed
import java.util.List;

public class FavouriteCarpark extends Fragment {

    private List<Carpark> favouriteCarparks;
    private RecyclerView favCarparkRecyclerView;
    private FavouriteCarparkAdapter favouriteCarparkAdapter;

    private CarparkAdapter carparkAdapter;
    private favouriteCarparkHelper helper;

    public FavouriteCarpark(favouriteCarparkHelper helper) {
        this.helper = helper;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        favCarparkRecyclerView = rootView.findViewById(R.id.favourite_carparks_recycler_view);
        favCarparkRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        favouriteCarparks = helper.readAllData(10);

        Log.d("Favourite Carparks", "Favourite carparks size: " + favouriteCarparks.size());

        if (favouriteCarparks.isEmpty()) {
            // Handle the case when no favourite carparks are found
        } else {
            favouriteCarparkAdapter = new FavouriteCarparkAdapter(requireContext());
            favouriteCarparkAdapter.setCarparkList(favouriteCarparks);
            favCarparkRecyclerView.setAdapter(favouriteCarparkAdapter);
        }

        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (helper != null) {
            helper.close(); // Close the database connection when the fragment is destroyed
        }
    }


}
