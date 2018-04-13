package com.example.deyvi.frugalcombustive.ui.adapters.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

    protected List<T> items;
    Context mContext;
    private static LayoutInflater inflater = null;
    private Resources resources;
    private ListView mListView;
    private OnItemClickListener<T> onItemClickListener;
    private boolean menuOptionsEnabled = true;

    public BaseAdapter(@NonNull Context context, @LayoutRes int sourceRow, @NonNull List<T> items) {

        super(context, sourceRow, items);
        this.items = items;
        this.mContext = context;
        this.resources = this.mContext.getResources();
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public BaseAdapter(@NonNull Activity baseActivityRoot, @LayoutRes int sourceRow, @NonNull List<T> items, @NonNull ListView listView) {

        this(baseActivityRoot, sourceRow, items);
        mListView = listView;
    }

    public void addAll(List<T> list) {
        items.addAll(list);
        this.notifyDataSetChanged();
    }


    public List<T> getItems() {
        return items;
    }

    public void clearAddAll(List<T> items) {

        if (this.items == null)
            throw new IllegalArgumentException("A lista de itens deve ser diferente de nulo. Informe ao menos uma lista vazia.");

        this.items.clear();
        addAll(items);
    }


    @Override
    public int getCount() {
        return this.items.size();
    }

    public Context getContext() {
        return mContext;
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public Resources getResources() {
        return resources;
    }

    @NonNull
    @Override
    public final View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = getView(position, convertView, parent, getItem(position));

        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(view, getItem(position), position);
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, getItem(position), position);
                    }
                }
        });

        return view;
    }

    public boolean isMenuOptionsEnabled() {
        return menuOptionsEnabled;
    }

    public void setMenuOptionsEnabled(boolean menuOptionsEnabled) {
        this.menuOptionsEnabled = menuOptionsEnabled;
    }

    public void onItemClick(View view, T item, int position){

    }

    @NonNull
    public abstract View getView(int position, View convertView, ViewGroup parent, T item);

    public ListView getListView() {
        return mListView;
    }

    public OnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Remove um range da lista
     *
     * @param start
     * @param end
     */
    public void removeRange(int start, int end, boolean notifyDtaChanged) {

        getItems().subList(start, end).clear();
        if (notifyDtaChanged) {
            notifyDataSetInvalidated();
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClick(View view, T item, int position);
    }
}
