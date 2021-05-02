package com.example.easyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick, View.OnClickListener{
    private FirebaseFirestore firebaseFirestore;


    private RecyclerView firestorelist;
    private FirestorePagingAdapter adapter;
    private ImageButton btt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btt = (ImageButton) findViewById(R.id.connexionButton);
        btt.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        firestorelist = findViewById(R.id.liste_firestore);
        firebaseFirestore = FirebaseFirestore.getInstance();


        ImageButton logoButton = findViewById(R.id.logoButton);


        Query query = firebaseFirestore.collection("Magasins");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(5)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<ModelMagasin> options = new FirestorePagingOptions.Builder<ModelMagasin>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<ModelMagasin>() {
                    @NonNull
                    @Override
                    public ModelMagasin parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ModelMagasin modelMagasin = snapshot.toObject(ModelMagasin.class);
                        String itemId = snapshot.getId();
                        modelMagasin.setItem_id(itemId);
                        return modelMagasin;
                    }
                })
                .build();

        adapter = new FirestoreAdapter(options, this);


        firestorelist.setHasFixedSize(true);
        firestorelist.setLayoutManager(new LinearLayoutManager(this));
        firestorelist.setAdapter(adapter);

        toolbar.setOnMenuItemClickListener(navListener);
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.logoButton){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                }

            }
        });
    }


    private Toolbar.OnMenuItemClickListener navListener =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_premium:
                            selectedFragment = new PremiumFragment();
                            break;
                        case R.id.nav_options:
                            selectedFragment = new OptionFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ITEM_CLICK", "Clicked an item : " + position + " and the ID is :" + snapshot.getId());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connexionButton:
                startActivity(new Intent(this, MainActivity2.class));
                break;
        }
    }
}
