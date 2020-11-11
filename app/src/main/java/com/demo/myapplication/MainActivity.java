package com.demo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String path;
    //Uri uri;
    Context context;
    ImageView finalSavedImage;
    int PERMISSION_ALL = 1111;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TestFolder");
        this.path = directory.getAbsolutePath()+"/image1.jpg";
      //  this.uri = Uri.fromFile(new File(path));
        context = this;
        // Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
        this.finalSavedImage = (ImageView) findViewById(R.id.Imgoutput);

        //finalSavedImage.setBackground(getResources().getDrawable(R.drawable.place));
        if (hasPermissions(MainActivity.this, this.PERMISSIONS)) {
            loadimage();

        }else {
            ActivityCompat.requestPermissions(MainActivity.this, this.PERMISSIONS, this.PERMISSION_ALL);
        }

    }

     public void loadimage(){
         File file = new File(path);
         if (file.exists())
             Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
         Uri uriForFile1 = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", new File(MainActivity.this.path));
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
//        finalSavedImage.setImageBitmap(bitmap);
         // bitmap = Bitmap.createScaledBitmap(bitmap,this.getWidth(),parent.getHeight(),true);
         //with.asBitmap().load(bitmap).into(finalSavedImage);
         Glide.with(context).load(uriForFile1).into(this.finalSavedImage);
     }
//    @TargetApi(23)
//    public void Get_CameraAndStorage_Permission() {
//        ArrayList arrayList = new ArrayList();
//        ArrayList arrayList2 = new ArrayList();
//        if (!addPermission(arrayList2, "android.permission.READ_EXTERNAL_STORAGE")) {
//            arrayList.add("Read Storage");
//        }
////        if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
////            arrayList.add("Write Storage");
////        }
//        if (!addPermission(arrayList2, "android.permission.CAMERA")) {
//            arrayList.add("Camera");
//        }
//        if (arrayList2.size() > 0) {
//            if (arrayList.size() > 0) {
//                for (int i = 0; i < 1; i++) {
//                    requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 124);
//                }
//                return;
//            }
//            requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 124);
//        }
//    }
//
//    @SuppressLint({"WrongConstant"})
//    @TargetApi(23)
//    private boolean addPermission(List<String> list, String str) {
//        if (checkSelfPermission(str) != 0) {
//            list.add(str);
//            if (!shouldShowRequestPermissionRationale(str)) {
//                return false;
//            }
//        }
//        return true;
//    }
    public boolean hasPermissions(Context context, String... strArr) {
        if (!(Build.VERSION.SDK_INT < 23 || context == null || strArr == null)) {
            for (String checkSelfPermission : strArr) {
                if (ActivityCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == this.PERMISSION_ALL) {
            int i2 = 0;
            boolean z = false;
            while (i2 < iArr.length) {
                String str = strArr[i2];
                boolean z2 = iArr[i2] == 0;
                if (iArr[i2] == -1) {
                    if (Build.VERSION.SDK_INT >= 23 ? shouldShowRequestPermissionRationale(str) : false) {
                        alertView();
                    }
                }
                i2++;
                z = z2;
            }
            if (!z) {
                return;
            }
            loadimage();

        }
    }
    private void alertView() {
        String str = "RE-TRY";
        new AlertDialog.Builder(MainActivity.this).setTitle((CharSequence) "Permission Denied").setMessage((CharSequence) "Without those permission the app is unable to save your profile. App needs to save profile image in your external storage and also need to get profile image from camera or external storage.Are you sure you want to deny this permission?").setNegativeButton((CharSequence) "I'M SURE", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.hasPermissions(MainActivity.this, MainActivity.this.PERMISSIONS);
            }
        }).show();
    }
}