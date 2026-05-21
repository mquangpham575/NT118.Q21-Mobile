package uit.edu.vn.lab05_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode, btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml, btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode, btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    private void findViewsByIds() {
        ivUitLogo = (ImageView) findViewById(R.id.iv_uit_logo);
        btnFadeInXml = (Button) findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = (Button) findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = (Button) findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = (Button) findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = (Button) findViewById(R.id.btn_blink_xml);
        btnBlinkCode = (Button) findViewById(R.id.btn_blink_code);
        btnZoomInXml = (Button) findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = (Button) findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = (Button) findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = (Button) findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = (Button) findViewById(R.id.btn_rotate_xml);
        btnRotateCode = (Button) findViewById(R.id.btn_rotate_code);
        btnMoveXml = (Button) findViewById(R.id.btn_move_xml);
        btnMoveCode = (Button) findViewById(R.id.btn_move_code);
        btnSlideUpXml = (Button) findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = (Button) findViewById(R.id.btn_slide_up_code);
        btnBounceXml = (Button) findViewById(R.id.btn_bounce_xml);
        btnBounceCode = (Button) findViewById(R.id.btn_bounce_code);
        btnCombineXml = (Button) findViewById(R.id.btn_combine_xml);
        btnCombineCode = (Button) findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    private void handleClickAnimationXml(Button btn, final int animId) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, animId);
                anim.setAnimationListener(animationListener);
                ivUitLogo.startAnimation(anim);
            }
        });
    }

    private void handleClickAnimationCode(Button btn, final Animation animation) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.setAnimationListener(animationListener);
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();

        
        handleClickAnimationXml(btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml, R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXml(btnCombineXml, R.anim.anim_combine);

        
        
        final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        handleClickAnimationCode(btnFadeInCode, fadeIn);

        
        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        handleClickAnimationCode(btnFadeOutCode, fadeOut);

        
        final AlphaAnimation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(300);
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(3);
        handleClickAnimationCode(btnBlinkCode, blink);

        
        final ScaleAnimation zoomIn = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomIn.setDuration(1000);
        zoomIn.setFillAfter(true);
        handleClickAnimationCode(btnZoomInCode, zoomIn);

        
        final ScaleAnimation zoomOut = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomOut.setDuration(1000);
        zoomOut.setFillAfter(true);
        handleClickAnimationCode(btnZoomOutCode, zoomOut);

        
        final RotateAnimation rotate = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(600);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(2);
        rotate.setInterpolator(new CycleInterpolator(1));
        handleClickAnimationCode(btnRotateCode, rotate);

        
        final TranslateAnimation move = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.75f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f
        );
        move.setDuration(800);
        move.setFillAfter(true);
        move.setInterpolator(new LinearInterpolator());
        handleClickAnimationCode(btnMoveCode, move);

        
        final ScaleAnimation slideUp = new ScaleAnimation(1f, 1f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        slideUp.setDuration(500);
        slideUp.setFillAfter(true);
        handleClickAnimationCode(btnSlideUpCode, slideUp);

        
        final ScaleAnimation bounce = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bounce.setDuration(500);
        bounce.setFillAfter(true);
        bounce.setInterpolator(new BounceInterpolator());
        handleClickAnimationCode(btnBounceCode, bounce);

        
        final AnimationSet combine = new AnimationSet(true);
        combine.setFillAfter(true);
        combine.setInterpolator(new LinearInterpolator());

        ScaleAnimation combineScale = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        combineScale.setDuration(4000);

        RotateAnimation combineRotate = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        combineRotate.setDuration(500);
        combineRotate.setRepeatCount(2);
        combineRotate.setRepeatMode(Animation.RESTART);

        combine.addAnimation(combineScale);
        combine.addAnimation(combineRotate);
        handleClickAnimationCode(btnCombineCode, combine);

        
        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
