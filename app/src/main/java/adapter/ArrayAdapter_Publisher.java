package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mcm_mangacollectionmanagement.R;

import java.util.ArrayList;

import MCMClass.Publisher;

public class ArrayAdapter_Publisher extends ArrayAdapter<Publisher> {
    Activity context;
    int idLayout;
    ArrayList<Publisher> list;
    public ArrayAdapter_Publisher(Activity context, int idLayout, ArrayList<Publisher> list) {
        super(context, idLayout, list);
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(idLayout, null);
        TextView txtId = convertView.findViewById(R.id.txtPublisherID);
        TextView txtName = convertView.findViewById(R.id.txtPublisherName);
        TextView txtStatus = convertView.findViewById(R.id.txtPublisherStatus);
        Publisher publisher = list.get(position);
        txtId.setText(publisher.getId());
        txtName.setText(publisher.getName());
        txtStatus.setText(publisher.getStatus());
        return convertView;
    }
}
