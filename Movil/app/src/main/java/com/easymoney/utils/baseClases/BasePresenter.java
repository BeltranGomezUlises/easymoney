package com.easymoney.utils.baseClases;

import com.easymoney.models.services.Response;

public abstract class BasePresenter<T extends BaseFragment> {

    private T fragment;

    public abstract void subscribe();
    public abstract void unsubscribe();

    public T getFragment() {
        return fragment;
    }

    public void setFragment(T baseFragment) {
        this.fragment = baseFragment;
    }

    public void evalResponse(Response res, Runnable onOk) {
        String msg = res.getMeta().getMessage();
        switch (res.getMeta().getStatus()) {
            case OK:
                if (onOk != null) {
                    onOk.run();
                }
                break;
            case WARNING:
                if (msg != null && !msg.isEmpty()) {
                    getFragment().showWARNING(msg);
                    getFragment().hideKeyBoard();
                }
                break;
            case ERROR:
                if (msg != null && !msg.isEmpty()) {
                    getFragment().showERROR(msg);
                    getFragment().hideKeyBoard();
                }
                break;
        }
    }

    public void evalResponse(Response res, Runnable onOk, Runnable onWarning) {
        String msg = res.getMeta().getMessage();
        switch (res.getMeta().getStatus()) {
            case OK:
                if (onOk != null) {
                    onOk.run();
                }
                break;
            case WARNING:
                if (onWarning != null) {
                    onWarning.run();
                }
                break;
            case ERROR:
                if (msg != null && !msg.isEmpty()) {
                    getFragment().showERROR(msg);
                    getFragment().hideKeyBoard();
                }
                break;
        }
    }

    public void evalResponse(Response res, Runnable onOk, Runnable onWarning, Runnable onError) {
        switch (res.getMeta().getStatus()) {
            case OK:
                if (onOk != null) {
                    onOk.run();
                }
                break;
            case WARNING:
                if (onWarning != null) {
                    onWarning.run();
                }
                break;
            case ERROR:
                if (onError != null) {
                    onError.run();
                }
                break;
        }
    }

}
