package com.github.htdangkhoa.recyclerviewhelper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        peoples.add(new Person("B", 21));
        peoples.add(new Person("C", 21));
        peoples.add(new Person("D", 21));
        peoples.add(new Person("E", 21));
        peoples.add(new Person("F", 21));
        peoples.add(new Person("G", 21));
        peoples.add(new Person("H", 21));
        peoples.add(new Person("I", 21));
        peoples.add(new Person("J", 21));
        peoples.add(new Person("K", 21));
        peoples.add(new Person("L", 21));
        peoples.add(new Person("M", 21));
        peoples.add(new Person("N", 21));
        peoples.add(new Person("O", 21));
        peoples.add(new Person("P", 21));
        peoples.add(new Person("Q", 21));
        peoples.add(new Person("R", 21));
        peoples.add(new Person("S", 21));
        peoples.add(new Person("T", 21));
        peoples.add(new Person("U", 21));
        peoples.add(new Person("V", 21));
        peoples.add(new Person("W", 21));
        peoples.add(new Person("X", 21));
        peoples.add(new Person("Y", 21));
        peoples.add(new Person("Z", 21));

        Adapter adapter = new Adapter(peoples);
        adapter.notifyDataSetChanged();

        new RecyclerViewHelper()
                .setContext(this)
                .setAdapter(adapter)
                .setRecyclerView(recyclerView)
                .setOrientation(RecyclerView.VERTICAL)
                .setDivider()
                .enableSwipeToDelete()
                .enableDragAndDrop()
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

class Adapter extends RecyclerViewHelperAdapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Person> peoples;

    public Adapter(ArrayList<Person> peoples) {
        super(peoples);
        this.peoples = peoples;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtView.setText(peoples.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtView = (TextView) itemView.findViewById(R.id.txtView);
        }
    }
}