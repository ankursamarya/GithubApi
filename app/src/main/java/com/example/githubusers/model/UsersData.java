
package com.example.githubusers.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersData {

    @SerializedName("total_count")
    @Expose
    public int totalCount;
    @SerializedName("incomplete_results")
    @Expose
    public boolean incompleteResults;
    @SerializedName("items")
    @Expose
    public List<User> users = new ArrayList<>();

}
