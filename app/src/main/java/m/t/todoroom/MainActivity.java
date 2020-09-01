package m.t.todoroom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import m.t.todoroom.adapter.TaskAdapter;
import m.t.todoroom.model.AppDatabase;
import m.t.todoroom.model.SQLiteHelper;
import m.t.todoroom.model.Task;
import m.t.todoroom.model.TaskDao;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddTaskItemTitle, TaskAdapter.CheckedTaskItemListener,
        TaskAdapter.DeleteTaskItemListener, TaskAdapter.UpdateTaskItemListener, EditTaskDialog.EditTaskItemTitle {
    private static final String TAG = "MainActivity";
    private TaskAdapter taskAdapter;
    private SQLiteHelper sqLiteHelper;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // وقتی از کتابخانه استفاده نمی کنیم
        //  sqLiteHelper = new SQLiteHelper(this);
        //  taskAdapter.showAll(sqLiteHelper.getAll());
        //وقتی از کتابخانه استفاده می کنیم

        taskAdapter = new TaskAdapter(this);
        taskDao = AppDatabase.getAppDatabase(this).getTaskDao();
        taskAdapter.showAll(taskDao.getAll());

        EditText editText = findViewById(R.id.et_main_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    //taskAdapter.showAll(sqLiteHelper.search(s.toString()));
                    taskAdapter.showAll(taskDao.search(s.toString()));
                } else {
                   // taskAdapter.showAll(sqLiteHelper.getAll());

                    taskAdapter.showAll(taskDao.getAll());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_main_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);


        View addTaskBtn = findViewById(R.id.btn_add_task);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog dialog = new AddTaskDialog(MainActivity.this);
                dialog.show(getSupportFragmentManager(), null);
            }
        });

        View deleteAllBtn = findViewById(R.id.iv_btn_deleteAll);
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sqLiteHelper.deleteAll();
                taskDao.deleteAll();
                taskAdapter.deleteAll();
            }
        });

    }

    @Override
    public void addTask(String title) {
        //  Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
        Task task = new Task();
        task.setTitle(title);
        //long res = sqLiteHelper.add(task);
        long res = taskDao.add(task);
        if (res > 0) {
            taskAdapter.add(task);
        }
    }

    @Override
    public void checked(Task task, int loc) {
        // int res =sqLiteHelper.update(task);
        int res = taskDao.update(task);
        if (res > 0) {
            taskAdapter.update(task, loc);
        }
    }

    @Override
    public void deleteTask(Task task) {
       // int res = sqLiteHelper.delete(task);
        int res = taskDao.delete(task);
        if (res > 0) {
            taskAdapter.delete(task);
        }
    }

    @Override
    public void updateTask(Task task, int loc) {
        EditTaskDialog editTaskDialog = new EditTaskDialog(this);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        bundle.putInt("loc", loc);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void updateTitleTask(Task title, int loc) {
       // int res = sqLiteHelper.update(title);
        int res = taskDao.update(title);
        if (res > 0) {
            taskAdapter.update(title, loc);
        }
    }
}
