package com.example.member_registration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.member_registration.Adapter.SearchAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    MaterialSearchBar searchBar;
    List<String> suggestList = new ArrayList<>();
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBar = (MaterialSearchBar) findViewById(R.id.sv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        data = new Data(this);
        searchBar.setHint("Search");
        searchBar.setCardViewElevation(10);
        loadSuggestList();
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {

                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        adapter=new SearchAdapter(this,data.getEmp());

    }

    private void startSearch(String text)

    {
        String str=searchBar.getText();
        if (str.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please enter some text!!",Toast.LENGTH_SHORT).show();
        }
        else if (data.checkSearch(str)){
            Intent intent = new Intent(this, AfterSearch.class);
            intent.putExtra("searchbar", str);
            startActivity(intent);
        }

        else{
            Toast.makeText(getApplicationContext(),"Not found",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSuggestList() {
        suggestList=data.getNames();
        searchBar.setLastSuggestions(suggestList);
    }
}
