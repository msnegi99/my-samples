package com.msnegi.mydialogexamples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Dialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.SyncStateContract;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.msnegi.mydialogexamples.dialogs.AddCategoryDialog;
import com.msnegi.mydialogexamples.dialogs.ConfirmDeleteDialog;
import com.msnegi.mydialogexamples.dialogs.ForgotPasswordDialog;
import com.msnegi.mydialogexamples.dialogs.LoginPasswordDialog;
import com.msnegi.mydialogexamples.dialogs.MyAlertDialog;
import com.msnegi.mydialogexamples.dialogs.RateUsDialog;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button helloBtn = findViewById(R.id.infoBtn);
        helloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("An operating system (OS) is system software that manages computer hardware and software resources and provides common services for computer programs.");
            }
        });

        Button yesNoBtn = findViewById(R.id.yesNoBtn);
        yesNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitMessage();
            }
        });

        Button okCancelBtn = findViewById(R.id.okCancelBtn);
        okCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okCancelDialog();
            }
        });

        Button radioBtn = findViewById(R.id.radioBtn);
        radioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroupDialog();
            }
        });

        Button checkBtn = findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxList();
            }
        });

        Button listBtn = findViewById(R.id.listBtn);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });

        Button customListButn = findViewById(R.id.customListBtn);
        customListButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListDialog();
            }
        });

        Button inputDialogBtn = findViewById(R.id.inputDialogBtn);
        inputDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDialog();
            }
        });

        Button addCategoryBtn = findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });

        Button alertDialogBtn = findViewById(R.id.alertDialogBtn);
        alertDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternet();
            }
        });

        Button rateUsBtn = findViewById(R.id.rateUsBtn);
        rateUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateUs();
            }
        });

        Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });

        Button forgotPassBtn = findViewById(R.id.forgotPassBtn);
        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });

        Button loginPassBtn = findViewById(R.id.loginPassBtn);
        loginPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPassword();
            }
        });


    }

    private void showMessage(String msg)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        Dialog dialog1 = alertDialogBuilder.create();
        dialog1.setCancelable(false);
        dialog1.show();
        dialog1.setContentView(R.layout.info_dialog);

        TextView message = dialog1.findViewById(R.id.okButton);
        message.setText(msg);

        Button msgButton = dialog1.findViewById(R.id.msgbutton);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }


    private void exitMessage() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_app);

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void okCancelDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    int selected = 0;

    private void radioGroupDialog()
    {
        final CharSequence[] MAP_TYPE_ITEMS = {"Road", "Satellite", "Hybrid"};
        int checkItem = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Title");
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        selected = item;
                        switch (item) {
                            case 0:
                                Toast.makeText(MainActivity.this, "Item " + MAP_TYPE_ITEMS[item] + " clicked ", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "Item " + MAP_TYPE_ITEMS[item] + " clicked ", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "Item " + MAP_TYPE_ITEMS[item] + " clicked ", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
        );

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(MainActivity.this, "You selected : " + MAP_TYPE_ITEMS[selected], Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private String selectedColors = "";

    private void  checkBoxList()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose some animals");

        String[] colors = {"Red", "Green", "Blue", "Purple", "Olive"};
        boolean[] checkedColors = {true, false, false, true, false};
        builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                for (int i=0; i < checkedColors.length; i++) {
                    boolean checked = checkedColors[i];
                    if (checked) {
                        selectedColors = selectedColors + " " + colors[i];
                    }
                }
                Toast.makeText(MainActivity.this, selectedColors, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The user clicked OK
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose some animals");

        String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You Selected : " + animals[i], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void customListDialog()
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.icon_1);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private void userDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");

        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "You Selected : " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addCategory(){

        final AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
        addCategoryDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), new AddCategoryDialog.MyCallback() {
            @Override
            public void onSubmit(String new_category) {

                Toast.makeText(MainActivity.this, "You added : " + new_category, Toast.LENGTH_SHORT).show();
                addCategoryDialog.dismiss();
            }

            @Override
            public void onCrossImgClick() {
                addCategoryDialog.dismiss();
            }
        });
    }

    private void noInternet() {
        final MyAlertDialog myAlertDialog = new MyAlertDialog();
        myAlertDialog.setBodyText(getString(R.string.no_internet_available));
        myAlertDialog.setButtonText(getString(R.string.ok));
        myAlertDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), new MyAlertDialog.MyAlertSubmitCallback() {
            @Override
            public void onSubmit() {
                myAlertDialog.dismiss();
            }

            @Override
            public void onCrossImgClick() {
                myAlertDialog.dismiss();
            }
        });
    }

    public void rateUs(){
        final RateUsDialog rateUsDialog = new RateUsDialog();
        rateUsDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), new ConfirmDeleteDialog.MyCallback() {
            @Override
            public void onSubmit() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse ("http://play.google.com/store/apps/details?id="+getPackageName()));
                startActivity(intent);

                //sessionManager.setRated(true);
                rateUsDialog.dismiss();
                finish();
            }

            @Override
            public void onCrossImgClick() {
                rateUsDialog.dismiss();
                finish();
            }
        });

    }

    public void confirmDelete(){
        final ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog();
        confirmDeleteDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), new ConfirmDeleteDialog.MyCallback() {
            @Override
            public void onSubmit() {

                confirmDeleteDialog.dismiss();
            }

            @Override
            public void onCrossImgClick() {
                confirmDeleteDialog.dismiss();
            }
        });
    }

    public void forgotPassword(){
        final ForgotPasswordDialog myForgotPasswordDialog = new ForgotPasswordDialog();
        myForgotPasswordDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), "user_password", new ForgotPasswordDialog.MyAlertSubmitCallback() {
            @Override
            public void onSubmit() {
                myForgotPasswordDialog.dismiss();
            }

            @Override
            public void onCrossImgClick() {
                myForgotPasswordDialog.dismiss();
            }
        });
    }

    public void loginPassword()
    {
        final LoginPasswordDialog loginPasswordDialog = new LoginPasswordDialog();
        loginPasswordDialog.openMessagePopup(getSupportFragmentManager(), MainActivity.class.getName(), new LoginPasswordDialog.MyCallback() {
            @Override
            public void onSubmit(String loginPassword, String masterPassword) {

                loginPasswordDialog.dismiss();
            }

            @Override
            public void onCrossImgClick() {
                loginPasswordDialog.dismiss();
            }
        });
    }
}