package com.upnyk.stefanus.kriptografi;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean encrypted = false;
    private String plaintext, chipertext;
    private final String keyToCrypt = "KRIPTOGRAFIITUASIKSEKALILHO";
    private final String abjad = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int countSpace = 0;

    private EditText etInput;
    private ImageButton ibChange;
    private ImageView ivCopy, ivReset;
    private TextView tvTitle11, tvTitle12, tvTitle21, tvTitle22, tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = (EditText) findViewById(R.id.etInput);
        ibChange = (ImageButton) findViewById(R.id.ibChange);
        ivCopy = (ImageView) findViewById(R.id.ivCopy);
        ivReset = (ImageView) findViewById(R.id.ivReset);
        tvTitle11 = (TextView) findViewById(R.id.title11);
        tvTitle12 = (TextView) findViewById(R.id.title12);
        tvTitle21 = (TextView) findViewById(R.id.title21);
        tvTitle22 = (TextView) findViewById(R.id.title22);
        tvResult = (TextView) findViewById(R.id.tvResult);

        plaintext = getString(R.string.plaintext);
        chipertext = getString(R.string.chipertext);

        ibChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapTitle();
                bimsalabim(etInput.getText().toString());
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = getString(R.string.prompt_1);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(message, tvResult.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        ivReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etInput.setText("");
            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                bimsalabim(editable.toString());
            }
        });
    }

    private void bimsalabim(String text) {
        if (encrypted) {
            tvResult.setText(decryptText(text));
        } else {
            tvResult.setText(encryptText(text));
        }
    }

    private void swapTitle() {
        if (encrypted) {
            tvTitle11.setText(plaintext);
            tvTitle12.setText(chipertext);
            tvTitle21.setText(plaintext);
            tvTitle22.setText(chipertext);
            encrypted = false;
        } else {
            tvTitle11.setText(chipertext);
            tvTitle12.setText(plaintext);
            tvTitle21.setText(chipertext);
            tvTitle22.setText(plaintext);
            encrypted = true;
        }
    }

    private String encryptText(String plaintext) {
        String chipertext = ""; countSpace = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char p = plaintext.charAt(i);
            if (p == ' ') {chipertext += ' '; countSpace++;}
            else {
                char k = keyToCrypt.charAt((i - countSpace) % keyToCrypt.length());
                chipertext += abjad.charAt(((int) p + (int) k) % abjad.length());
            }
        }
        return chipertext;
    }

    private String decryptText(String chipertext) {
        String plaintext = ""; countSpace = 0;
        for (int i = 0; i < chipertext.length(); i++) {
            char c = chipertext.charAt(i);
            if (c == ' ') {plaintext += ' '; countSpace++;}
            else {
                char k = keyToCrypt.charAt((i - countSpace) % keyToCrypt.length());
                plaintext += abjad.charAt((((int) c - (int) k) + 26) % abjad.length());
            }
        }
        return plaintext;
    }
}
