package pt.ipleiria.simplecontactsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<String>();
        contacts.add("André Marques | 961234567");
        contacts.add("Marcelo Faustino | 912903991");
        contacts.add("Mickaël Constatino | 912345678");
        contacts.add("Sandrinha Pereira | 914689261");
        contacts.add("Sofia Pereira | 9145862846");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);

        ListView listView = (ListView) findViewById(R.id.ListView_contacts);
        listView.setAdapter(adapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_sreach);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_option, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "clicou no item " + position, Toast.LENGTH_SHORT).show();

                contacts.remove(position);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, contacts);
                ListView listView = (ListView) findViewById(R.id.ListView_contacts);
                listView.setAdapter(adapter);
            }
        });
    }

    public void onClick_search(View view) {
        EditText text = (EditText) findViewById(R.id.editText_search);
        Spinner sp = (Spinner) findViewById(R.id.spinner_sreach);
        ListView lv = (ListView) findViewById(R.id.ListView_contacts);

        String Item = (String)sp.getSelectedItem();
        ArrayList<String> sreach_contact = new ArrayList<String>();
        String termo = text.getText().toString();

        if (termo.equals("")){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, contacts);
            lv.setAdapter(adapter);
            Toast.makeText(MainActivity.this, "Showing all contacts.", Toast.LENGTH_SHORT).show();
        }else {
            if (Item.equals("ALL")) {
                for (String c : contacts) { // : -> em
                    if (c.contains(termo)) {
                        sreach_contact.add(c);
                    }
                }
            } else if (Item.equals("Name")) {
                for (String c : contacts) {
                    String[] s = c.split("\\|");
                    String name = s[0];
                    if (name.contains(termo)) {
                        sreach_contact.add(c);
                    }
                }
            } else if (Item.equals("Phone")) {
                for (String c : contacts) {
                    String[] s = c.split("\\|");
                    String phone = s[1];
                    if (phone.contains(termo)) {
                        sreach_contact.add(c);
                    }
                }
            }
            boolean vazia = sreach_contact.isEmpty();

            if(!vazia){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, sreach_contact);
                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Showing all contacts.", Toast.LENGTH_SHORT).show();
            }else{
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, contacts);
                lv.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "No results found. Showing all contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClick_add(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add, null));
        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                AlertDialog al = (AlertDialog) dialog;

                EditText etName = (EditText)al.findViewById(R.id.editText_name);
                EditText etPhone = (EditText)al.findViewById(R.id.editText_phone);

                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String newContact = name + " | " + phone;

                contacts.add(newContact);
                ListView lv = (ListView) findViewById(R.id.ListView_contacts);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, contacts);
                lv.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "Novo Contacto adicionado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

            }
        });
        // Set other dialog properties
        builder.setTitle("Novo Contacto");
        builder.setMessage("Introduza o mome e o telefone do contacto!");
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void onClick_remover(View view){
        ListView lv = (ListView) findViewById(R.id.ListView_contacts);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, contacts);
        lv.setAdapter(adapter);
        adapter.clear();
    }
}