package com.example.arkan.arkantaramuhammad_1202154146_modul5;

/**
 * Created by Arkan on 25/03/2018.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {
    //deklarasi variable yang akan digunakan
    private EditText ToDo, Description, Priority;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        //set title menjadi add to do
        setTitle("Add To Do");

        //merefer semua variabel yang ada
        ToDo = (EditText) findViewById(R.id.edt_Todo);
        Description = (EditText) findViewById(R.id.edt_Desc);
        Priority = (EditText) findViewById(R.id.edt_Priority);
        db = new Database(this);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddToDo.this, MainActivity.class);

        startActivity(intent);

        this.finish();
    }

    public void addTodo(View view) {
        // data todoname, deskripsi dan prioritas di isi,
        if (db.inputdata(new AddData(ToDo.getText().toString(), Description.getText().toString(), Priority.getText().toString()))){

            Toast.makeText(this, "To Do List Ditambahkan !", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(AddToDo.this, MainActivity.class));

            this.finish();
        }else {
            //apabila edit text kosong maka akan muncul toast bahwa tidak bisa menambah ke dalam list
            Toast.makeText(this, "List tidak boleh kosong", Toast.LENGTH_SHORT).show();
            //set semua edit text menjadi kosong
            ToDo.setText(null);
            Description.setText(null);
            Priority.setText(null);
        }
    }

}
