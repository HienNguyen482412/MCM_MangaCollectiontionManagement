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

import MCMClass.Manga;

public class ArrayAdapter_Manga extends ArrayAdapter<Manga> {
    Activity context;
    int idLayout;
    ArrayList<Manga> list;
    public ArrayAdapter_Manga(Activity context, int idLayout, ArrayList<Manga> list) {
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
        TextView txtChapter = convertView.findViewById(R.id.txtMangaChapter);
        TextView txtType = convertView.findViewById(R.id.txtMangaType);
        TextView txtStatus = convertView.findViewById(R.id.txtMangaStatus);
        TextView txtPrice = convertView.findViewById(R.id.txtMangaPrice);
        TextView txtDate = convertView.findViewById(R.id.txtMangaDate);
        Manga manga = list.get(position);
        txtChapter.setText(manga.getChapter());
        txtType.setText(manga.getType());
        txtStatus.setText(manga.getStatus());
        txtPrice.setText(String.valueOf(manga.getPrice()));
        txtDate.setText(manga.getDate());
        return convertView;
    }
}
