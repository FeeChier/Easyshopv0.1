package com.example.easyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView firestorelist;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestorelist = findViewById(R.id.liste_firestore);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Magasins");
        FirestoreRecyclerOptions<ModelMagasin> options = new FirestoreRecyclerOptions.Builder<ModelMagasin>()
                .setQuery(query, ModelMagasin.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ModelMagasin, MagasinViewHolder>(options) {
            @NonNull
            @Override
            public MagasinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new MagasinViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MagasinViewHolder holder, int position, @NonNull ModelMagasin model) {
                holder.list_name.setText(model.getName());
                holder.list_adress.setText(model.getAdress());
            }
        };
        firestorelist.setHasFixedSize(true);
        firestorelist.setLayoutManager(new LinearLayoutManager(this));
        firestorelist.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



    }
        private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new HomeFragment();
                                break;
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


    private class MagasinViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_adress;
        public MagasinViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name=itemView.findViewById(R.id.list_name);
            list_adress=itemView.findViewById(R.id.list_adress);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
