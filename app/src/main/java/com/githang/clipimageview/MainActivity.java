package com.githang.clipimageview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private String mOutputPath;
    private String mDemoPath;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDemoPath = new File(getExternalCacheDir(), "demo.jpg").getPath();
        mOutputPath = new File(getExternalCacheDir(), "chosen.jpg").getPath();
        prepareDemo();
    }

    private void prepareDemo() {
        if (new File(mDemoPath).exists()) {
            return;
        }
        OutputStream out = null;
        InputStream in = null;
        try {
            out = new FileOutputStream(mDemoPath);
            in = getAssets().open("01.jpg");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(in);
            IOUtils.close(out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        IOUtils.deleteFile(mOutputPath);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_take_photo) {
            PhotoActionHelper.takePhoto(this).output(mOutputPath).maxOutputWidth(800)
                    .requestCode(Const.REQUEST_TAKE_PHOTO).start();
            return true;
        } else if (id == R.id.action_clip_image) {
            PhotoActionHelper.clipImage(this).input(mDemoPath).output(mOutputPath)
                    .requestCode(Const.REQUEST_CLIP_IMAGE).start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK
                && data != null
                && (requestCode == Const.REQUEST_CLIP_IMAGE || requestCode == Const.REQUEST_TAKE_PHOTO)) {
            String path = PhotoActionHelper.getOutputPath(data);
            if (path != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mImageView.setImageBitmap(bitmap);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
