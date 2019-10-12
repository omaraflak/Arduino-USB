package me.aflak.arduinousb.ui.view;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aflak.arduinousb.R;
import me.aflak.arduinousb.data.AppModule;
import me.aflak.arduinousb.ui.data.ConnexionModule;
import me.aflak.arduinousb.ui.data.DaggerConnexionComponent;
import me.aflak.arduinousb.ui.presenter.ConnexionPresenter;

public class ConnexionActivity extends AppCompatActivity implements ConnexionView, ChatFragment.OnChatListener {
    @BindView(R.id.activity_connexion_connect) Button connectButton;
    @BindView(R.id.activity_connexion_status) TextView statusText;
    @BindView(R.id.activity_connexion_usb) ImageView usbImage;

    @Inject ConnexionPresenter presenter;
    @Inject FragmentManager fragmentManager;
    @Inject ChatFragment chatFragment;
    @Inject AlertDialog.Builder aboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        ButterKnife.bind(this);
        DaggerConnexionComponent.builder()
                .appModule(new AppModule(this))
                .connexionModule(new ConnexionModule(this))
                .build().inject(this);

        fragmentManager.beginTransaction().add(R.id.activity_connexion_fragment, chatFragment).hide(chatFragment).commit();
        chatFragment.setListener(this);
    }

    @Override
    public void setConnectButtonVisibility(int visibility) {
        connectButton.setVisibility(visibility);
    }

    @Override
    public void showChatFragment() {
        fragmentManager.beginTransaction()
                .show(chatFragment)
                .commit();
    }

    @Override
    public void hideChatFragment() {
        fragmentManager.beginTransaction()
                .hide(chatFragment)
                .commit();
    }

    @Override
    public void setStatusText(int id) {
        statusText.setText(id);
    }

    @Override
    public void setUsbColor(int id) {
        usbImage.setColorFilter(ContextCompat.getColor(this, id));
    }

    @Override
    public void showMessage(String message) {
        chatFragment.appendMessage(message, ContextCompat.getColor(this, R.color.fragment_chat_arduino));
    }

    @OnClick(R.id.activity_connexion_connect)
    public void onConnect(){
        presenter.connect();
    }

    @Override
    public void onSendMessage(String message) {
        presenter.send(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.connexion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connexion_about:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout(){
        aboutDialog.setTitle(R.string.activity_connexion_about_title);
        aboutDialog.setMessage(R.string.activity_connexion_about_message);
        aboutDialog.setPositiveButton(R.string.activity_connexion_about_go, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.activity_connexion_about_link).toString())));
            }
        });
        aboutDialog.setNegativeButton(R.string.activity_connexion_about_stay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aboutDialog.show();
    }
}
