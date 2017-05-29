package me.aflak.arduinousbchat.ui.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aflak.arduinousbchat.R;


/**
 * Created by Omar on 28/05/2017.
 */

public class ChatFragment extends Fragment {
    @BindView(R.id.fragment_chat_message) EditText editText;
    @BindView(R.id.fragment_chat_textView) TextView chat;

    private OnChatListener listener;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        chat.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @OnClick(R.id.fragment_chat_send)
    public void onSend(){
        String message = editText.getText().toString();
        emptyEditText();
        appendMessage(message, ContextCompat.getColor(getActivity(), R.color.fragment_chat_android));

        if(listener!=null){
            listener.onSendMessage(message);
        }
    }

    public void appendMessage(final String message, final int color){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int start = chat.getText().length();
                chat.append(message+"\n");
                int end = chat.getText().length();

                Spannable spannableText = (Spannable) chat.getText();
                spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
            }
        });
    }

    public void emptyEditText(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText("");
            }
        });
    }

    public void setListener(OnChatListener listener) {
        this.listener = listener;
    }

    public interface OnChatListener{
        void onSendMessage(String message);
    }
}
