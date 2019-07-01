package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TabFragment3 extends Fragment implements View.OnClickListener {

    private static final int ADD_DATA_REQUEST = 0;
    private static final int EDIT_DATA_REQUEST = 1;
    private static final int RESULT_DELETED = 100;
    private static final int RESULT_EDITTED = 200;

    private FloatingActionButton addButton;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private TODORecyclerAdapter mAdapter1, mAdapter2, mAdapter3, mAdapter4;

    private ArrayList<TODOData> importantUrgentList = new ArrayList<>();
    private ArrayList<TODOData> importantNotUrgentList = new ArrayList<>();
    private ArrayList<TODOData> trivialUrgentList = new ArrayList<>();
    private ArrayList<TODOData> trivialNotUrgentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        addButton = (FloatingActionButton) view.findViewById(R.id.addTODOButton);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
        recyclerView4 = (RecyclerView) view.findViewById(R.id.recyclerView4);
        mAdapter1 = new TODORecyclerAdapter(importantUrgentList);
        mAdapter2 = new TODORecyclerAdapter(importantNotUrgentList);
        mAdapter3 = new TODORecyclerAdapter(trivialUrgentList);
        mAdapter4 = new TODORecyclerAdapter(trivialNotUrgentList);

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);

        mAdapter1.setOnItemClickListener(new TODORecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), EditTODOListActivity.class);
                putExtraHelper(intent, importantUrgentList, position);
                startActivityForResult(intent, EDIT_DATA_REQUEST);
            }
        });
        mAdapter2.setOnItemClickListener(new TODORecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), EditTODOListActivity.class);
                putExtraHelper(intent, importantNotUrgentList, position);
                startActivityForResult(intent, EDIT_DATA_REQUEST);
            }
        });
        mAdapter3.setOnItemClickListener(new TODORecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), EditTODOListActivity.class);
                putExtraHelper(intent, trivialUrgentList, position);
                startActivityForResult(intent, EDIT_DATA_REQUEST);
            }
        });
        mAdapter4.setOnItemClickListener(new TODORecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), EditTODOListActivity.class);
                putExtraHelper(intent, trivialNotUrgentList, position);
                startActivityForResult(intent, EDIT_DATA_REQUEST);
            }
        });

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView4.setLayoutManager(layoutManager4);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView4.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.setAdapter(mAdapter1);
        recyclerView2.setAdapter(mAdapter2);
        recyclerView3.setAdapter(mAdapter3);
        recyclerView4.setAdapter(mAdapter4);

        addButton.setOnClickListener(this);

        addExampleTODO();

        return view;
    }

    // TODO : Sort arrays after a change, create TODO entry item and change font for done TODO entries

    // TODO : Add done checklists?

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTODOButton) {
            Intent intent = new Intent(getActivity(), AddTODOActivity.class);
            startActivityForResult(intent, ADD_DATA_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case ADD_DATA_REQUEST: {
                if(resultCode == RESULT_CANCELED) {
                    // Do nothing?
                }

                else if(resultCode == RESULT_OK) {
                    TODOData todoData = new TODOData();
                    String title, date;
                    boolean isTrivial, isUrgent;
                    int position;

                    title = intent.getStringExtra("title");
                    date = intent.getStringExtra("date");
                    isTrivial = intent.getBooleanExtra("isTrivial", false);
                    isUrgent = intent.getBooleanExtra("isUrgent", true);

                    todoData.setTitle(title);
                    todoData.setDate(date);
                    todoData.setTrivial(isTrivial);
                    todoData.setUrgent(isUrgent);

                    position = getTargetedArray(isTrivial, isUrgent).size();
                    getTargetedArray(isTrivial, isUrgent).add(position, todoData);
                    getTargetedAdapter(isTrivial, isUrgent).notifyItemInserted(position);
                }
            }

            case EDIT_DATA_REQUEST: {
                if(resultCode == RESULT_CANCELED) {
                    // Do nothing?
                }

                else if(resultCode == RESULT_EDITTED) {
                    String title, date;
                    boolean isTrivial, isUrgent, isDone;
                    int initialPosition;

                    title = intent.getStringExtra("title");
                    date = intent.getStringExtra("date");
                    isTrivial = intent.getBooleanExtra("trivial", false);
                    isUrgent = intent.getBooleanExtra("urgent", true);
                    isDone = intent.getBooleanExtra("done", false);
                    initialPosition = intent.getIntExtra("position", 0);

                    if(intent.getBooleanExtra("samePosition", false)) {
                        getTargetedArray(isTrivial, isUrgent).get(initialPosition).setTitle(title);
                        getTargetedArray(isTrivial, isUrgent).get(initialPosition).setDate(date);
                        getTargetedAdapter(isTrivial, isUrgent).notifyItemChanged(initialPosition);
                    }
                    else {
                        boolean initialTrivial, initialUrgent;
                        TODOData todoData = new TODOData(title, date, isTrivial, isUrgent);
                        int position;

                        initialTrivial = intent.getBooleanExtra("initialTrivial", false);
                        initialUrgent = intent.getBooleanExtra("initialUrgent", true);
                        getTargetedArray(initialTrivial, initialUrgent).remove(initialPosition);
                        getTargetedAdapter(initialTrivial, initialUrgent).notifyItemRemoved(initialPosition);

                        position = getTargetedArray(isTrivial, isUrgent).size();
                        todoData.setDone(isDone);
                        getTargetedArray(isTrivial, isUrgent).add(position, todoData);
                        getTargetedAdapter(isTrivial, isUrgent).notifyItemInserted(position);
                    }
                }

                else if(resultCode == RESULT_DELETED) {
                    boolean isTrivial = intent.getBooleanExtra("initialTrivial", false);
                    boolean isUrgent = intent.getBooleanExtra("initialUrgent", true);
                    int position = intent.getIntExtra("position", 0);
                    getTargetedArray(isTrivial, isUrgent).remove(position);
                    getTargetedAdapter(isTrivial, isUrgent).notifyItemRemoved(position);
                }
            }
        }
    }

    private void putExtraHelper(Intent intent, ArrayList<TODOData> arrayList, int position) {
        intent.putExtra("title", arrayList.get(position).getTitle());
        intent.putExtra("date", arrayList.get(position).getDate());
        intent.putExtra("trivial", arrayList.get(position).isTrivial());
        intent.putExtra("urgent", arrayList.get(position).isUrgent());
        intent.putExtra("done", arrayList.get(position).isDone());
        intent.putExtra("position", position);
    }

    private ArrayList<TODOData> getTargetedArray(boolean trivial, boolean urgent) {
        if(trivial) {
            if(urgent) { return trivialUrgentList; }
            else { return trivialNotUrgentList; }
        }
        else {
            if(urgent) { return importantUrgentList; }
            else { return importantNotUrgentList; }
        }
    }

    private TODORecyclerAdapter getTargetedAdapter(boolean trivial, boolean urgent) {
        if(trivial) {
            if(urgent) { return mAdapter3; }
            else { return mAdapter4; }
        }
        else {
            if(urgent) { return mAdapter1; }
            else { return mAdapter2; }
        }
    }

    private void addExampleTODO() {
        TODOData todoData1, todoData2, todoData3, todoData4, todoData5,
                todoData6, todoData7, todoData8, todoData9, todoData10,
                todoData11, todoData12, todoData13, todoData14, todoData15,
                todoData16, todoData17, todoData18, todoData19, todoData20;

        todoData1 = new TODOData("TODO1", "20190702", false, true);
        todoData2 = new TODOData("TODO2", "20190816", false, false);
        todoData3 = new TODOData("TODO3", "20190705", true, true);
        todoData4 = new TODOData("TODO4", "20190921", true, false);
        todoData5 = new TODOData("My Birthday", "20191130", false, false);
        todoData6 = new TODOData("Buy new clothes", "20190708", true, true);
        todoData7 = new TODOData("Pintos", "20191202", false, false);
        todoData8 = new TODOData("Eat food", "20190702", false, false);
        todoData9 = new TODOData("Play game", "20190709", true, false);
        todoData10 = new TODOData("Do homework", "20190831", false, false);
        todoData11 = new TODOData("Go home", "20190726", false, true);
        todoData12 = new TODOData("Something", "20201230", true, false);
        todoData13 = new TODOData("Blah", "201910706", true, true);
        todoData14 = new TODOData("aaa", "20190830", true, false);
        todoData15 = new TODOData("asdfldkfj", "20211130", false, false);
        todoData16 = new TODOData("hello world", "20121212", false, true);
        todoData17 = new TODOData("suppp", "20190903", true, true);
        todoData18 = new TODOData("some words", "20191020", false, false);
        todoData19 = new TODOData("gotcha", "20191225", false, false);
        todoData20 = new TODOData("Last one", "20991231", false, true);
        todoData20.setDone(true);


        getTargetedArray(todoData1.isTrivial(), todoData1.isUrgent()).add(todoData1);
        getTargetedArray(todoData2.isTrivial(), todoData2.isUrgent()).add(todoData2);
        getTargetedArray(todoData3.isTrivial(), todoData3.isUrgent()).add(todoData3);
        getTargetedArray(todoData4.isTrivial(), todoData4.isUrgent()).add(todoData4);
        getTargetedArray(todoData5.isTrivial(), todoData5.isUrgent()).add(todoData5);
        getTargetedArray(todoData6.isTrivial(), todoData6.isUrgent()).add(todoData6);
        getTargetedArray(todoData7.isTrivial(), todoData7.isUrgent()).add(todoData7);
        getTargetedArray(todoData8.isTrivial(), todoData8.isUrgent()).add(todoData8);
        getTargetedArray(todoData9.isTrivial(), todoData9.isUrgent()).add(todoData9);
        getTargetedArray(todoData10.isTrivial(), todoData10.isUrgent()).add(todoData10);
        getTargetedArray(todoData11.isTrivial(), todoData11.isUrgent()).add(todoData11);
        getTargetedArray(todoData12.isTrivial(), todoData12.isUrgent()).add(todoData12);
        getTargetedArray(todoData13.isTrivial(), todoData13.isUrgent()).add(todoData13);
        getTargetedArray(todoData14.isTrivial(), todoData14.isUrgent()).add(todoData14);
        getTargetedArray(todoData15.isTrivial(), todoData15.isUrgent()).add(todoData15);
        getTargetedArray(todoData16.isTrivial(), todoData16.isUrgent()).add(todoData16);
        getTargetedArray(todoData17.isTrivial(), todoData17.isUrgent()).add(todoData17);
        getTargetedArray(todoData18.isTrivial(), todoData18.isUrgent()).add(todoData18);
        getTargetedArray(todoData19.isTrivial(), todoData19.isUrgent()).add(todoData19);
        getTargetedArray(todoData20.isTrivial(), todoData20.isUrgent()).add(todoData20);
    }
}