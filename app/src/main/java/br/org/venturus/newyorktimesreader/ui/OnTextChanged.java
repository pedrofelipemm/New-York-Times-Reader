package br.org.venturus.newyorktimesreader.ui;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class OnTextChanged implements TextWatcher {

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public abstract void onTextChanged(CharSequence s, int start, int before, int count);

    @Override
    public final void afterTextChanged(Editable s) {}
}
