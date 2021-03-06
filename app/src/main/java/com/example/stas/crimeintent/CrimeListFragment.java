package com.example.stas.crimeintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private Crime mCrime;

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class SeriousCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Crime mCrime;

       /* public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }*/
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public TextView getTitleTextView(){
            return mTitleTextView;
        }
        public TextView getDateTextView(){
            return mDateTextView;
        }

        public SeriousCrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_serious_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.serious_crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.serious_crime_date);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Crime> mCrimes;
        private final int SERIOUS_CRIME =1,EASY_CRIME = 0;

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice()){
                return SERIOUS_CRIME;
            }else {
                return EASY_CRIME;
            }
        }

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;

        }
        /*if (viewType==0){
                layoutInflater.inflate(R.layout.list_item_crime,parent,false);
                return new CrimeHolder(layoutInflater, parent);
            }else {
                layoutInflater.inflate(R.layout.list_item_serious_crime,parent,false);
                return new SeriousCrimeHolder(layoutInflater, parent);
            }*/

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           RecyclerView.ViewHolder viewHolder;
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType){
                case SERIOUS_CRIME:
                    layoutInflater.inflate(R.layout.list_item_serious_crime,
                            parent, false);
                    viewHolder = new SeriousCrimeHolder(layoutInflater, parent);
                    break;
                case EASY_CRIME:
                    layoutInflater.inflate(R.layout.list_item_serious_crime,
                            parent, false);
                    viewHolder = new CrimeHolder(layoutInflater, parent);
                    break;
                default:
                    layoutInflater.inflate(R.layout.list_item_serious_crime,
                            parent, false);
                    viewHolder = new CrimeHolder(layoutInflater, parent);
                    break;
            }
            return viewHolder;
        }

        private void seriousBind(SeriousCrimeHolder holder, Crime crime){
            holder.getDateTextView().setText(crime.getDate().toString());
            holder.getTitleTextView().setText(crime.getTitle());
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);

            switch (holder.getItemViewType()){
                case SERIOUS_CRIME:
                    SeriousCrimeHolder sch = (SeriousCrimeHolder) holder;
                    seriousBind(sch, crime);
                    break;
                case EASY_CRIME:
                    CrimeHolder ch = (CrimeHolder) holder;
                    ch.bind(crime);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);

    }


}
