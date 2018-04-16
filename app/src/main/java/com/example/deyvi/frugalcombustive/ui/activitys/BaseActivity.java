package com.example.deyvi.frugalcombustive.ui.activitys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deyvi.frugalcombustive.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;

import java.util.List;



/**
 * ION Sistemas de Informática
 * Projeto: IonVendas
 * <p/>
 * Created by CíceroMoura on 02/09/2014 às 11:49
 */
@SuppressLint("Registered")
@EActivity
public class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getName();
    private View viewLoading;
    private TextView textViewMessage;
    private ViewGroup viewContent;
    private ProgressDialog mProgressDialog;
    private ViewGroup mRootView;
    private OnResumeActivityListener onResumeActivityListener;

    private final int REQUEST_ANDROID_PERMISSION_CODE = Short.MAX_VALUE;
    private final int REQUEST_ANDROID_HIGH_ACURACY_GPS = Short.MAX_VALUE - 1;


    @InstanceState
    protected boolean progressShowing;
    @InstanceState
    protected boolean progrableShowing;
    @InstanceState
    protected String messageProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {

            // habilita o HOME BUTTON da actionbar para fazer o efeito de toogle
            //toogle =>  poder clicar no ícone da actionbar para  voltar ou abrir drawer por exemplo.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


    }

    @AfterViews
    @CallSuper
    protected void onAfterViews() {

    }

    public OnResumeActivityListener getOnResumeActivityListener() {
        return onResumeActivityListener;
    }

    public void setOnResumeActivityListener(OnResumeActivityListener onResumeActivityListener) {
        this.onResumeActivityListener = onResumeActivityListener;
    }

    public Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    public void replaceFragment(int idContainer, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(idContainer, fragment, tag)
                .commit();
    }

    public void replaceFragment(int idContainer, Fragment fragment, @AnimRes int animationEnter, @AnimRes int animationExit, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(idContainer, fragment, tag)
                .setCustomAnimations(animationEnter, animationExit)
                .commit();
    }

//    public <T extends DialogFragmentBase> T findDialogFragment(String tag, ResultReceiverIon resultReceiverIon) {
//        T dialogFragmentBase = (T) findFragmentByTag(tag);
//        if (dialogFragmentBase != null) {
//            dialogFragmentBase.setResultReceiver(resultReceiverIon);
//        }
//        return dialogFragmentBase;
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


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
        *
        *  NÃO USAR EM AFTER VIEWS, POIS ELE É CHAMADO ANTES DE POST CREATE
        *  QUANDO FIZER CARREGAMENTO DE ALGO MAIS PESADO
        */
        mRootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        initLoadingResource(mRootView);

        if (progrableShowing) {
            showProgressDialogProgressable(messageProgress);
        } else if (progressShowing) {
            showProgressDialog(messageProgress);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        progressShowing = isProgressDialogShowing();
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        //garante que progres dialog velho não fique ativo
        dismissProgressDialog();
        super.onDestroy();
    }

    /**
     * Tenta iniciar o recurso de loading de acordo com a View informada; que deve ser uma instância de ViewGroup
     *
     * @param viewGroup
     */
    @SuppressLint("InflateParams")
    private void initLoadingResource(@NonNull ViewGroup viewGroup) {

        if (viewContent == null) {
            //Primeiro tenta carregar o content, pois,
            //se ele nao existir, o recurso nao funcionará
            viewContent = (ViewGroup) viewGroup.findViewById(R.id.viewContent);
        }

        if (viewContent != null) {

            //talvez esse layout use um customizado, então vamos dar prioridade a ele
            viewLoading = viewGroup.findViewById(R.id.viewLoading);
            if (viewLoading == null) {
                viewLoading = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
                viewGroup.addView(viewLoading);
            }
            textViewMessage = viewLoading.findViewById(R.id.textViewMessage);
            if (textViewMessage != null) {
                textViewMessage.setText(getMessageTextLoading());
            }

            //Define o estado inicial (nao mostrar a view Loading)
            viewLoading.setVisibility(View.GONE);
        }

    }


    /**
     * Esta função alterna o estado de Loading mediante ao parâmetro informado.
     * Quando TRUE, a View principal será escondida e a View de loading será exibida.
     * Quando FALSE, a View principal será exibida e a View de loading será escondida.
     *
     * @param isLoading
     * @param modeInvisible
     */

    public void setLoading(boolean isLoading, boolean modeInvisible) {

        Log.i(TAG, "setLoading = " + isLoading);

        View viewHide = isLoading ? viewContent : viewLoading;
        View viewShow = isLoading ? viewLoading : viewContent;

        if (viewHide != null && viewShow != null) {
            viewHide.setVisibility((modeInvisible && viewHide == viewContent) ? View.INVISIBLE : View.GONE);
            viewShow.setVisibility(View.VISIBLE);
        } else {
            throw new RuntimeException("setLoading não funciona, pois viewContent ou viewLoading está nulo! Verifique se a View principal do Fragment possui ID igual a viewContent. Lembre-se também que a chamada NÃO deve ser usada dentro de @AfterViews, mas em onPostCreate.");
        }
    }

    /**
     * Se o loading em modo padrão
     *
     * @param isLoading
     */
    public void setLoading(boolean isLoading) {
        setLoading(isLoading, false);
    }


    /**
     * Diz se estamos no modo de loading
     *
     * @return
     */
    public boolean isLoading() {

        return viewLoading != null && viewLoading.getVisibility() == View.VISIBLE;
    }

    @Nullable
    public View getViewLoading() {
        return viewLoading;
    }

    @Nullable
    public View getViewContent() {
        return viewContent;
    }

    public void setViewContent(@NonNull ViewGroup viewContent) {
        this.viewContent = viewContent;
//        initLoadingResource(mRootView);
    }

    /**
     * Quando chamada, faz atualizar o texto de loading
     */
    public void notifyChangeMessageTextLoading() {
        if (textViewMessage != null) {
            textViewMessage.setText(getMessageTextLoading());
        }
    }


    /**
     * Quando chamada, faz atualizar o texto do progress dialog
     */
    public void notifyChangeMessageTextProgressDialog() {

        if (mProgressDialog != null) {
            mProgressDialog.setMessage(getString(getMessageTextProgressDialog()));
        }
    }

    /**
     * Altera a visualização dos itens do menu
     *
     * @param visibility
     */
    public void setMenuVisibility(boolean visibility) {

    }


    @StringRes
    public int getMessageTextLoading() {
        return R.string.aguarde;
    }

    @StringRes
    public int getMessageTextProgressDialog() {
        return R.string.aguarde;
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     *
     * @param message
     */
    public void showProgressDialog(String message) {

        showProgressDialog(message, false);
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     *
     * @param cancelable
     */
    public void showProgressDialog(boolean cancelable) {

        showProgressDialog(null, cancelable);
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     *
     * @param message
     */
    public void showProgressDialog(String message, boolean cancelable) {

        //não exibe nessas condições
        if (isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed())) {
            return;
        }

        if (message != null && message.length() > 0) {
            messageProgress = message;
            if (isProgressDialogShowing()) {
                mProgressDialog.setMessage(messageProgress);
                return;
            }
            mProgressDialog = ProgressDialog.show(this, "", Html.fromHtml(message), true, cancelable);
        } else {
            messageProgress = getString(getMessageTextProgressDialog());
            if (isProgressDialogShowing()) {
                mProgressDialog.setMessage(messageProgress);
                return;
            }
            mProgressDialog = ProgressDialog.show(this, "", Html.fromHtml(messageProgress), true, cancelable);
        }

        Log.d("PROGRESS_DIAOG_ACT", "showProgressDialog()");
        progressShowing = true;
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     *
     * @param message
     */
    public void showProgressDialogProgressable(String message) {

        //não exibe nessas condições
        if (isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed())) {
            return;
        }

        dismissProgressDialog();

        Spanned messageHtml;
        if (message != null && message.length() > 0) {
            messageProgress = message;
            messageHtml = Html.fromHtml(message);
        } else {
            messageProgress = getString(getMessageTextProgressDialog());
            messageHtml = Html.fromHtml(messageProgress);
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(messageHtml);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
        progrableShowing = true;
        progressShowing = true;
        Log.d("PROGRESS_DIAOG_ACT", "showProgressDialogProgressable()");
    }

    /**
     * Atauliza a mensagem do Progress
     *
     * @param message
     */
    public void setProgressDialogMessage(String message) {

        if (!isProgressDialogShowing()) {
            return;
        }

        Spanned messageHtml;
        if (message != null && message.length() > 0) {
            messageProgress = message;
            messageHtml = Html.fromHtml(message);
        } else {
            messageProgress = getString(getMessageTextProgressDialog());
            messageHtml = Html.fromHtml(messageProgress);
        }


        mProgressDialog.setMessage(messageHtml);
    }

    /**
     * Informa um máximo para o ProgressDialog
     * Ele não será mais indeterminado
     *
     * @param max
     */
    public void setProgressDialogMax(int max) {

        if (!isProgressableDialogShowing()) {
            return;
        }

        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(max);

    }


    /**
     * Obtém o máximo atual
     *
     * @return
     */
    public int getProgressDialogMax() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return mProgressDialog.getMax();
        }

        return -1;
    }

    /**
     * Obtém o progresso atual
     *
     * @return
     */
    public int getProgressDialogProgress() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return mProgressDialog.getProgress();
        }

        return -1;
    }

    /**
     * Informa o progresso para o diálogo
     *
     * @param progress
     */
    public void setProgressDialogProgress(int progress) {
        if (mProgressDialog != null) {
            mProgressDialog.setProgress(progress);
        }
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     */
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    /**
     * Abre o dialog com mensagem padrão ou customizada
     */
    public void showProgressDialog(@StringRes int messageResid) {
        showProgressDialog(getString(messageResid));
    }

    /**
     * Retira o progress
     */
    public void dismissProgressDialog() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
            progrableShowing = false;
            progressShowing = false;
            Log.d("PROGRESS_DIAOG_ACT", "dismissProgressDialog()");
        }
    }

    /**
     * Diz se o Progress Dialog está visível ou não
     *
     * @return
     */
    public boolean isProgressDialogShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    /**
     * Diz se o Progress Dialog está visível ou não
     *
     * @return
     */
    public boolean isProgressableDialogShowing() {
        return progrableShowing && mProgressDialog != null && mProgressDialog.isShowing();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //acao padrao sera essa, mas em caso de querer fazer
            //outra coisa com essa opcao, basta o filho pegar primeiro
            //NOTA:
            //Para que a opcao HOME da actionbar apareca, o filtho deve fazer
            // getSupportActionBar().setHomeButtonEnabled(true)
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       /* if (requestCode == AndroidPermissionsManager.getInstance(IonApplication.getInstance()).getCurrentRequestCode()) {

            List<String> permissionsGrantedsList = new ArrayList<>();
            List<String> permissionsDeniedsList = new ArrayList<>();

            //vamos separar os resultados
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    permissionsGrantedsList.add(permissions[i]);
                } else {
                    permissionsDeniedsList.add(permissions[i]);
                }
            }

            if (!permissionsGrantedsList.isEmpty()) {
                String[] permissionsGranteds = new String[permissionsGrantedsList.size()];
                permissionsGrantedsList.toArray(permissionsGranteds);
                onPermissionGranted(permissionsGranteds);
            }

            if (!permissionsDeniedsList.isEmpty()) {
                String[] permissionsDenieds = new String[permissionsDeniedsList.size()];
                permissionsDeniedsList.toArray(permissionsDenieds);
                onPermissionDenied(permissionsDenieds);

                if (AndroidPermissionsManager.getInstance(IonApplication.getInstance()).getCurrentMessageDenied() != -1) {
                    AlertDialog.show(BaseActivity.this, ThemeApplication.isThemeDark(), getString(R.string.permissao_acesso), getString(AndroidPermissionsManager.getInstance(IonApplication.getInstance()).getCurrentMessageDenied()));
                }
            }
        }*/
    }


    @CallSuper
    protected void onPermissionGranted(String[] permissions) {
        //AndroidPermissionsManager.getInstance(FinsoftApplication.getInstance()).getAndroidPermissionEvents().onPermissionGranted(null, permissions);
    }

    @CallSuper
    protected void onPermissionDenied(String[] permissions) {
        // AndroidPermissionsManager.getInstance(FinsoftApplication.getInstance()).getAndroidPermissionEvents().onPermissionDenied(null, permissions);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public interface OnResumeActivityListener {
        void onActivityResumed(BaseActivity baseActivity);

        void onActivityPaused(BaseActivity baseActivity);
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
        else{
            ViewCompat.setElevation(view, value);
        }
    }

    /**
     * Obtém a view pai de todas no layout
     *
     * @return
     */
    public ViewGroup getRootView() {
        return mRootView;
    }


    /**
     * Considerando que a ViewLoading nao é um aView utuil, o objetivo dessa função
     * é retornar aquela View que contem as Views utilizadas na navegação
     *
     * @return
     */
    public ViewGroup getRootViewUtil() {
        return viewContent != null ? viewContent : mRootView;
    }

    @Nullable
    public Fragment getActiveFragment() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null || fragmentList.size() == 0) {
            return null;
        }
        Fragment lastFragmentAdded = null;
        //Starts at the end since it was the last fragment added
        for (int i = fragmentList.size() - 1; i >= 0; i--) {

            Fragment fragment = fragmentList.get(i);
            if (fragment.isAdded()
                    && !fragment.isDetached()
                    && !fragment.isRemoving()
                    && fragment.isResumed()
                    && fragment.getActivity() != null) {
                lastFragmentAdded = fragment;
                break;
            }
        }
        return lastFragmentAdded;
    }
}
