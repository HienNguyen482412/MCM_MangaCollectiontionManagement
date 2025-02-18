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

import MCMClass.Author;

public class ArrayAdaper_Author extends ArrayAdapter<Author> {
    Activity context;
    int idLayout;
    ArrayList<Author> list;
    public ArrayAdaper_Author(Activity context, int idLayout, ArrayList<Author> list) {
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
        TextView txtId = convertView.findViewById(R.id.txtAuthorID);
        TextView txtName = convertView.findViewById(R.id.txtAuthorName);
        TextView txtCountry = convertView.findViewById(R.id.txtAuthorCountry);
        Author author = list.get(position);
        txtId.setText(author.getId());
        txtName.setText(author.getName());
        txtCountry.setText(author.getNationality());
        return convertView;
    }
}
