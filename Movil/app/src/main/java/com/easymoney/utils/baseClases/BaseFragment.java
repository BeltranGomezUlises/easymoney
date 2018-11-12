package com.easymoney.utils.baseClases;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.easymoney.R;
import com.easymoney.models.ModelTotalAPagar;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private static final short PREVENT_DOUBLE_CLICK_TIME_MILLIS = 800;
    private ProgressDialog dialog;
    private T presenter;
    private long lastClickTime = SystemClock.elapsedRealtime();

    /**
     * Displays a snack bar with the specified message
     *
     * @param message string message to display
     */
    public void showMessage(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
        hideKeyBoard();
    }

    /**
     * Displays a snack bar with the specified message color green
     *
     * @param message string message to display
     */
    public void showOK(String message) {
        Snackbar sb = Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundResource(R.color.positive);
        sb.show();
        hideKeyBoard();
    }

    /**
     * Displays a snack bar with the specified message color green
     *
     * @param message string message to display
     */
    public void showWARNING(String message) {
        Snackbar sb = Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundResource(R.color.warning);
        sb.show();
        hideKeyBoard();
    }

    /**
     * Displays a snack bar with the specified message color green
     *
     * @param message string message to display
     */
    public void showERROR(String message) {
        Snackbar sb = Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundResource(R.color.error);
        sb.show();
        hideKeyBoard();
    }

    /**
     * Displays a snack bar with the specified message
     *
     * @param message  string message to display
     * @param duration if true, the duration is short, otherwise it will be a long duration
     */
    public void showMessage(String message, boolean duration) {
        if (duration) {
            Snackbar.make(this.getView(), message, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * Shows in screen a generic dialog with indeterminate duration
     */
    public void showLoading() {
        dialog = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", true);
    }

    /**
     * Shows in screen a dialog with indeterminate duration with the title and message provided
     */
    public void showLoading(String title, String message) {
        dialog = ProgressDialog.show(getActivity(), title, message, true);
    }

    /**
     * Closes the dialog running in this fragment
     */
    public void stopShowLoading() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * Try to close the keyboard layout
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public void preventDoubleClick(Runnable runable) {
        if (SystemClock.elapsedRealtime() - lastClickTime > PREVENT_DOUBLE_CLICK_TIME_MILLIS) {
            lastClickTime = SystemClock.elapsedRealtime();
            runable.run();
        }
    }

}
