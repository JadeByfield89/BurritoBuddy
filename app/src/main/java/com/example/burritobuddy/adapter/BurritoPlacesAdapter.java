package com.example.burritobuddy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.burritobuddy.R;
import com.example.burritobuddy.model.BurritoPlace;

import java.util.ArrayList;

/**
 * This RecyclerView adapter will display the list of nearby burrito places that is returned from the Google Places API
 */
public class BurritoPlacesAdapter extends RecyclerView.Adapter<BurritoPlacesAdapter.ViewHolder> {

    private ArrayList<BurritoPlace> mPlacesList = new ArrayList<>();
    private OnPlaceSelectedListener mListener;


    // A listener that will notify the main view (MainActivity) anytime a place is selected in the list
    public interface OnPlaceSelectedListener{
        void onPlaceSelected(BurritoPlace place);
    }

    public BurritoPlacesAdapter(OnPlaceSelectedListener listener){
        this.mListener = listener;
    }

    public void setData(ArrayList<BurritoPlace> places){
        this.mPlacesList = places;
        notifyDataSetChanged();
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burrito_place, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        BurritoPlace place = mPlacesList.get(position);

        if(place.getPlaceName() != null && !place.getPlaceName().isEmpty()){
            viewHolder.placeName.setText(place.getPlaceName());
        }

        if(place.getPlaceAddress() != null && !place.getPlaceAddress().isEmpty()){
            viewHolder.placeAddress.setText(place.getPlaceAddress());
        }

        // According to the Google Place docs, price level ranges from 0 to 4, so let's render a dollar sign for each level
        String priceLevel = "";
        switch(place.getPriceLevel()){
            case 1:
                priceLevel = "$";
                break;


            case 2:
                priceLevel = "$$";
                break;

            case 3:
                priceLevel = "$$$";

                break;

            case 4:
                priceLevel = "$$$$";

                break;
        }

        viewHolder.placeInfo.setText(priceLevel + " • ");

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onPlaceSelected(mPlacesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent)
        View parent;

        @BindView(R.id.placeName)
        TextView placeName;

        @BindView(R.id.placeAddress)
        TextView placeAddress;

        @BindView(R.id.placeInfo)
        TextView placeInfo;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
