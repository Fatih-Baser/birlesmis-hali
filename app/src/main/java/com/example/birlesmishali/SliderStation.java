package com.example.birlesmishali;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SliderStation {

    List<Slider> sliderList = new ArrayList<>();
    private TextView textViewResult;
    private static String token;
    JsonPlaceHolderApi jsonPlaceHolderApi;


    public static SliderStation get() {
        return new SliderStation();
    }

    private SliderStation() {
    }

    public List<Slider> getForecasts() {


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
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
                if (!response.isSuccessful()) {
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
                for (Category category : categories) {
                    byte[] decodedString = Base64.decode(category.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    sliderList.add(new Slider("3", decodedByte));

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


            }

            @Override
            public void onFailure(Call<TopLevel> call, Throwable t) {

                System.out.println(t.getMessage());
            }
        });

        return sliderList;

    }

}

        // json cekme ve bitmape donusturme
        // decode bilmem ne
        // return Arrays.asList(
        //  for dongusu icinde olsturdugun listenin icine atacaksin new Slider ( "cityname", decode);
        // return slider listesi



//                Arrays.asList(
//                new Slider("Pisa", R.drawable.kucuk1),
//                new Slider("Paris", R.drawable.daire1),
//                new Slider("New York", R.drawable.seffaf),
//                new Slider("Rome", R.drawable.yuvarlak),
//                new Slider("London", R.drawable.yuvarlak),
//                new Slider("Washinn", R.drawable.yuvarlak));

