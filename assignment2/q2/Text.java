public class Text extends File {
    private String text;

    public Text(String name, Keeper parent, int size, String text) {
        super(name, parent, size);
        this.text = text;
    }

    public Text(Text txt, Keeper parent) {
        super(txt, parent);
        text = txt.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getType() {
        return "txt";
    }

    @Override
    public String toString() {
        return this.name + " txt " + this.size + "MB";
    }
}
