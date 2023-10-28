
import java.util.ArrayList;

public class Folder extends Keeper {
    private String name;
    private Keeper parent;
    private int visitedCount;

    public Folder(String name, Keeper parent) {
        super();
        this.name = name;
        this.parent = parent;
        this.visitedCount = 0;
    }

    public Folder(Folder folder, Keeper address) {
        super();
        name = folder.name;
        parent = address;
        visitedCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    public Integer getVisitedCount() {
        return visitedCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Folder getSubFolderByName(String name) {
        for (Folder subFolder : subFolders) {
            if (subFolder.name.equalsIgnoreCase(name))
                return subFolder;
        }
        return null;
    }

    public void setParent(Keeper parent) {
        this.parent = parent;
    }

    public void visited() {
        this.visitedCount += 1;
    }

    @Override
    public Keeper getParent() {
        return parent;
    }

    @Override
    public Drive getDrive() {
        Keeper parentOfFolder = this.parent;
        while (parentOfFolder != null && parentOfFolder.getParent() != null) {
            parentOfFolder = parentOfFolder.getParent();
        }
        return (Drive) parentOfFolder;
    }


    public int getSizeOfSubFiles() {
        ArrayList<File> files = this.getAllSubFiles();
        int size = 0;
        for (File file : files) {
            size += file.getSize();
        }
        return size;
    }


    @Override
    public String toString() {
        return this.name + " " + this.getSizeOfSubFiles() + "MB";
    }

    public void resetVisited() {
        ArrayList<Folder> folders = this.getAllSubFolders();
        this.visitedCount = 0;
        for (Folder folder : folders) {
            folder.visitedCount = 0;
        }

    }
}
