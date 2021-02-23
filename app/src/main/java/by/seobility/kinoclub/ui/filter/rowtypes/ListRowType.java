package by.seobility.kinoclub.ui.filter.rowtypes;

public class ListRowType implements RowType {
    private long id;
    private String text;
    private String type;

    public ListRowType(long id, String text, String type){
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getText(){
        return text;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getItemViewType() {
        return RowType.LIST_ROW_TYPE;
    }
}
