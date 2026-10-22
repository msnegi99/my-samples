package com.msnegi.formdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class FormFragment extends Fragment {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    //private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private ImageView myImage;
    private EditText name, age, sex, email_id, bank_accoun_no, ifsc_code, bank_name, password;
    private Button save, validateBtn;
    private Button show;

    private static int RESULT_LOAD_IMAGE = 1;
    private String imageStringToSave = "";

    CallBackInterface callback;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Form Sample");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        myImage = (ImageView) view.findViewById(R.id.myImage);
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        sex = (EditText) view.findViewById(R.id.sex);
        email_id = (EditText) view.findViewById(R.id.email_id);
        bank_accoun_no = (EditText) view.findViewById(R.id.bank_accoun_no);
        ifsc_code = (EditText) view.findViewById(R.id.ifsc_code);
        bank_name = (EditText) view.findViewById(R.id.bank_name);
        password = (EditText) view.findViewById(R.id.password);
        save = (Button) view.findViewById(R.id.save);
        validateBtn = (Button) view.findViewById(R.id.validateBtn);

        name.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ "), new InputFilter.AllCaps(), new InputFilter.LengthFilter(50)});
        bank_name.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .,&"), new InputFilter.AllCaps(), new InputFilter.LengthFilter(50)});
        bank_accoun_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        ifsc_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        //bank_address.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .,&-/"),new InputFilter.AllCaps(), new InputFilter.LengthFilter(150)});
        //pan_id.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),new InputFilter.AllCaps(), new InputFilter.LengthFilter(10)});
        //voter_id.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),new InputFilter.AllCaps(), new InputFilter.LengthFilter(10)});
        //driving_id.setFilters(new InputFilter[]{new StringInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),new InputFilter.AllCaps(), new InputFilter.LengthFilter(15)});

        bank_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //-- call service or save here.
                }
                return true;
            }
        });

        email_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (email_id.getText().toString().trim().length() > 0 && !isValidEmail(email_id.getText().toString().trim())) {
                        email_id.setError("Please provide a valid email id");
                        setFocus(email_id);
                    }
                }
            }
        });

        bank_accoun_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (bank_accoun_no.getText().toString().trim().length() > 0) {
                        if (bank_accoun_no.getText().toString().trim().length() < 9 || bank_accoun_no.getText().toString().trim().length() > 25){
                            bank_accoun_no.setError("Bank account no should be between 9 and 25 characters");
                            setFocus(bank_accoun_no);
                        }
                    }
                }
            }
        });

        ifsc_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (ifsc_code.getText().toString().trim().length() != 11) {
                        ifsc_code.setError("IFSC code should be of 11 characters");
                        setFocus(ifsc_code);
                    }
                }
            }
        });

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validiate2(password.getText().toString().trim())){
                    Toast.makeText(getActivity(),"Valid Password",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"In-Valid Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPAN(CharSequence target) {
        Pattern pattern = Pattern.compile("[A-Z](5)[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            myImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            imageStringToSave = convertToBase64(picturePath);
        }
    }

    private String convertToBase64(String imagePath) {
        String encodedImage = "";

        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        int size = byteSizeOf(bm);

        if (size > 8294400) {
            Toast.makeText(getActivity(), "Big images more than 8mb can not be saved !!!", Toast.LENGTH_SHORT).show();
            myImage.setImageBitmap(null);
            myImage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }
        return encodedImage;
    }

    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    public void setFocus(EditText editText){
        /*name.clearFocus();
        age.clearFocus();
        sex.clearFocus();
        email_id.clearFocus();
        bank_accoun_no.clearFocus();
        ifsc_code.clearFocus();
        bank_name.clearFocus();
        editText.requestFocus();*/
    }

   //------------ password validation ----------//
   // if you don't care why it fails and only want to know if valid or not
   public static boolean validiate (String pass, String username, String email){
       String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
       if(pass.matches(pattern)){
           for(int i=0;(i+3)<username.length();i++){
               if(pass.contains(username.substring(i,i+3)) || username.length()<3 || username.length()>15){
                   return false;
               }
           }
           for(int i=0;(i+3)<email.length();i++){
               if(pass.contains(email.substring(i,i+3)) || email.length()<3 || email.length()>15){
                   return false;
               }
           }
           return true;
       }
       return false;
   }
    // if you want to know which requirement was not met
    public boolean validiate2 (String pass){

        if (pass.length() < 8 || pass.length() >15 ){
            Toast.makeText(getActivity(),"pass too short or too long",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*\\d.*")){
            Toast.makeText(getActivity(),"no digits found",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*[a-z].*")) {
            Toast.makeText(getActivity(),"no lowercase letters found",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*[A-Z].*")) {
            Toast.makeText(getActivity(),"no uppercase letters found",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.matches(".*[!@#$%^&*+=?-].*")) {
            Toast.makeText(getActivity(),"no special chars found",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static boolean containsPartOf(String pass, String username) {
        int requiredMin = 3;
        for(int i=0;(i+requiredMin)<username.length();i++){
            if(pass.contains(username.substring(i,i+requiredMin))){
                return true;
            }
        }
        return false;
    }

    //--------------- password validation ends
}
