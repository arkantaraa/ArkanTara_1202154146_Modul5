package com.example.arkan.arkantaramuhammad_1202154146_modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //deklarasi variabel yang akan digunakan
    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List");


        rcView = findViewById(R .id.rec_view);
        //membuat araylist baru
        data_list = new ArrayList<>();
        //membuat database baru
        dtBase = new Database(this);
        //memanggil method readdata
        dtBase.readdata(data_list);

        //menginisialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);

        //membuat adapter baru
        adapter = new Adapter(this,data_list, color);

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        rcView.setAdapter(adapter);

        //menjalankan method hapus data pada list to do
        hapusgeser();
    }

    //membuat method untuk menghapus item pada to do list
    public void hapusgeser(){

        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);
                //apabila item di swipe ke arah kiri
                if(direction==ItemTouchHelper.LEFT){
                    //remove item yang dipilih dengan mengenali todonya sebagai primary key
                    if(dtBase.removedata(current.getTodo())){
                        //menghapus data
                        adapter.deleteData(position);

                        Snackbar.make(findViewById(R.id.coordinator), "List Telah Terhapus", 2000).show();
                    }
                }
            }
        };

        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //apabila item yang dipilih adalah setting
        if (id==R.id.action_settings){
            //membuat intent ke menu Settings
            Intent intent = new Intent(MainActivity.this, Settings.class);
            //memulai intent
            startActivity(intent);
            //menutup aktivitas setelah intent dijalankan
            finish();
        }
        return true;
    }

    //method yang akan dijalankan ketika tombol add di klik
    public void addlist(View view) {
        //intent ke class add to do
        Intent intent = new Intent(MainActivity.this, AddToDo.class);
        //memulai intent
        startActivity(intent);
    }
}
