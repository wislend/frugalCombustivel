package com.example.deyvi.frugalcombustive.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deyvi.frugalcombustive.ui.activitys.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by WislenDouglasdeSouza on 13/04/2018.
 */

@EFragment
public class BaseFragments  extends Fragment{

    protected String TAG = this.getClass().getName();
    protected View mContentView;
    protected int mShortAnimationDuration;
    protected View mContentOverlay;
//    private BroadcastsManagerReceiver mBroadcastsManagerReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

    }

    public <Act extends BaseActivity> Act getBaseActivity() {
        return (Act) getActivity();
    }

//    @CallSuper
//    @AfterViews
//    protected final void onAfterViewsInternal() {
//
//        if (isStateValid()) {
//            String[] actions = onListenToActions();
//            if (actions != null && actions.length > 0) {
//                mBroadcastsManagerReceiver = BroadcastManager.createReceiver()
//                        .addActions(actions)
//                        .tag(onTagBroadcast())
//                        .callback( new BroadcastsManagerCallback() {
//                            @Override
//                            public void onReceive(Context context, BroadcastsManagerEvent data) {
//                                onActionDataReceived(context, data);
//                            }
//                        });
////                BroadcastManager.getInstance().registerReceiver(mBroadcastsManagerReceiver);
//            }
//
//            onAfterViews();
//        }
//    }

    protected void onAfterViews() {

    }

    @Override
    public void onDestroy() {
//        unregisterBroadcast();
        super.onDestroy();
    }

//    protected void unregisterBroadcast() {
//        if (mBroadcastsManagerReceiver != null && mBroadcastsManagerReceiver.isRegistered()) {
//            BroadcastManager.getInstance().unregisterReceiver(mBroadcastsManagerReceiver);
//    }
//    }

    /**
     * Ações para serem escutadas
     *
     * @return
     */
    protected String[] onListenToActions() {
        return null;
    }

    /**
     * TAG para a assinatura
     * Caso queira que seja avisado de qualquer forma sobre algum evento, basta retorna null
     *
     * @return
     */
    protected String onTagBroadcast() {
        return TAG;
    }

//    /**
//     * Evento para uma ação
//     *
//     * @param context
//     * @param data

//    protected void onActionDataReceived(Context context, BroadcastsManagerEvent data) {
//
//    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        /*
        * FUNÇÃO LOADING
        *
        * Esta função adiciona a View principal do Fragment uma View de Loading.
        * Assim, quando houver um carregamento de dados que não é instantâneo,
        * o utilizador pode chamar a função setLoading passando true e a View
        * de loading será exibida. Passando false, ela será escondida.
        *
        * REGRAS:
        *
        * 1) O pai da View principal do Fragment deve ser um ViewGroup (Linear, Relative, TableLayout, etc).
        *    Se o pai do layout não for um ViewGroup, não irá funcionar
        *    Se for um TextView na raiz, por exemplo, não irá funcionar.
        *
        * 2) O pai deve ter ID igual a "viewContent"
        *    A View que tiver esse ID na View principal do Fragment é a que será escondida em modo Loading true!
        *    Se não existir essa view no Fragment, o recurso não irá funcionar.
        *
        * 3) Caso não exista um ID no pai como "viewContent", ele poderá ser informado no filho
        *    , mas se não for informado e o dev chamar o setLoading, uma exceção em tempo de execução
        *    será propagada.
        */
//        initLoadingResource(view);

    }

//    /**
//     * Tenta iniciar o recurso de loading de acordo com a View informada; que deve ser uma instância de ViewGroup
//     *
//     * @param view
//     */
//    private void initLoadingResource(View view) {
//
//        if (view instanceof ViewGroup) {
//
//            ViewGroup viewGroup = (ViewGroup) view;
//
//            if (mContentView == null) {
//                //Primeiro tenta carregar o content, pois,
//                //se ele nao existir, o recurso nao funcionará
//                mContentView = viewGroup.findViewById(R.id.viewContent);
//            }
//
//            if (mContentView != null) {
//
//                //tenta carregar se tiver no layout já definida
//                mContentOverlay = viewGroup.findViewById(R.id.viewLoading);
//
//                if (mContentOverlay == null) {
//                    mContentOverlay = LayoutInflater.from(getActivity()).inflate(R.layout.view_loading, null);
//                    viewGroup.addView(mContentOverlay);
//                }
//
//                //Define o estado inicial (nao mostrar a view Loading)
//                mContentOverlay.setVisibility(View.GONE);
//            }
//        }
//    }

    /**
     * Esta função alterna o estado de Loading mediante ao parâmetro informado.
     * Quando TRUE, a View principal será escondida e a View de loading será exibida.
     * Quando FALSE, a View principal será exibida e a View de loading será escondida.
     *
     * @param contentLoaded
     */

    public void setLoading(boolean contentLoaded) {

        contentLoaded = !contentLoaded;
        Log.i(TAG, "setLoading = " + contentLoaded);

        // Decide which view to hide and which to show.
        final View showView = contentLoaded ? mContentView : mContentOverlay;
        final View hideView = contentLoaded ? mContentOverlay : mContentView;

        // Set the "show" view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        showView.setVisibility(View.VISIBLE);
        showView.setAlpha(0f);

        // Animate the "show" view to 100% opacity, and clear any animation listener set on
        // the view. Remember that listeners are not limited to the specific animation
        // describes in the chained method calls. Listeners are set on the
        // ViewPropertyAnimator object for the view, which persists across several
        // animations.
        showView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showView.setVisibility(View.VISIBLE);
                        showView.setAlpha(1);
                    }
                });

        // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
        // to GONE as an optimization step (it won't participate in layout passes, etc.)
        hideView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
    }


    /**
     * Diz se estamos no modo de loading
     *
     * @return
     */
    public boolean isLoading() {

        return mContentOverlay != null && mContentOverlay.getVisibility() == View.VISIBLE;
    }

    public View getViewLoading() {
        return mContentOverlay;
    }

    public View getViewContent() {
        return mContentView;
    }

    public void setViewContent(View viewContent) {
        this.mContentView = viewContent;

//        initLoadingResource(getView());
    }

    protected void removeFragmentByTag(String tag) {
        try {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            Fragment prev = getChildFragmentManager().findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev).commit();
            }
            ft.addToBackStack(null);
        } catch (Exception e) {

        }
    }

    /**
     * Apenas insere o elevation na ActionBar, caso haja e caso a versão seja igual ou superior a Lollipop
     */
    public void elevationActionBar(float elevation) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getSupportActionBar() != null) {
            getSupportActionBar().setElevation(elevation);
        }
    }

    /**
     * Apenas insere o elevation na ActionBar, caso haja e caso a versão seja igual ou superior a Lollipop
     */
    public void elevationActionBar() {
        elevationActionBar(8);
    }

    /**
     * Insere elevação padrão em uma View
     *
     * @param view
     */
    public void elevationView(@NonNull View view) {

        elevationView(view, 8);
    }

    /**
     * Insere elevação padrão em uma View
     *
     * @param view
     */
    public void elevationView(@NonNull View view, float value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(value);

        }
    }

    /**
     * Obtem a Actionbar a partir do Fragment
     * Facilita a chamada direta de getSupportActionBar sem ter que chamar a Activity primeiro
     *
     * @return
     */
    public android.support.v7.app.ActionBar getSupportActionBar() {
        if (!isStateSaved()) {
            return null;
        }
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

//    protected void toogleViewVisible(View view, boolean visible) {
//        if (visible) {
//            YoYo.with(Techniques.FadeInUp).playOn(view);
//            view.setVisibility(View.VISIBLE);
//        } else {
//            YoYo.with(Techniques.FadeOutDown).playOn(view);
//            view.setVisibility(View.GONE);
//        }
//    }

//    public <D extends DialogFragmentBase> D findDialogFragment(String tag, ResultReceiverIon resultReceiverIon) {
//        D dialogFragmentBase = (D) getChildFragmentManager().findFragmentByTag(tag);
//        if (dialogFragmentBase != null) {
//            dialogFragmentBase.setResultReceiver(resultReceiverIon);
//        }
//        return dialogFragmentBase;
//    }
//
//    public boolean isStateValid() {
//        return (isAdded() && getActivity() != null && !getActivity().isFinishing() &&
//                (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 ||
//                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
//                                && !getActivity().isDestroyed())));
//    }

    /**
     * Caso o evento seja consumido, true é retornado, caso contrário, false é retornado informando que o
     * evento não foi consumido
     *
     * @return
     */
    public boolean onBackPressed() {

        return false;
    }

    public boolean finish() {

        return false;
    }
}
