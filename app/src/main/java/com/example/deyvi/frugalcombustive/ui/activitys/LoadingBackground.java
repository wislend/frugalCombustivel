package com.example.deyvi.frugalcombustive.ui.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.deyvi.frugalcombustive.R;
import com.example.deyvi.frugalcombustive.entity.Veiculo;
import com.example.deyvi.frugalcombustive.ui.views.VeiculoViewHolder;
import com.example.deyvi.frugalcombustive.ui.views.VeiculoViewHolder_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_loading_background)
public class LoadingBackground extends BaseActivity {

    @ViewById
    protected ListView listView;

    @InstanceState
    protected ArrayList<Veiculo> values = new ArrayList<>();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        carregaDados();
    }

    @Background
    public void carregaDados() {


        for (int i = 0; i < 50; i++) {
            values.add(new Veiculo("Marca " + i));
        }

        setupUI(values);

    }

    @UiThread
    void setupUI(List<Veiculo> veiculos) {
        VeiculoApdater veiculoApdater = new VeiculoApdater(this, veiculos);
        listView.setAdapter(veiculoApdater);
    }


    public static class VeiculoApdater extends ArrayAdapter<Veiculo> {

        public VeiculoApdater(@NonNull Context context, @NonNull List<Veiculo> objects) {
            super(context, -1, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            VeiculoViewHolder veiculoViewHolder;

            if (convertView == null) {
                veiculoViewHolder = VeiculoViewHolder_.build(getContext());
            } else {
                veiculoViewHolder = (VeiculoViewHolder) convertView;
            }

            veiculoViewHolder.setVeiculo(getItem(position));
            return veiculoViewHolder;
        }
    }

}





