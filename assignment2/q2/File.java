public abstract class File {
    protected String name;
    protected int size;
    protected Keeper parent;


    public File(String name, Keeper parent, int size) {
        this.name = name;
        this.size = size;
        this.parent = parent;
    }

    public File(File file, Keeper parent1) {
        name = file.name;
        size = file.size;
        parent = parent1;
    }

    public String getName() {
        return name;
    }

    public Keeper getParent() {
        return parent;
    }

    public void setParent(Keeper parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getType();

    @Override
    public abstract String toString();

    public String address() {
        String address = "\\" + this.name;
        Keeper parent = this.parent;
        while (parent != null) {
            address = "\\" + parent.getName() + address;
            parent = parent.getParent();
        }
        address = address.charAt(1) + ":" + address.substring(2);
        return address;
    }

}
