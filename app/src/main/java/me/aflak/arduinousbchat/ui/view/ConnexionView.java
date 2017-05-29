package me.aflak.arduinousbchat.ui.view;

/**
 * Created by Omar on 28/05/2017.
 */

public interface ConnexionView {
    void setConnectButtonVisibility(int visibility);
    void showChatFragment();
    void hideChatFragment();
    void setStatusText(int id);
    void setUsbColor(int id);
    void showMessage(String message);
}
