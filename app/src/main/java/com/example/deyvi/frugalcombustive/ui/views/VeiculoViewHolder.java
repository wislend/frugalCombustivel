package com.example.deyvi.frugalcombustive.ui.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deyvi.frugalcombustive.R;
import com.example.deyvi.frugalcombustive.entity.Veiculo;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by WislenDouglasdeSouza on 13/04/2018.
 */
@EViewGroup(R.layout.view_holder)
public class VeiculoViewHolder extends FrameLayout {

    @ViewById
    ProgressBar progress;
    @ViewById
    TextView text1;

    public VeiculoViewHolder(@NonNull Context context) {
        super(context);
    }

    public VeiculoViewHolder(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VeiculoViewHolder(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VeiculoViewHolder(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setVeiculo(final @NonNull Veiculo veiculo) {
        progress.setVisibility(VISIBLE);
        text1.setVisibility(GONE);
        postDelayed(new Runnable() {
            @Override
            public void run() {

                progress.setVisibility(GONE);
                text1.setVisibility(VISIBLE);
                text1.setText(veiculo.getMarca());
            }
        }, 2000);

    }
}
