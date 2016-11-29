package moneyminder.suncha.com.moneyminder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 11/15/2016.
 */

public class Receivables_Fragment extends android.support.v4.app.Fragment {
    RecyclerView receivableRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ReceivablesAdapter receivablesAdapter;
    List<ReceivablesModel> receivablesModel = new ArrayList<>();
    long initialCount;
    int modifyPos = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receivables_layout, container, false);
        receivableRecyclerView = (RecyclerView) rootView.findViewById(R.id.receivableRecyclerView);

        //StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        receivableRecyclerView.setLayoutManager(linearLayoutManager);

        initialCount = ReceivablesModel.count(ReceivablesModel.class);

        if (initialCount >= 0) {
            receivablesModel = ReceivablesModel.listAll(ReceivablesModel.class);
            //Adapter
            receivablesAdapter = new ReceivablesAdapter(getActivity(), receivablesModel);
            receivableRecyclerView.setAdapter(receivablesAdapter);

            if (receivablesModel.isEmpty())
                Toast.makeText(getActivity(), "Nothing added", Toast.LENGTH_SHORT).show();
        } else {

        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        final long newCount = ReceivablesModel.count(ReceivablesModel.class);
        if (newCount > initialCount) {
            //A new entry is made
            //Load the latest added note
            ReceivablesModel newReceivable = ReceivablesModel.last(ReceivablesModel.class);
            receivablesModel.add(newReceivable);
            receivablesAdapter.notifyItemInserted((int) newCount);
            initialCount = newCount;
        }

    }

    //https://github.com/Suleiman19/Android-Note-app-with-Sugar-ORM/blob/master/app/src/main/java/com/grafixartist/noteapp/MainActivity.java
}
