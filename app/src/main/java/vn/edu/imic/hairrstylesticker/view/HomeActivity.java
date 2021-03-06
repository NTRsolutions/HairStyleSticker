package vn.edu.imic.hairrstylesticker.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.AdChoicesView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.NativeAd;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.edu.imic.hairrstylesticker.R;
import vn.edu.imic.hairrstylesticker.utils.Const;
import vn.edu.imic.hairrstylesticker.utils.PermissionUtils;

public class HomeActivity extends AppCompatActivity {
    /**/
    private Unbinder unbinder;
    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.btn_start)
    ImageView btnStart;
    @BindView(R.id.gv_icon)
    com.cunoraz.gifview.library.GifView gvIcon;
    private AdChoicesView adChoicesView;
    //@BindView(R.id.ll_native_ads)
    LinearLayout llAdsView;
    InterstitialAd n;
    private NativeAd nativeAd;
    /*Web thể hiện ads*/
    private String startAds = "http://www.appwallettech.com/appwalletftp/StartAdFile.xml";
    private String exitAds = "http://www.appwallettech.com/appwalletftp/exitad.xml";
    boolean q = Boolean.parseBoolean(null);
    String[] s = null;
    String[] t = null;
    Context v = this;
    boolean w = false;
    Button x;
    boolean y = false;
    Dialog z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);
/*
        gvIcon.setVisibility(View.VISIBLE);
        gvIcon.play();
        gvIcon.pause();
        gvIcon.setGifResource(R.drawable.animation35);
*/

        if (!PermissionUtils.isPermissionCameraGranted(this) ||
                !PermissionUtils.isPermissionExternalStorageGranted(this)) {
            Dexter.withActivity(this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Log.d(TAG, "Permissions are granted!");
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                //showSettingDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).withErrorListener(new PermissionRequestErrorListener() {
                @Override
                public void onError(DexterError error) {

                }
            }).onSameThread().check();
        }
    }

    /*Show setting dialog*/
    private void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Đến màn hình setting app!");
        builder.setPositiveButton("GoTo Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSetting();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    /*Open setting app*/
    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Const.REQUEST_PERMISSIONS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_start)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_start:
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                break;
        }
    }
}
