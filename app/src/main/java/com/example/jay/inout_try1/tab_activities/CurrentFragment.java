package com.example.jay.inout_try1.tab_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.inout_try1.R;
import com.example.jay.inout_try1.mysql_connection.GetOrders;

/**
 * Created by jay on 31/10/2015.
 */
public class CurrentFragment extends Fragment {

    public static RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String[][] str;

    public static View view;

    String[][] orderArray;

    public CurrentFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_current,container,false);

        String method="current";


        mRecyclerView=(RecyclerView) view.findViewById(R.id.current_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(false);
        GetOrders getOrders=new GetOrders(this.getContext(),view,mRecyclerView,mAdapter);
        getOrders.execute(method);
//        mRecyclerView=(RecyclerView) view.findViewById(R.id.past_recycle_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(PastFragment.view.getContext()));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setHasFixedSize(false);
//        mRecyclerView.setAdapter(null);
        mAdapter=null;
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
