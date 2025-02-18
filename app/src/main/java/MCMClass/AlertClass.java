package MCMClass;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mcm_mangacollectionmanagement.R;

public class AlertClass {
    Context context;

    public AlertClass(Context context) {
        this.context = context;
    }

    public interface AlertDialogCallback{
        void onResult(boolean isDone);
    }
    public void showAlertDialog(String title, String description , int imgID, boolean ButtonCancel, AlertDialogCallback alertDialogCallback){
//        LinearLayout successLinearLayout = findViewById(R.id.containlayout);
        View viewSuccess = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null);
        Button successDone = viewSuccess.findViewById(R.id.successdone);
        Button btnCancel = viewSuccess.findViewById(R.id.cancel);
        ImageView imageView = viewSuccess.findViewById(R.id.successimage);
        TextView txtTitle = viewSuccess.findViewById(R.id.successtitle);
        TextView txtDesc = viewSuccess.findViewById(R.id.successdesc);
        imageView.setImageResource(imgID);
        txtTitle.setText(title);
        txtDesc.setText(description);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(viewSuccess);
        final AlertDialog alertDialog = builder.create();
        btnCancel.setVisibility(View.GONE);
        if (ButtonCancel){
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogCallback.onResult(false);
                    alertDialog.dismiss();
                }
            });
        }

        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                alertDialogCallback.onResult(true);
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }
    public  void showSimpleAlerDialog(String title, String description , int imgID){
        View viewSuccess = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null);
        Button successDone = viewSuccess.findViewById(R.id.successdone);
        Button btnCancel = viewSuccess.findViewById(R.id.cancel);
        ImageView imageView = viewSuccess.findViewById(R.id.successimage);
        TextView txtTitle = viewSuccess.findViewById(R.id.successtitle);
        TextView txtDesc = viewSuccess.findViewById(R.id.successdesc);
        imageView.setImageResource(imgID);
        txtTitle.setText(title);
        txtDesc.setText(description);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(viewSuccess);
        final AlertDialog alertDialog = builder.create();
        successDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
        btnCancel.setVisibility(View.GONE);
    }
}
