package com.example.quocthao.supership.shop.services;

import com.example.quocthao.supership.objects.ObjBill;
import com.example.quocthao.supership.objects.ObjInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GameNet on 3/23/2017.
 */

public class DataService {

    private Retrofit retrofit;
    private IRestService iRestService;
    private TaskCompletionSource<HashMap<String, ObjInfo>> taskData;
    private TaskCompletionSource<HashMap<String, ObjBill>> taskBill;
    private TaskCompletionSource<String> taskNewBill;
    private FirebaseUser userShop = FirebaseAuth.getInstance().getCurrentUser();

    public DataService() {
        taskData = new TaskCompletionSource<>();
        taskBill = new TaskCompletionSource<>();
        taskNewBill = new TaskCompletionSource<>();

        retrofit = new Retrofit.Builder().baseUrl("https://super-ship.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        iRestService = retrofit.create(IRestService.class);
    }

    public Task<HashMap<String, ObjInfo>> loadData() {
        iRestService.loadData().enqueue(new Callback<HashMap<String, ObjInfo>>() {
            @Override
            public void onResponse(Call<HashMap<String, ObjInfo>> call, Response<HashMap<String, ObjInfo>> response) {
                taskData.setResult(response.body());
            }

            @Override
            public void onFailure(Call<HashMap<String, ObjInfo>> call, Throwable t) {
                taskData.setError((Exception) t);
            }
        });

        return taskData.getTask();
    }

    public Task<HashMap<String, ObjBill>> loadBill() {
        iRestService.loadBill(userShop.getUid()).enqueue(new Callback<HashMap<String, ObjBill>>() {
            @Override
            public void onResponse(Call<HashMap<String, ObjBill>> call, Response<HashMap<String, ObjBill>> response) {
                taskBill.setResult(response.body());
            }

            @Override
            public void onFailure(Call<HashMap<String, ObjBill>> call, Throwable t) {
                taskBill.setError((Exception) t);
            }
        });

        return taskBill.getTask();
    }

    private String[] arrBill;
    private ArrayList<String> listBills = new ArrayList<>();
    private String idShop, idCustomer, oldBill, newBill;

    public Task<String> newBill(final String email) {
        idShop = userShop.getUid();
        List<Task<Void>> tasks = new ArrayList<>();

        Task<Void> taskData = loadData().continueWith(new Continuation<HashMap<String, ObjInfo>, Void>() {
            @Override
            public Void then(Task<HashMap<String, ObjInfo>> task) throws Exception {

                for (HashMap.Entry<String, ObjInfo> info : task.getResult().entrySet()) {
                    if (info.getValue().getEmail().equals(email)) {
                        idCustomer = info.getKey();
                        newBill = idShop + "_" + idCustomer;

                        return null;
                    }
                }
                return null;
            }
        });
        tasks.add(taskData);

        Task<Void> taskBill = loadBill().continueWith(new Continuation<HashMap<String, ObjBill>, Void>() {
            @Override
            public Void then(Task<HashMap<String, ObjBill>> task) throws Exception {

                for (HashMap.Entry<String, ObjBill> bill : task.getResult().entrySet()) {
                    listBills.add(bill.getKey());
                }

                return null;
            }
        });
        tasks.add(taskBill);

        Task.whenAll(tasks).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(Task <Void> task) throws Exception {

                int max = 0, numBill;

                for (String bill : listBills) {
                    arrBill = bill.split("_");
                    oldBill = arrBill[0] + "_" + arrBill[1];

                    if (newBill.equals(oldBill)) {
                        numBill = Integer.parseInt(arrBill[2]) + 1;

                        if(max < numBill)
                            max = numBill;

                    }
                }

                taskNewBill.setResult(newBill + "_" + max);
                return null;
            }
        });


        return taskNewBill.getTask();
    }


}
