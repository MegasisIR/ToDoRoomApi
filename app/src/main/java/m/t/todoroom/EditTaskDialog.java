package m.t.todoroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import m.t.todoroom.model.Task;

public class EditTaskDialog extends DialogFragment {

    private EditTaskItemTitle editTaskItemTitle;

    public EditTaskDialog(EditTaskItemTitle editTaskItemTitle) {
        this.editTaskItemTitle = editTaskItemTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);
        builder.setView(view);

        MaterialButton saveAddTaskBtn = view.findViewById(R.id.btn_dialog_saveTask);
        final TextInputEditText textInputEditText = view.findViewById(R.id.et_dialog_editTask);
        Task task = new Task();
        task = getArguments().getParcelable("task");
        final int loc = getArguments().getInt("loc");
        textInputEditText.setText(task.getTitle());
        final Task finalTask = task;
        saveAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalTask.setTitle(textInputEditText.getText().toString());
                editTaskItemTitle.updateTitleTask(finalTask,loc);
                dismiss();
            }
        });
        return builder.create();
    }

    public interface EditTaskItemTitle {
        void updateTitleTask(Task title,int loc);
    }
}
