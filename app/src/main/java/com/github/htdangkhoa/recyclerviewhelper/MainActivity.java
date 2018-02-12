package com.github.htdangkhoa.recyclerviewhelper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.htdangkhoa.library.Decoration.StickyItemListener;
import com.github.htdangkhoa.library.RecyclerViewHelper;
import com.github.htdangkhoa.library.Adapter.RecyclerViewHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Person> peoples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));
        peoples.add(new Person("A", 21));

        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));
        peoples.add(new Person("B", 21));

        Adapter adapter = new Adapter(peoples);
        adapter.notifyDataSetChanged();

        new RecyclerViewHelper()
                .setContext(this)
                .setAdapter(adapter)
                .setRecyclerView(recyclerView)
                .setOrientation(RecyclerView.VERTICAL)
//                .setDivider()
                .enableSwipeToDelete()
                .enableDragAndDrop()
                .enableStickyHeader()
                .build();
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Adapter extends RecyclerViewHelperAdapter<Adapter.ViewHolder> implements StickyItemListener<Adapter.ViewHolderHeader> {
    Context context;
    ArrayList<Person> peoples;

    ItemTouchHelper itemTouchHelper;

    public Adapter(ArrayList<Person> peoples) {
        super(peoples);
        this.peoples = peoples;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);

        itemTouchHelper = getItemTouchHelper();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txtView.setText(peoples.get(position).getName());

        holder.icDrag.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(holder);
            }

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    @Override
    public ViewHolderHeader onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.header, parent, false);

        return new ViewHolderHeader(view);
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolderHeader holder, int position) {
        holder.txtView.setText(peoples.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Log.i("CLICK", "onBindHeaderViewHolder: " + holder.txtView.getText().toString());
        });
    }

    @Override
    public char getSectionHeader(int position) {
        return peoples.get(position).getName().charAt(0);
    }

    @Override
    public boolean isSection(int position) {
        return position == 0
                || peoples.get(position).getName().charAt(0) != peoples.get(position - 1).getName().charAt(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        ImageView icDrag;

        public ViewHolder(View itemView) {
            super(itemView);

            txtView = (TextView) itemView.findViewById(R.id.txtView);
            icDrag = (ImageView) itemView.findViewById(R.id.icDrag);
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {
        TextView txtView;

        public ViewHolderHeader(View itemView) {
            super(itemView);

            txtView = (TextView) itemView.findViewById(R.id.txtView);
        }
    }
}