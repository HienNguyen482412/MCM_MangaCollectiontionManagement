package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mcm_mangacollectionmanagement.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

import MCMClass.Author;
import MCMClass.MangaCollection;
import MCMClass.Publisher;
import database.AuthorDatabase;
import database.CollectionDatabase;
import database.PublisherDatabase;

public class ArrayAdapter_Collection extends ArrayAdapter<MangaCollection> {
    Activity context;
    int idLayout;
    ArrayList<MangaCollection> list;
    public ArrayAdapter_Collection(Activity context, int idLayout, ArrayList<MangaCollection> list) {
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
        AuthorDatabase authorDatabase = new AuthorDatabase(context);
        PublisherDatabase publisherDatabase = new PublisherDatabase(context);
        CollectionDatabase collectionDatabase = new CollectionDatabase(context);
        TextView txtCollectionName = convertView.findViewById(R.id.txtCollectionName);
        TextView txtAuthorName = convertView.findViewById(R.id.txtAuthor_CName);
        TextView txtPublisherName = convertView.findViewById(R.id.txtPublisher_CName);
        TextView txtTotalChapter = convertView.findViewById(R.id.txtTotalChapter);
        ImageView imgCover = convertView.findViewById(R.id.imgCollectionCover);
        MangaCollection collection = list.get(position);
        txtCollectionName.setText(collection.getName());

        txtAuthorName.setText(authorDatabase.getAuthor(collection.getAuthorId()).getName());
        txtPublisherName.setText(publisherDatabase.getPublisher(collection.getPublisherId()).getName());
        txtTotalChapter.setText(String.valueOf(collectionDatabase.getTotalChapter(collection.getId())));

        byte[] bytes = collection.getImage();
        if (bytes!= null && bytes.length> 0){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imgCover.setImageBitmap(bitmap);
        }
        else {
            imgCover.setImageResource(R.drawable.nocover);
        }
        return convertView;
    }
}
