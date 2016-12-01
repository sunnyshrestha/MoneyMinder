package moneyminder.suncha.com.moneyminder;

import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllReceivables extends AppCompatActivity {
    RecyclerView recyclerView;
    ReceivablesAdapter receivablesAdapter;
    List<ReceivablesModel> receivables =  new ArrayList<>();
    long initialCount;
    int modifyPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_receivables);

        Log.d("main","onCreate");

        recyclerView = (RecyclerView)findViewById(R.id.viewallreceivables);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        initialCount = ReceivablesModel.count(ReceivablesModel.class);

        if(initialCount>=0){
            receivables = ReceivablesModel.listAll(ReceivablesModel.class);
            receivablesAdapter = new ReceivablesAdapter(ViewAllReceivables.this,receivables);
            recyclerView.setAdapter(receivablesAdapter);

            if(receivables.isEmpty())
                Snackbar.make(recyclerView,"Nothing here",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("modify",modifyPos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        modifyPos=savedInstanceState.getInt("modify");
    }
    
    // Handling swipe to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove swiped item from list and notify the RecyclerView

                final int position = viewHolder.getAdapterPosition();
                final ReceivableModel receivable = receivables.get(viewHolder.getAdapterPosition());
                receivables.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(position);

                receivable.delete();
                initialCount -= 1;

                Snackbar.make(recyclerView, getResources().getString(R.string.itemDeleted), Snackbar.LENGTH_SHORT)
                        .setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                note.save();
                                receivables.add(position, receivable);
                                adapter.notifyItemInserted(position);
                                initialCount += 1;
                            }
                        })
                        .show();
            }

        };
            
        }

    @Override
    protected void onResume() {
        super.onResume();

        final long newCount = ReceivablesModel.count(ReceivablesModel.class);
        if(newCount>initialCount){
            //An entry has been added
            //Load the last added note
            ReceivablesModel newReceivable = ReceivablesModel.last(ReceivablesModel.class);
            receivables.add(newReceivable);
            receivablesAdapter.notifyItemInserted((int)newCount);
            initialCount=newCount;
        }
        if(modifyPos!=-1){
            receivables.set(modifyPos,ReceivablesModel.listAll(ReceivablesModel.class).get(modifyPos));
            receivablesAdapter.notifyItemChanged(modifyPos);
        }
    }
}
