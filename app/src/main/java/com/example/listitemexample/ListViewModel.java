package com.example.listitemexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<String>> items = new MutableLiveData<>();

    public ListViewModel() {
        // Initialize with some data
        List<String> initialItems = new ArrayList<>();
        initialItems.add("rice");
        initialItems.add("pasta");
        initialItems.add("jukumando");
        initialItems.add("matoke");
        items.setValue(initialItems);
    }

    public LiveData<List<String>> getItems() {
        return items;
    }

    public void setItems(List<String> newItems) {
        items.setValue(newItems);
    }
}

