package me.aflak.arduinousbchat.ui.presenter;

import android.hardware.usb.UsbDevice;
import android.view.View;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
import me.aflak.arduinousbchat.R;
import me.aflak.arduinousbchat.ui.view.ConnexionView;

/**
 * Created by Omar on 28/05/2017.
 */

public class ConnexionPresenterImpl implements ConnexionPresenter, ArduinoListener {
    private ConnexionView view;
    private Arduino arduino;
    private UsbDevice device;

    private boolean fragmentDisplayed = false;

    public ConnexionPresenterImpl(ConnexionView view, Arduino arduino) {
        this.view = view;
        this.arduino = arduino;

        arduino.setArduinoListener(this);
    }

    @Override
    public void connect() {
        arduino.open(device);
    }

    @Override
    public void send(String message) {
        arduino.send(message.getBytes());
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        this.device = device;

        view.setStatusText(R.string.activity_connexion_status_plugged);
        view.setUsbColor(R.color.activity_connexion_status_plugged);
        view.setConnectButtonVisibility(View.VISIBLE);
    }

    @Override
    public void onArduinoDetached() {
        view.setStatusText(R.string.activity_connexion_status_unplugged);
        view.setUsbColor(R.color.activity_connexion_status_unplugged);
        view.setConnectButtonVisibility(View.INVISIBLE);

        if(fragmentDisplayed) {
            view.hideChatFragment();
            fragmentDisplayed = false;
        }
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        view.showMessage(new String(bytes));
    }

    @Override
    public void onArduinoOpened() {
        view.showChatFragment();
        fragmentDisplayed = true;
    }
}
