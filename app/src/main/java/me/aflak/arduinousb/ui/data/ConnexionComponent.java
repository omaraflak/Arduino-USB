package me.aflak.arduinousb.ui.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.arduinousb.data.AppModule;
import me.aflak.arduinousb.ui.view.ChatFragment;
import me.aflak.arduinousb.ui.view.ConnexionActivity;

/**
 * Created by Omar on 28/05/2017.
 */
@Singleton
@Component(modules = {AppModule.class, ConnexionModule.class})
public interface ConnexionComponent {
    void inject(ConnexionActivity activity);
    void inject(ChatFragment fragment);
}
