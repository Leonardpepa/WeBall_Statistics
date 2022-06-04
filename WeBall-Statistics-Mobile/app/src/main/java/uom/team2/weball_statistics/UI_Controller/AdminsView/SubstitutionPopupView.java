package uom.team2.weball_statistics.UI_Controller.AdminsView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import uom.team2.weball_statistics.R;
import uom.team2.weball_statistics.databinding.FragmentAdminsViewBinding;
import uom.team2.weball_statistics.databinding.FragmentPopupViewBinding;

public class SubstitutionPopupView extends Dialog implements
        android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;
    public TextView player1, player2;
    private String str;




    public SubstitutionPopupView(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.substitution_popup_view);

        TextView text=(TextView)findViewById(R.id.dialog_info);
        text.setText("2-Pointer Basket Made?");

        player1 = (TextView) findViewById(R.id.player1_text);
        player2 = (TextView) findViewById(R.id.player2_text);
        player1.setOnClickListener(this);
        player2.setOnClickListener(this);

        //


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_Yes:


                //dismiss();
                break;
            case R.id.dialog_No:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}