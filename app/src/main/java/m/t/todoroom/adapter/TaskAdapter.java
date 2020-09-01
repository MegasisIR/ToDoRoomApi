package m.t.todoroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import m.t.todoroom.R;
import m.t.todoroom.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private CheckedTaskItemListener checkedTaskItemListener;
    private DeleteTaskItemListener deleteTaskItemListener;
    private UpdateTaskItemListener updateTaskItemListener;

    public TaskAdapter(Context context) {
        this.checkedTaskItemListener = (CheckedTaskItemListener) context;
        this.deleteTaskItemListener = (DeleteTaskItemListener) context;
        this.updateTaskItemListener = (UpdateTaskItemListener) context;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ImageView deleteTaskIv;
        private CheckBox doneTaskChB;
        private View lineCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            deleteTaskIv = itemView.findViewById(R.id.btn_itemTask_deleteTask);
            doneTaskChB = itemView.findViewById(R.id.checkbox_itemTask_tik);
            lineCompleted = itemView.findViewById(R.id.line_delete);

        }

        void bindTask(final Task task) {
            doneTaskChB.setChecked(task.isCompleted());
            doneTaskChB.setText(task.getTitle());
            lineCompleted.setVisibility(task.isCompleted() ? View.VISIBLE : View.GONE);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    updateTaskItemListener.updateTask(task,getAdapterPosition());
                    return false;
                }
            });
            doneTaskChB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setCompleted(!task.isCompleted());
                    lineCompleted.setVisibility(!task.isCompleted() ? View.VISIBLE : View.GONE);
                    checkedTaskItemListener.checked(task, getAdapterPosition());
                }
            });

            deleteTaskIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTaskItemListener.deleteTask(task);
                }
            });
        }
    }

    //add
    public void add(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    //delete
    public void delete(Task task) {
        int res = tasks.indexOf(task);
        tasks.remove(res);
        notifyItemRemoved(res);
    }

    //search
    public void search(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    //update
    public void update(Task task, int loc) {
        this.tasks.set(loc, task);
        notifyItemChanged(loc);
    }

    //deleteAll
    public void deleteAll() {
        this.tasks.clear();
        notifyDataSetChanged();
    }

    //showAll
    public void showAll(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }


    public interface CheckedTaskItemListener {
        void checked(Task task, int loc);
    }

    public interface DeleteTaskItemListener {
        void deleteTask(Task task);
    }

    public interface UpdateTaskItemListener {
        void updateTask(Task task,int loc);
    }
}
