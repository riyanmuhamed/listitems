package com.example.listitemexample;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private Button displayButton, saveButton;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "ListFragmentPrefs";
    private static final String KEY_SAVED = "isListSaved";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Initialize SharedPreferences
        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);

        // Initialize buttons
        displayButton = view.findViewById(R.id.display_button);
        saveButton = view.findViewById(R.id.save_button);

        // Use ViewModel
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        // Observe the list of items
        listViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> items) {
                listAdapter.setItems(items);
            }
        });


        boolean isListSaved = sharedPreferences.getBoolean(KEY_SAVED, false);
        if (isListSaved) {
            recyclerView.setVisibility(View.VISIBLE);
        }


        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> items = listAdapter.getItems();
                if (items != null) {
                    listViewModel.setItems(items);
                    sharedPreferences.edit().putBoolean(KEY_SAVED, true).apply();
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sharedPreferences.getBoolean(KEY_SAVED, false)) {
            List<String> items = listAdapter.getItems();
            if (items != null) {
                listViewModel.setItems(items);
            }
        }
    }
}

