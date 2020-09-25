package org.piedev.implicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final int CAMERA_PIC_REQUEST = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btn_llamada = (Button)findViewById(R.id.btn_call);
    btn_llamada.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Uri number = Uri.parse("tel:952654851");
        Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
        startActivity(callIntent);
      }
    });

    Button btn_ubicar = (Button)findViewById(R.id.btn_location);
    btn_ubicar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Uri location = Uri.parse("geo:0.0?q=Universidad+Privada+de+Tacna,+Granada,+Tacna");
        Intent locationIntent = new Intent(Intent.ACTION_VIEW,location);
        startActivity(locationIntent);
      }
    });

    Button btn_navegador = (Button)findViewById(R.id.btn_web);
    btn_navegador.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Uri portal = Uri.parse("http://upt.edu.pe");
        Intent webIntent = new Intent(Intent.ACTION_VIEW,portal);
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(webIntent,
            PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        String titulo = getResources().getString(R.string.chooser_title);
        Intent wChooser = Intent.createChooser(webIntent,titulo);
        if(isIntentSafe){
          startActivity(wChooser);
        }
      }
    });

    ((Button)findViewById(R.id.btn_camera)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
      }
    });
  }

  @Override
  protected void onActivityResult(int requesCode, int resultCode, Intent data){
    super.onActivityResult(requesCode,resultCode,data);
    if(requesCode == CAMERA_PIC_REQUEST){
      if(resultCode == RESULT_OK){
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        ImageView iv_foto = (ImageView)findViewById(R.id.iv_picture);
        iv_foto.setImageBitmap(bitmap);
      }
    }
  }
}