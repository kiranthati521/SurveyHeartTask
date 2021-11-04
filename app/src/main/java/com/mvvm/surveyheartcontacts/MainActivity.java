package com.mvvm.surveyheartcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mvvm.surveyheartcontacts.dbhandler.DBHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddContact, btnViewContact;
    private MainActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = MainActivity.this;

        btnAddContact = findViewById(R.id.btnAddContact);
        btnViewContact = findViewById(R.id.btnViewContact);

        btnAddContact.setOnClickListener(this);
        btnViewContact.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddContact:

                Intent intent = new Intent(thisActivity, AddorUpdateContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AddorUpdateContactActivity.DATA_TYPE, "New");
                bundle.putSerializable(AddorUpdateContactActivity.EXTRA_SERIAZABLE_DATA, null);
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case R.id.btnViewContact:

                ArrayList<ContactModel> contactsList = ContactsListDAO.getInstance().getRecordsList(DBHandler.getReadableDb(thisActivity));
                if (contactsList!=null && contactsList.size()>0) {
                    startActivity(new Intent(thisActivity, ViewContactsActivity.class));
                }
                else {
                    Toast.makeText(thisActivity, "No Data Available", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}