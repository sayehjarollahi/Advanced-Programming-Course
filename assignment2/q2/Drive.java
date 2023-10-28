
public class Drive extends Keeper {
    private String name;
    private int size;
    private int remainingSize;
    private Keeper parent;

    public Drive(String name, int size) {
        super();
        this.name = name;
        this.size = size;
        this.remainingSize = size;
        this.parent = null;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public Folder getSubFolderByName(String name) {
        for (Folder subFolder : subFolders) {
            if (subFolder.getName().equalsIgnoreCase(name))
                return subFolder;
        }
        return null;
    }

    @Override
    public Keeper getParent() {
        return null;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public void increaseRemainingSize(int number) {
        remainingSize += number;
    }

    public void decreaseRemainingSize(int number) {
        remainingSize -= number;
    }

    @Override
    public Drive getDrive() {
        return this;
    }

    @Override
    public String toString() {
        return this.name + " " + this.size + "MB " + (this.size - this.remainingSize) + "MB";
    }
}
