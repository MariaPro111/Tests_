package com.example.tests;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {
    ListView listView;
    Long questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);


        listView = findViewById(R.id.answers);


        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("question")) {
            Question question = (Question) getIntent().getExtras().get("question");
            questionId = question.getId();
        } else {
            questionId = null;
        }




        List<Answer> answers = generateAnswers();
        AnswersActivity.AnswersAdapter adapter = new AnswersActivity.AnswersAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, answers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(AnswersActivity.this, AfterAnswerActivity.class);
                startActivity(intent);
            }
        });
    }
    private List<Answer> generateAnswers() {
        List<Answer> answers = new ArrayList<>();

        TestsDataBase testsdb=new TestsDataBase(this);

        SQLiteDatabase database=testsdb.getWritableDatabase();

        Cursor c;
        if (questionId != null) {
            c = database.rawQuery("select _id, answer, question_id from answers where question_id = " + questionId , null);
        } else {
            c = database.rawQuery("select _id, answer, question_id from answers where question_id = " + questionId , null);
        }

        int answerIndex = c.getColumnIndex("answer");
        int idIndex = c.getColumnIndex("_id");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Answer an = new Answer(c.getLong(idIndex), c.getString(answerIndex));
            answers.add(an);
            c.moveToNext();
        }

        return answers;

    }

    public static class AnswersAdapter extends ArrayAdapter<Answer> {
        public AnswersAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
            super(context, resource, objects);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Answer an = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.answer_item, null);
            TextView answer = convertView.findViewById(R.id.answer);
            answer.setText(an.getAnswer());

            return convertView;
        }
    }

}

