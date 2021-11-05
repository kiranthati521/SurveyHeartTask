package com.mvvm.surveyheartcontacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mvvm.surveyheartcontacts.dbhandler.DBHandler;
import com.mvvm.surveyheartcontacts.utils.Utils;

public class AddorUpdateContactActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView idText;
    private EditText etName, etMNO, etEmail;
    private RadioButton rbPersonalContact, rbBusinessContact;
    private Button btnSubmit;
    private AddorUpdateContactActivity thisActvity;
    public static final String EXTRA_SERIAZABLE_DATA= "ExtraSeriazableData";
    public static final String DATA_TYPE= "dataType";

    private String getType;
    private ContactModel contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);

        thisActvity = AddorUpdateContactActivity.this;

        Bundle bundle = getIntent().getExtras();
        getType = bundle.getString(DATA_TYPE);
        if (bundle.containsKey(EXTRA_SERIAZABLE_DATA)) {
            contactModel = (ContactModel) bundle.getSerializable(EXTRA_SERIAZABLE_DATA);
        }
        initView();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validation = validationContacts();

                if (validation ==  null){

                    ContactModel model = new ContactModel();
                    model.setName(Utils.isValidStr(etName.getText().toString()) ? etName.getText().toString() : "");
                    model.setMobileNumber(Utils.isValidStr(etMNO.getText().toString()) ? etMNO.getText().toString() : "");
                    model.setEmail(Utils.isValidStr(etEmail.getText().toString()) ? etEmail.getText().toString() : "");
                    if (rbPersonalContact.isChecked())
                        model.setContactType(rbPersonalContact.getTag().toString());
                    else if (rbBusinessContact.isChecked())
                        model.setContactType(rbBusinessContact.getTag().toString());

                    if (btnSubmit.getText().equals("Submit")) {
                        String dataInsertion = ContactsListDAO.getInstance().insert(model, DBHandler.getWritableDb(thisActvity));

                        if (dataInsertion.equalsIgnoreCase("")) {
                            Toast.makeText(thisActvity, "Contact saved successfully", Toast.LENGTH_LONG).show();
                            clearFields();
                        }
                    }
                    else {
                        model.setId(Integer.parseInt(idText.getText().toString()));
                        boolean dataUpdate = ContactsListDAO.getInstance().update(model, DBHandler.getWritableDb(thisActvity));

                        if (dataUpdate) {
                            Toast.makeText(thisActvity, "Contact Updated successfully", Toast.LENGTH_LONG).show();
                            clearFields();
                        }
                    }
                }
                else {
                    Toast.makeText(thisActvity, validation, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void initView() {

        idText = findViewById(R.id.idText);
        etName = findViewById(R.id.etName);
        etMNO = findViewById(R.id.etMNO);
        etEmail = findViewById(R.id.etEmail);
        rbPersonalContact = findViewById(R.id.rbPersonalContact);
        rbBusinessContact = findViewById(R.id.rbBusinessContact);
        btnSubmit = findViewById(R.id.btnSubmit);

        rbPersonalContact.setOnCheckedChangeListener(this);
        rbBusinessContact.setOnCheckedChangeListener(this);

        if (Utils.isValidStr(getType)){
            if (getType.equalsIgnoreCase("Update") && contactModel!=null){

                if (Utils.isValidStr(contactModel.getName())){
                    etName.setText(contactModel.getName());
                }

                if (Utils.isValidStr(contactModel.getMobileNumber())){
                    etMNO.setText(contactModel.getMobileNumber());
                }

                if (Utils.isValidStr(contactModel.getEmail())){
                    etEmail.setText(contactModel.getEmail());
                }

                if (Utils.isValidStr(contactModel.getContactType())){
                    if (rbBusinessContact.getTag().toString().equalsIgnoreCase(contactModel.getContactType()))
                        rbBusinessContact.setChecked(true);
                    else
                        rbPersonalContact.setChecked(true);

                    idText.setText(contactModel.getId()+"");
                    btnSubmit.setText("Update");
                }
            }
        }
        else {
            clearFields();
        }

    }

    private void clearFields() {
        etName.setText("");
        etMNO.setText("");
        etEmail.setText("");
        rbBusinessContact.setChecked(false);
        rbPersonalContact.setChecked(false);
        idText.setText("");
        btnSubmit.setText("Submit");
    }

    private String validationContacts() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!Utils.isValidStr(etName.getText().toString())) {
            return "Enter Name";

        }else if (!Utils.isValidStr(etMNO.getText().toString())) {
            return "Enter Mobile number";
        }
        else if (etMNO.getText().toString().length()<10) {
            return "Enter Valid mobile number";
        }
        if (!Utils.isValidStr(etEmail.getText().toString())) {
            return "Enter Email Id";
        }
        else if (Utils.isValidStr(etEmail.getText().toString()) && !etEmail.getText().toString().trim().matches(emailPattern)) {
            return "Enter Valid Email Id";
        }
        else if (!rbPersonalContact.isChecked() && !rbBusinessContact.isChecked()){
            return "Please select any one Personal or Business contact";
        }

        return null;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.rbBusinessContact:
                if (isChecked) {
                    rbBusinessContact.setChecked(true);
                    rbPersonalContact.setChecked(false);
                }

                break;

            case R.id.rbPersonalContact:
                if (isChecked) {
                    rbBusinessContact.setChecked(false);
                    rbPersonalContact.setChecked(true);
                }
                break;
        }
    }
}
