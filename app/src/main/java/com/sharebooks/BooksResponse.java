package com.sharebooks;

/**
 * Created by Jorge on 18/02/2018.
 */
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksResponse {
    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("items")
    private List<Book> items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
