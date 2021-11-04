package com.mvvm.surveyheartcontacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvvm.surveyheartcontacts.dbhandler.DBHandler;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ViewContactsActivity extends AppCompatActivity implements OnBookletClickListener, View.OnClickListener {

    private ViewContactsActivity thisActivity;
    private EditText edtSearch;
    private ImageView imgClear, imgSearch;
    private RecyclerView recyclerSearch;
    private ArrayList<ContactModel> originalList = new ArrayList<>();
    private ArrayList<ContactModel> searchList = new ArrayList<>();
    private SearchActivityAdapter searchActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact_layout);

        thisActivity = ViewContactsActivity.this;

        initView();


    }

    private void initView() {
        edtSearch = findViewById(R.id.edt_search);
        imgClear = findViewById(R.id.img_clear);
        imgSearch = findViewById(R.id.img_search);
        recyclerSearch = findViewById(R.id.recycle_view);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerSearch.setLayoutManager(layoutManager);
        searchActivityAdapter = new SearchActivityAdapter(thisActivity, searchList, this);
        recyclerSearch.setAdapter(searchActivityAdapter);
        searchActivityAdapter.notifyDataSetChanged();

        getContactListData();

        edtSearch.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchList.clear();
                if (s.toString().isEmpty()) {
                    searchList.addAll(originalList);
                } else {
                    for (ContactModel dto : originalList) {
                        if (Pattern.compile(Pattern.quote(edtSearch.getText().toString().trim()), Pattern.CASE_INSENSITIVE).matcher(dto.getName()).find()) {
                            searchList.add(dto);
                        }
                        else if (Pattern.compile(Pattern.quote(edtSearch.getText().toString().trim()), Pattern.CASE_INSENSITIVE).matcher(dto.getMobileNumber()).find()) {
                            searchList.add(dto);
                        }
                        else if (Pattern.compile(Pattern.quote(edtSearch.getText().toString().trim()), Pattern.CASE_INSENSITIVE).matcher(dto.getContactType()).find()) {
                            searchList.add(dto);
                        }
                        else if (Pattern.compile(Pattern.quote(edtSearch.getText().toString().trim()), Pattern.CASE_INSENSITIVE).matcher(dto.getEmail()).find()) {
                            searchList.add(dto);
                        }
                    }
                }
                searchActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getContactListData() {
        ArrayList<ContactModel> contactsList = ContactsListDAO.getInstance().getRecordsList(DBHandler.getReadableDb(thisActivity));
        if (contactsList!=null && contactsList.size()>0){
            searchList.clear();
            searchList.addAll(contactsList);
            originalList.clear();
            originalList.addAll(contactsList);
        }
        searchActivityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.btnUpdate:
                if (searchList!=null && searchList.size()>0) {
                    ContactModel contactModel = searchList.get(position);
                    Intent intent = new Intent(thisActivity, AddorUpdateContactActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AddorUpdateContactActivity.DATA_TYPE, "Update");
                    bundle.putSerializable(AddorUpdateContactActivity.EXTRA_SERIAZABLE_DATA, contactModel);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;

            case R.id.btnDelete:
                if (searchList!=null && searchList.size()>0) {
                    ContactModel contactModel = searchList.get(position);
                    boolean deleted = ContactsListDAO.getInstance().deleteRecordById(contactModel.getId(), DBHandler.getReadableDb(thisActivity));
                    if (deleted) {
                        Toast.makeText(thisActivity, "Record Deleted Succesfully", Toast.LENGTH_LONG).show();
                        getContactListData();
                    }
                }
                break;
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_clear:
                edtSearch.setText("");
                break;
            case R.id.edt_search:
                edtSearch.setFocusableInTouchMode(true);
                edtSearch.setFocusable(true);
                edtSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.img_search:
                edtSearch.setFocusableInTouchMode(true);
                edtSearch.setFocusable(true);
                edtSearch.requestFocus();
                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
        }
    }
}
