package by.seobility.kinoclub.ui.filter.rowtypes;

public interface RowType {
    int BUTTON_ROW_TYPE =   0;
    int LIST_ROW_TYPE = 1;
    int SEEKBAR_ROW_TYPE = 2;
    int SUBMIT_BUTTON_ROW_TYPE = 3;

    int getItemViewType();
}
