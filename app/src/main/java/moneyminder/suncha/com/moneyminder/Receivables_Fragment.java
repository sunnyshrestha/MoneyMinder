package moneyminder.suncha.com.moneyminder;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MSI on 11/15/2016.
 */

public class Receivables_Fragment extends android.support.v4.app.Fragment {
    @BindView(R.id.receivableRecyclerView)
    RecyclerView receivableRecyclerView;   
    
    ReceivablesAdapter receivablesAdapter;
    List<ReceivablesModel> receivablesModel = new ArrayList<>();
    long initialCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        receivableRecyclerView.setLayoutManager(gridLayoutManager);
        
        initialCount=ReceivablesModel.count(ReceivablesModel.class);
        
        if(initialCount>=0){
            receivablesModel = ReceivablesModel.listAll(ReceivablesModel.class);
            //Need to create adapter        
        }

        

        return inflater.inflate(R.layout.receivables_layout,container,false);

    }

    //https://github.com/Suleiman19/Android-Note-app-with-Sugar-ORM/blob/master/app/src/main/java/com/grafixartist/noteapp/MainActivity.java
}
