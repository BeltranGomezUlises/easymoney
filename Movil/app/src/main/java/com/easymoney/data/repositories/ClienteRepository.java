package com.easymoney.data.repositories;

import com.easymoney.entities.Cliente;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.services.UtilsWS;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.utils.schedulers.SchedulerProvider.ioT;
import static com.easymoney.utils.schedulers.SchedulerProvider.uiT;

public class ClienteRepository {

    private static ClienteRepository INSTANCE;

    private ClienteRepository(){

    }

    public static ClienteRepository getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ClienteRepository();
        }
        return INSTANCE;
    }


    public Disposable findAll(Consumer<Response<List<Cliente>, Object>> onNext, Consumer<Throwable> onError) {
        return UtilsWS.webServices().cargarClientes(UtilsPreferences.loadToken())
                .observeOn(uiT())
                .subscribeOn(ioT())
                .subscribe(onNext, onError);
    }
}
