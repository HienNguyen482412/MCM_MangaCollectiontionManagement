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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ArrayAdapter_DESCMangaList extends ArrayAdapter<String> {
    Activity context;
    int idLayout;
    ArrayList<String> list;
    public ArrayAdapter_DESCMangaList(Activity context, int idLayout, ArrayList<String> list) {
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
        TextView txtCollectionName = convertView. findViewById(R.id.txtCollectionNameDESC);
        TextView txtMangaName = convertView.findViewById(R.id.txtMangaNameDESC);
        TextView txtMangaPrice = convertView.findViewById(R.id.txtMangaPriceDESC);
        if (list!= null){
            String[] item = list.get(position).split("-");
            txtCollectionName.setText(item[0]);
            txtMangaName.setText(item[2]);
            txtMangaPrice.setText(item[3]);
        }
        else {
            txtMangaName.setText("");
            txtCollectionName.setText("");
            txtMangaPrice.setText("");
        }
        return convertView;
    }
}
