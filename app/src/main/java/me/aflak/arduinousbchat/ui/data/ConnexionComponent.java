package me.aflak.arduinousbchat.ui.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.arduinousbchat.data.AppModule;
import me.aflak.arduinousbchat.ui.view.ChatFragment;
import me.aflak.arduinousbchat.ui.view.ConnexionActivity;

/**
 * Created by Omar on 28/05/2017.
 */
@Singleton
@Component(modules = {AppModule.class, ConnexionModule.class})
public interface ConnexionComponent {
    void inject(ConnexionActivity activity);
    void inject(ChatFragment fragment);
}
