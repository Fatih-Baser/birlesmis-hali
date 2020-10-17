package com.example.birlesmishali;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        DiscreteScrollView.ScrollStateChangeListener<RecyclerViewAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<RecyclerViewAdapter.ViewHolder>,
        View.OnClickListener  {

    //private ArrayList<Bitmap> arrayimage = new ArrayList<Bitmap>();
    private List<Slider> sliders;
    ArrayList<Bitmap> hebele = new ArrayList<>();
    ImageView image;
    Bitmap bitmap;

    Button start;
    ProgressBar progressBar;
    private int counter=0;
    Timer t=new Timer();
    TimerTask tt;
    RelativeLayout progress;

    CardView cardwiew;
    private DiscreteScrollView cityPicker;


    Adapter adapter;


    private TextView textViewResult;
    private static String token;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ikc3SEFpVGRrb3VzWnF3cDlOYlRkaitETllXWE5aSVFhaVJ1QzI1aVFoaDA9IiwibmJmIjoxNjAyMDk1MTUwLCJleHAiOjE2MDIxODE1NTAsImlhdCI6MTYwMjA5NTE1MH0.exQTR7GK6KgUaCk7YFJJEcFHqmKyo5sa3Zrb16gmcXU";
        image=findViewById(R.id.image);

        cityPicker = findViewById(R.id.recyclerView);

        sliders = SliderStation.get().getForecasts();

        cityPicker.setAdapter(new RecyclerViewAdapter(this,sliders));
       sliders = SliderStation.get().getForecasts();

        cityPicker.setSlideOnFling(true);


//        System.out.println(sliders.size());
//        int b = 0;
//        for (Bitmap a :sliders){
//            b++;
//        }
//        System.out.println(b);


       // textViewResult.append(content);


        cityPicker.addOnItemChangedListener(this);
        cityPicker.addScrollStateChangeListener(this);
        cityPicker.scrollToPosition(0);
        // cityPicker.setItemTransitionTimeMillis(SliderScrollViewOptions.getTransitionTime));
        cityPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.9f)
                .build());

        progressBarkismi();


        textViewResult = findViewById(R.id.text_view_result);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ikc3SEFpVGRrb3VzWnF3cDlOYlRkaitETllXWE5aSVFhaVJ1QzI1aVFoaDA9IiwibmJmIjoxNjAyMDk1MTUwLCJleHAiOjE2MDIxODE1NTAsImlhdCI6MTYwMjA5NTE1MH0.exQTR7GK6KgUaCk7YFJJEcFHqmKyo5sa3Zrb16gmcXU")
                        .build();
                System.out.println(newRequest + "OkHttpClient out data");
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://moodytest-env.eba-mmzgp9iv.eu-central-1.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<TopLevel> call = jsonPlaceHolderApi.getTopLevel();

        call.enqueue(new Callback<TopLevel>() {
            @Override
            public void onResponse(Call<TopLevel> call, Response<TopLevel> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code:" + response.code());
                    //getSecret();

                    return;
                }

                TopLevel topLevel = response.body();
                List<Category> categories = topLevel.getCategories();

                String content = "" + "\n";
                content += "versionNumber: " + topLevel.getVersionNumber() + "\n";
                content += "isUpdated: " + topLevel.getUpdated() + "\n";





//                byte[] decodedString = Base64.decode(categories.get(4).getImage(), Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//                image.setImageBitmap(decodedByte);
                for (Category category:categories) {
                    byte[] decodedString = Base64.decode(category.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    sliders.add(new Slider("3", decodedByte));
                    System.out.println(sliders.size());
                }


//                    image.setImageBitmap(decodedByte);
//                    content += "categoryId: " + category.getId() + "\n";
//                    content += "categoryName: " + category.getName() + "\n";
//                    content += "categoryOrder: " + category.getOrder() + "\n";
//                    content += "categoryImage: " + category.getImage() + "\n";
//                   List<CategoryDetails> categoryDetailsList = category.getCategoryDetails();
//                    for (CategoryDetails categoryDetails:categoryDetailsList)
//                    {
//                        content += "categoryDetailsId: " + categoryDetails.getId() + "\n";
//                        content += "categoryDetailsOrder: " + categoryDetails.getOrder() + "\n";
//                        content += "categoryDetailsImage: " + categoryDetails.getImage() + "\n";
//                    }
       //         }
//                initRecyclerView();
//                int x = 0;
//                for (Bitmap a :hebele){
//                    x++;
//                }
//                System.out.println(x);


                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<TopLevel> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                System.out.println(t.getMessage());
            }
        });

    }


    /*
    private void getSecret(){
        Call<ResponseBody> call = jsonPlaceHolderApi.getSecret(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Login is not correct", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

     */

//    private void initRecyclerView(){
//
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, sliders );
//        recyclerView.setAdapter(adapter);
//    }
    @SuppressLint("ClickableViewAccessibility")
    public void progressBarkismi(){
        start=findViewById(R.id.button);
        progressBar=findViewById(R.id.progress_bar);

        progress=findViewById(R.id.progressLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager(). getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        System.out.println("WIDTH == " + width);
        progress.setHorizontalGravity(width/2);
        progress.setVerticalGravity(height/2);

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, final MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        t=new Timer();
                        tt=new TimerTask() {
                            @Override
                            public void run() {
                                counter++;
                                progressBar.setProgress(counter);
                                if(counter ==100)
                                    t.cancel();

                            }

                        };
                        t.schedule(tt,0,100);
                        progressBar.setVisibility(View.VISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:

                        t.cancel();
                        progressBar.setProgress(counter=0);

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerViewAdapter.ViewHolder viewHolder, int adapterPosition) {

        if (viewHolder != null) {

            viewHolder.ortadaBuyume();
        }
    }


    @Override
    public void onScrollStart(@NonNull RecyclerViewAdapter.ViewHolder currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(@NonNull RecyclerViewAdapter.ViewHolder currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerViewAdapter.ViewHolder currentHolder, @Nullable RecyclerViewAdapter.ViewHolder newCurrent) {

    }
}