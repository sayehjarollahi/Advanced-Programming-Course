import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public abstract class Keeper {
    protected ArrayList<Folder> subFolders;
    protected ArrayList<File> subFiles;

    public Keeper() {
        subFiles = new ArrayList<>();
        subFolders = new ArrayList<>();
    }


    public boolean isThereFolderWithName(String name) {
        for (Folder subFolder : subFolders) {
            if (subFolder.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<File> getSubFiles() {
        return subFiles;
    }

    public ArrayList<Folder> getSubFolders() {
        return subFolders;
    }

    public void clearSubFoldersFiles() {
        this.subFolders.clear();
        this.subFiles.clear();
    }

    public boolean isThereFileWithName(String name) {
        for (File subFile : subFiles) {
            if (subFile.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public abstract Folder getSubFolderByName(String name);

    public abstract Keeper getParent();

    public abstract String getName();

    public void addSubFolder(Folder folder) {
        subFolders.add(folder);
    }

    public void addNewFile(File file) {
        subFiles.add(file);
    }

    public abstract Drive getDrive();

    public void deleteFile(String name) {
        for (File subFile : subFiles) {
            if (subFile.getName().equalsIgnoreCase(name)) {
                subFiles.remove(subFile);
                break;
            }
        }
    }

    public File getSubFileByName(String name) {
        for (File subFile : subFiles) {
            if (subFile.getName().equalsIgnoreCase(name))
                return subFile;
        }
        return null;
    }

    public void deleteFolder(String name) {
        for (Folder subFolder : subFolders) {
            if (subFolder.getName().equalsIgnoreCase(name)) {
                subFolders.remove(subFolder);
                break;
            }
        }
    }

    public String address() {
        String address = "";
        Keeper parent = this;
        if (this.getParent() == null) {
            return this.getDrive().getName() + ":";
        }
        address = "\\" + this.getName();
        while (parent.getParent() != null) {
            parent = parent.getParent();
            address = "\\" + parent.getName() + address;
        }
        address = address.charAt(1) + ":" + address.substring(2);
        return address;
    }

    public void showSubFolders() {
        Collections.sort(subFolders, new SortFolders());
        for (Folder folder : subFolders) {
            System.out.println(folder);
        }
    }

    public void showSubFiles() {
        Collections.sort(this.subFiles, new SortFiles());
        for (File file : subFiles) {
            System.out.println(file);
        }
    }

    public ArrayList<Folder> getAllSubFolders() {
        ArrayList<Folder> queue = new ArrayList<>();
        ArrayList<Folder> result = new ArrayList<>();
        queue.addAll(this.subFolders);
        while (!queue.isEmpty()) {
            queue.addAll(queue.get(0).subFolders);
            result.add(queue.get(0));
            queue.remove(0);
        }
        return result;
    }

    public ArrayList<File> getAllSubFiles() {
        ArrayList<File> allSubFiles = new ArrayList<>();
        allSubFiles.addAll(this.subFiles);
        ArrayList<Folder> folders = this.getAllSubFolders();
        for (Folder folder : folders) {
            allSubFiles.addAll(folder.subFiles);
        }
        return allSubFiles;
    }

    static class SortFolders implements Comparator<Folder> {
        @Override
        public int compare(Folder folder1, Folder folder2) {
            return folder1.getName().compareTo(folder2.getName());
        }
    }

    static class SortFiles implements Comparator<File> {
        @Override
        public int compare(File file1, File file2) {
            String type1 = file1.getType();
            String type2 = file2.getType();
            if (type1 == "mp4")
                type1 = "video";
            if (type2 == "mp4")
                type2 = "video";
            int type = type1.compareTo(type2);
            int name = file1.getName().compareTo(file2.getName());
            if (type == 0) {
                return name;
            }
            return type;
        }
    }
}
