package dev.kaua.river;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import dev.kaua.river.Activitys.IntroActivity;
import dev.kaua.river.Activitys.SignInActivity;

public class Warnings {
    private static BottomSheetDialog bottomSheetDialog;
    private static Dialog WarningError;

    //  Create Show To Base Message
    public static void Base_Sheet_Alert(Activity context, String msg, boolean cancelable) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
        bottomSheetDialog.setCancelable(cancelable);
        //  Creating View for SheetMenu
        View sheetView = LayoutInflater.from(context).inflate(R.layout.adapter_sheet_menu_base,
                context.findViewById(R.id.sheet_menu_base));
        LinearLayout btn_negative_sheet = sheetView.findViewById(R.id.btn_negative_sheet);
        btn_negative_sheet.setVisibility(View.GONE);
        TextView txt_positive_button_sheet = sheetView.findViewById(R.id.txt_positive_button_sheet);
        txt_positive_button_sheet.setText(context.getString(R.string.ok));

        //  Set Main Message
        TextView txt_main_text_sheet = sheetView.findViewById(R.id.txt_main_text_sheet);
        txt_main_text_sheet.setText(msg);

        sheetView.findViewById(R.id.btn_positive_sheet).setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    //  Create Show To did not receive email
    public static void DidNot_receive_email(Activity context, String msg_main, String msg, int case_type) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
        //  Creating View for SheetMenu
        View sheetView = LayoutInflater.from(context).inflate(R.layout.adapter_sheet_menu_did_not_receive_email,
                context.findViewById(R.id.sheet_menu_did_not_receive_email));
        LinearLayout btn_negative_sheet = sheetView.findViewById(R.id.btn_negative_sheet);
        btn_negative_sheet.setVisibility(View.GONE);
        TextView txt_positive_button_sheet = sheetView.findViewById(R.id.txt_positive_button_sheet);
        txt_positive_button_sheet.setText(msg);

        //  Set Main Message
        TextView txt_main_text_sheet_did_not_receive_email = sheetView.findViewById(R.id.txt_main_text_sheet_did_not_receive_email);
        txt_main_text_sheet_did_not_receive_email.setText(msg_main);

        sheetView.findViewById(R.id.btn_positive_sheet).setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    //  Create Show To Really want to leave?
    public static void Sheet_Really_want_to_leave_emailValidation(Activity context) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
        //  Creating View for SheetMenu
        View sheetView = LayoutInflater.from(context).inflate(R.layout.adapter_sheet_menu_base,
                context.findViewById(R.id.sheet_menu_base));
        TextView txt_positive_button_sheet = sheetView.findViewById(R.id.txt_positive_button_sheet);
        txt_positive_button_sheet.setText(context.getString(R.string.yes));

        //  Set Main Message
        TextView txt_main_text_sheet = sheetView.findViewById(R.id.txt_main_text_sheet);
        txt_main_text_sheet.setText(context.getString(R.string.account_will_not_be_validated_leave));

        sheetView.findViewById(R.id.btn_positive_sheet).setOnClickListener(v -> {
            Intent goTo_intro = new Intent(context, SignInActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.move_to_right, R.anim.move_to_right);
            ActivityCompat.startActivity(context, goTo_intro, activityOptionsCompat.toBundle());
            ((Activity) context).finish();
            bottomSheetDialog.dismiss();
        });

        sheetView.findViewById(R.id.btn_negative_sheet).setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    public static void showWeHaveAProblem(Context context){
        WarningError = new Dialog(context);

        CardView btnOk_WeHaveAProblem;
        WarningError.setContentView(R.layout.adapter_wehaveaproblem);
        WarningError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnOk_WeHaveAProblem = WarningError.findViewById(R.id.btnOk_WeHaveAProblem);
        btnOk_WeHaveAProblem.setElevation(10);
        WarningError.setCancelable(false);

        btnOk_WeHaveAProblem.setOnClickListener(v -> WarningError.dismiss());

        WarningError.show();
    }
}
