package m.t.todoroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskDialog extends DialogFragment {

    private AddTaskItemTitle addTaskItemTitle;

    public AddTaskDialog(AddTaskItemTitle addTaskItemTitle) {
        this.addTaskItemTitle = addTaskItemTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null, false);
        builder.setView(view);

        MaterialButton saveAddTaskBtn = view.findViewById(R.id.btn_dialog_saveTask);
        final TextInputEditText textInputEditText = view.findViewById(R.id.ed_dialog_addTask);
        saveAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskItemTitle.addTask(textInputEditText.getText().toString());
                dismiss();
            }
        });
        return builder.create();
    }

    public interface AddTaskItemTitle {
        void addTask(String title);
    }
}
