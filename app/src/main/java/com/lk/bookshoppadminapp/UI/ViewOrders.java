package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.lk.bookshoppadminapp.Fragment.OrderViewFragment;
import com.lk.bookshoppadminapp.Holders.ProductHolder;
import com.lk.bookshoppadminapp.Model.Customer;
import com.lk.bookshoppadminapp.Model.FCmClient;
import com.lk.bookshoppadminapp.Model.Invoice;
import com.lk.bookshoppadminapp.Model.InvoiceItem;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class ViewOrders extends OptionMenuActivity {

    Toolbar toolbar;
    String invoiceID, productID, proName;
    int qty;
    double subTotal;
    TextView no, proNametext, qtytext, subtol, dateText, timeText, total, orderId;
    DataTable dataTable;
    DataTableHeader header;
    private ArrayList<DataTableRow> rows = new ArrayList<>();
    Button placedBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference customercollection;
    private FCmClient myfcmClient;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Order");
        customercollection = db.collection("Customer");

        Bundle bundle = getIntent().getExtras();
        invoiceID = bundle.getString("invoiceID");

        dataTable = findViewById(R.id.data_table);
        dateText = findViewById(R.id.order_date);
        timeText = findViewById(R.id.order_time);
        total = findViewById(R.id.total_amount);
        orderId = findViewById(R.id.orderID);
        placedBtn = findViewById(R.id.placed_btn);


        db.collection("Invoice").document(invoiceID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Invoice invoice = documentSnapshot.toObject(Invoice.class);
                Log.d("iiiiiiiiiiiii",invoice.getCustomerId());

                dateText.setText(invoice.getCurrentDate());
                timeText.setText(invoice.getCurrentTime());
                total.setText(String.valueOf(invoice.getTotalAmount()) + "0");
                orderId.setText(invoice.getCustomerId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        db.collection("InvoiceItem").whereEqualTo("invoiceId", invoiceID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<InvoiceItem> itemList = queryDocumentSnapshots.toObjects(InvoiceItem.class);
                Log.d("iiiiiiiiiiiii",invoiceID);
                header = new DataTableHeader.Builder()
                        .item("No", 5)
                        .item("Product Name", 6)
                        .item("Qty", 5)
                        .item("subTotal", 5)
//                        .item("Amount", 5)
                        .build();

                if (itemList.size() > 0) {
                    for (int i = 0; i < itemList.size(); i++) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(i);
                        InvoiceItem inItem = doc.toObject(InvoiceItem.class);
                        productID = inItem.getProductId();
                        qty = inItem.getQty();
                        subTotal = inItem.getSubAmount();

                        int finalI = i;
                        db.collection("Product").document(productID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                 product = documentSnapshot.toObject(Product.class);
                                proName = product.getProduct_name();
                                Log.d("OOOOOO", proName);
                                DataTableRow row = new DataTableRow.Builder()
                                        .value(String.valueOf(finalI +1))
                                        .value(product.getProduct_name())
                                        .value(String.valueOf(inItem.getQty()))
                                        .value("Rs."+String.valueOf(inItem.getSubAmount())+"0")
//                                .value("Rs:" + String.valueOf(item.getQty() * product.getPrice()))
                                        .build();
                                rows.add(row);
                                dataTable.setHeader(header);
                                dataTable.setRows(rows);
                                dataTable.inflate(ViewOrders.this);

                            }
                        });

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("eeeeeeeeeerrrrrr",e.getMessage());
            }
        });

        placedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDetails();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myfcmClient = new FCmClient();
    }

    public void orderDetails() {

        db.collection("Invoice").document(invoiceID).update("orderedStatus", "accept order").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection("Invoice").document(invoiceID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Invoice in = documentSnapshot.toObject(Invoice.class);
                        customercollection.document(in.getCustomerId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Customer customer = documentSnapshot.toObject(Customer.class);
                                String fcmId = documentSnapshot.getString("fcmId");

                                Log.d("fcmIdfcmId......", fcmId);
                                myfcmClient.execute(fcmId, customer.getCus_email(), "Your Order have been accepted");
                                //new LoginAsyncTask(v).execute(customer.getEmail(),id);

                                Toast.makeText(ViewOrders.this, "Order Is Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ViewOrders.this, OrdersActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                });
            }


        });
    }
}
