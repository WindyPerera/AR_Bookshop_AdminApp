package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lk.bookshoppadminapp.Model.Admin;
import com.lk.bookshoppadminapp.R;

import java.util.Arrays;
import java.util.List;

public class AdminLoging extends AppCompatActivity {

    private int RC_SIGN_IN = 123;
    SignInButton login_btn;
    String FCMToken = null;
    TextView new_account;
    String admin_Id, user_email;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference adminCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loging);

        adminCollectionReference = db.collection("Admin");
        firebaseAuth = FirebaseAuth.getInstance();
        initFCM();

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSignInIntent();
            }
        });
    }


    private void initFCM() {
// call firebase messaging and get the token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        FCMToken = task.getResult().toString();
                        //Toast.makeText(LoginActivity.this, FCMToken, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication
        // Intent from Google Firebse
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                String auth = user.getUid();
                String name = user.getDisplayName();
                Log.d("MAIN-----------------", name);
                Log.d("MAIN-----------------", email);

                db.collection("Admin").whereEqualTo("adminEmail", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Admin> adminList = queryDocumentSnapshots.toObjects(Admin.class);

                        Log.d("awaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", email);
//                        if (adminList.size()>0){
//                            Log.d("awaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", email[0]);
//
//                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
//                            admin_Id = doc.getId();
//                            Admin admin = doc.toObject(Admin.class);
//                            user_email = admin.getAdminEmail();
//
//                            updateFCMToken(admin_Id);
//                            Intent intent = new Intent(AdminLoging.this,AdminHome.class);
//                            intent.putExtra("adminName",name);
//                            intent.putExtra("adminID",admin_Id);
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            Intent regiintent = new Intent(AdminLoging.this,AdminRegister.class);
//                            startActivity(regiintent);
//                            finish();
//                        }

                        if (adminList.size() > 0) {
                            Log.d("awaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", email);
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                            admin_Id = doc.getId();
                            Admin admin = doc.toObject(Admin.class);
                            user_email = admin.getAdminEmail();

                            updateFCMToken(admin_Id);
                            Intent homeIntent = new Intent(AdminLoging.this, AdminHome.class);
                            Toast.makeText(AdminLoging.this, "Sing in ok", Toast.LENGTH_LONG).show();
//                            homeIntent.putExtra("auth_name", customer.getCus_name() + "");
//                            homeIntent.putExtra("auth_email", customer.getCus_email() + "");
                            homeIntent.putExtra("userDocId", admin_Id);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(homeIntent);
                        } else {
                            Intent regiIntent = new Intent(AdminLoging.this, AdminHome.class);
                            regiIntent.putExtra("adminName",name);
                            regiIntent.putExtra("adminID",admin_Id);
                            startActivity(regiIntent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("adddddddd", e.getMessage());
                    }
                });
            }
        }
    }

    private void updateFCMToken(String customer_id) {
        Log.d("Token----------", "Customer Id : " + customer_id);
        db.collection("Customer").document(customer_id).update("fcmId", FCMToken).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("token----------------------------------", "FCM Token Updated");
                    }
                });
    }
}