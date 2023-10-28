
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Computer {
    private String operatingSystemName;
    private String version;
    private int hardSize;
    private ArrayList<Drive> drives;
    private ArrayList<Folder> allFoldersInComputer;
    private Keeper currentLocation;
    private ArrayList<Folder> copyClipboardFolder;
    private ArrayList<File> copyClipBoardFile;
    private ArrayList<Folder> cutClipBoardFolder;
    private ArrayList<File> cutClipBoardFile;

    public Computer(String operatingSystemName, String version) {
        this.operatingSystemName = operatingSystemName;
        this.version = version;
        drives = new ArrayList<>();
        allFoldersInComputer = new ArrayList<>();
        copyClipBoardFile = new ArrayList<>();
        copyClipboardFolder = new ArrayList<>();
        cutClipBoardFile = new ArrayList<>();
        cutClipBoardFolder = new ArrayList<>();
    }

    public void setDrive(String name, int size) {
        drives.add(new Drive(name, size));
    }

    public void setCurrentDriveFirstTime() {
        this.currentLocation = drives.get(0);
    }

    public void setHardSize(int hardSize) {
        this.hardSize = hardSize;
    }

    public boolean openFolder(String name) {
        if (!currentLocation.isThereFolderWithName(name))
            return false;
        else {
            currentLocation = currentLocation.getSubFolderByName(name);
            ((Folder) currentLocation).visited();
            return true;
        }
    }

    public boolean changeDrive(String name) {
        for (Drive drive : drives) {
            if (drive.getName().equals(name)) {
                currentLocation = drive;
                return true;
            }
        }
        return false;
    }

    public void back() {
        if (currentLocation.getParent() != null)
            currentLocation = currentLocation.getParent();
    }

    public boolean createFolder(String name) {
        if (currentLocation.isThereFolderWithName(name))
            return false;
        Folder folder = new Folder(name, currentLocation);
        allFoldersInComputer.add(folder);
        currentLocation.addSubFolder(folder);
        return true;
    }

    public boolean canCreateFile(String name, String format, int size) {
        if (currentLocation.isThereFileWithName(name)) {
            System.out.println("file exists with this name");
            return false;
        } else if (!format.matches("(mp4|img|txt)")) {
            System.out.println("invalid format");
            return false;
        } else if (currentLocation.getDrive().getRemainingSize() < size) {
            System.out.println("insufficient drive size");
            return false;
        }
        return true;
    }

    public void createNewPhoto(String name, String resolution, String extension, int size) {
        Image image = new Image(name, currentLocation, size, resolution, extension);
        currentLocation.addNewFile(image);
        currentLocation.getDrive().decreaseRemainingSize(size);
    }

    public void createNewVideo(String name, String quality, String videoLength, int size) {
        Video video = new Video(name, currentLocation, size, quality, videoLength);
        currentLocation.addNewFile(video);
        currentLocation.getDrive().decreaseRemainingSize(size);
    }

    public void createNewText(String name, String text, int size) {
        Text newText = new Text(name, currentLocation, size, text);
        currentLocation.addNewFile(newText);
        currentLocation.getDrive().decreaseRemainingSize(size);
    }

    public boolean deleteFile(String name) {
        if (!currentLocation.isThereFileWithName(name))
            return false;
        currentLocation.getDrive().increaseRemainingSize(currentLocation.getSubFileByName(name).getSize());
        currentLocation.deleteFile(name);
        return true;
    }

    public void deleteFolder(String name) {
        if (!currentLocation.isThereFolderWithName(name)) {
            System.out.println("invalid name");
            return;
        }
        int size = currentLocation.getSubFolderByName(name).getSizeOfSubFiles();
        currentLocation.getDrive().increaseRemainingSize(size);
        currentLocation.getSubFolderByName(name).clearSubFoldersFiles();
        currentLocation.deleteFolder(name);
        System.out.println("folder deleted");
    }

    public void renameFile(String oldName, String newName) {
        if (!currentLocation.isThereFileWithName(oldName))
            System.out.println("invalid name");
        else if (currentLocation.isThereFileWithName(newName))
            System.out.println("file exists with this name");
        else {
            currentLocation.getSubFileByName(oldName).setName(newName);
            System.out.println("file renamed");
        }
    }

    public void renameFolder(String oldName, String newName) {
        if (!currentLocation.isThereFolderWithName(oldName))
            System.out.println("invalid name");
        else if (currentLocation.isThereFolderWithName(newName))
            System.out.println("folder exists with this name");
        else {
            currentLocation.getSubFolderByName(oldName).setName(newName);
            System.out.println("folder renamed");
        }
    }

    public void status() {
        System.out.println(currentLocation.address());
        System.out.println("Folders:");
        currentLocation.showSubFolders();
        System.out.println("Files:");
        currentLocation.showSubFiles();
    }

    public void driveStatus() {
        for (Drive drive : drives) {
            System.out.println(drive.toString());
        }
    }

    private void resetClipBoard() {
        this.copyClipboardFolder.clear();
        this.copyClipBoardFile.clear();
        this.cutClipBoardFolder.clear();
        this.cutClipBoardFile.clear();
    }

    public void copyFile(ArrayList<String> names) {
        for (String name : names) {
            if (!currentLocation.isThereFileWithName(name)) {
                System.out.println("invalid name");
                return;
            }
        }
        resetClipBoard();
        for (String name : names) {
            copyClipBoardFile.add(currentLocation.getSubFileByName(name));
        }
        System.out.println("files copied");
    }

    public void cutFile(ArrayList<String> names) {
        for (String name : names) {
            if (!currentLocation.isThereFileWithName(name)) {
                System.out.println("invalid name");
                return;
            }
        }
        resetClipBoard();
        for (String name : names) {
            cutClipBoardFile.add(currentLocation.getSubFileByName(name));
        }
        System.out.println("files cut completed");

    }

    public void copyFolder(ArrayList<String> names) {
        for (String name : names) {
            if (!currentLocation.isThereFolderWithName(name)) {
                System.out.println("invalid name");
                return;
            }
        }
        resetClipBoard();
        for (String name : names) {
            copyClipboardFolder.add(currentLocation.getSubFolderByName(name));
        }
        System.out.println("folders copied");
    }

    public void cutFolder(ArrayList<String> names) {
        for (String name : names) {
            if (!currentLocation.isThereFolderWithName(name)) {
                System.out.println("invalid name");
                return;
            }
        }
        resetClipBoard();
        for (String name : names) {
            cutClipBoardFolder.add(currentLocation.getSubFolderByName(name));
        }
        System.out.println("folders cut completed");
    }

    public void paste() {
        if (copyClipBoardFile.isEmpty() && copyClipboardFolder.isEmpty() && cutClipBoardFile.isEmpty() && cutClipBoardFolder.isEmpty())
            return;
        else if (!copyClipboardFolder.isEmpty())
            pasteCopiedFolder();
        else if (!copyClipBoardFile.isEmpty())
            pasteCopiedFile();
        else if (!cutClipBoardFolder.isEmpty())
            pasteCutFolder();
        else if (!cutClipBoardFile.isEmpty())
            pasteCutFile();

    }

    private void pasteCopiedFolder() {
        int size = 0;
        for (Folder folder : copyClipboardFolder) {
            size += folder.getSizeOfSubFiles();
            if (currentLocation.isThereFolderWithName(folder.getName())) {
                System.out.println("folder exists with this name");
                return;
            }
        }
        if (size > currentLocation.getDrive().getRemainingSize()) {
            System.out.println("insufficient drive size");
            return;
        }
        currentLocation.getDrive().decreaseRemainingSize(size);
        ArrayList<Folder> oldFolder = new ArrayList<>();
        ArrayList<Folder> newFolder = new ArrayList<>();
        ArrayList<Folder> temp = new ArrayList<>();
        ArrayList<File> subFiles = new ArrayList<>();
        for (Folder folder : copyClipboardFolder) {
            Folder head = new Folder(folder, currentLocation);
            allFoldersInComputer.add(head);
            subFiles.addAll(folder.getSubFiles());
            for (File subFile : subFiles) {
                head.addNewFile(getCopiedFile(subFile, head));
            }
            temp.addAll(folder.getAllSubFolders());
            for (Folder folder1 : temp) {
                Folder temp1 = new Folder(folder1, head);
                head.addSubFolder(temp1);
                newFolder.add(temp1);
                oldFolder.add(folder1);
            }
            currentLocation.addSubFolder(head);
            temp.clear();
            subFiles.clear();
            while (!newFolder.isEmpty()) {
                head = newFolder.get(0);
                allFoldersInComputer.add(head);
                temp.addAll(oldFolder.get(0).getSubFolders());
                subFiles.addAll(oldFolder.get(0).getSubFiles());
                oldFolder.remove(0);
                newFolder.remove(0);
                for (Folder folder1 : temp) {
                    newFolder.add(new Folder(folder1, head));
                    oldFolder.add(folder1);
                }
                for (File subFile : subFiles) {
                    head.addNewFile(getCopiedFile(subFile, head));
                }
                temp.clear();
                subFiles.clear();
            }
        }
        System.out.println("paste completed");
        resetClipBoard();
    }

    private void pasteCopiedFile() {
        int size = 0;
        for (File file : copyClipBoardFile) {
            size += file.getSize();
            if (currentLocation.isThereFileWithName(file.getName())) {
                System.out.println("file exists with this name");
                return;
            }
        }
        if (size > currentLocation.getDrive().getRemainingSize()) {
            System.out.println("insufficient drive size");
            return;
        }
        currentLocation.getDrive().decreaseRemainingSize(size);
        for (File file : copyClipBoardFile) {
            currentLocation.addNewFile(getCopiedFile(file,currentLocation));
        }
        resetClipBoard();
        System.out.println("paste completed");
    }

    private void pasteCutFolder() {
        int size = 0;
        for (Folder folder : cutClipBoardFolder) {
            size += folder.getSizeOfSubFiles();
            if (currentLocation.isThereFolderWithName(folder.getName())) {
                System.out.println("folder exists with this name");
                return;
            }
        }
        if (size > currentLocation.getDrive().getRemainingSize()) {
            System.out.println("insufficient drive size");
            return;
        }
        currentLocation.getDrive().decreaseRemainingSize(size);
        cutClipBoardFolder.get(0).getDrive().increaseRemainingSize(size);
        for (Folder folder : cutClipBoardFolder) {
            folder.getParent().deleteFolder(folder.getName());
            folder.setParent(currentLocation);
            ArrayList<Folder> allSubFolders = folder.getAllSubFolders();
            for (Folder subFolder : allSubFolders) {
                subFolder.resetVisited();
            }
            currentLocation.addSubFolder(folder);
            folder.resetVisited();
        }
        resetClipBoard();
        System.out.println("paste completed");
    }

    private void pasteCutFile() {
        int size = 0;
        for (File file : cutClipBoardFile) {
            size += file.getSize();
            if (currentLocation.isThereFileWithName(file.getName())) {
                System.out.println("file exists with this name");
                return;
            }
        }
        if (size > currentLocation.getDrive().getRemainingSize()) {
            System.out.println("insufficient drive size");
            return;
        }
        currentLocation.getDrive().decreaseRemainingSize(size);
        cutClipBoardFile.get(0).getParent().getDrive().increaseRemainingSize(size);
        for (File file : cutClipBoardFile) {
            file.getParent().deleteFile(file.getName());
            file.setParent(currentLocation);
            currentLocation.addNewFile(file);
        }
        resetClipBoard();
        System.out.println("paste completed");
    }

    private File getCopiedFile(File file, Keeper parent) {
        if (file instanceof Text) {
            return new Text((Text) file, parent);
        } else if (file instanceof Video) {
            return new Video((Video) file, parent);
        } else if (file instanceof Image) {
            return new Image((Image) file, parent);
        }
        return null;
    }

    public void fileStats(String name) {
        if (!currentLocation.isThereFileWithName(name)) {
            System.out.println("invalid name");
            return;
        }
        File file = currentLocation.getSubFileByName(name);
        System.out.println(file.getName() + " " + file.getType());
        System.out.println(file.address());
        System.out.println("Size: " + file.getSize() + "MB");
        if (file instanceof Text) {
            System.out.println("Text: " + ((Text) file).getText());
        } else if (file instanceof Video) {
            System.out.println("Quality: " + ((Video) file).getQuality());
            System.out.println("Video Length: " + ((Video) file).getVideoLength());
        } else {
            System.out.println("Resolution: " + ((Image) file).getResolution());
            System.out.println("Extension: " + ((Image) file).getExtension());
        }
    }

    public void writeText(String name, Scanner scanner) {
        if (!currentLocation.isThereFileWithName(name)) {
            System.out.println("invalid name");
            return;
        } else if (!(currentLocation.getSubFileByName(name) instanceof Text)) {
            System.out.println("this file is not a text file");
        } else {
            ((Text) currentLocation.getSubFileByName(name)).setText(scanner.nextLine());
            return;
        }
    }

    public void frequentFolders() {
        Collections.sort(allFoldersInComputer, new SortFolders());
        int count = 0;
        for (Folder folder : allFoldersInComputer) {
            if (folder.getVisitedCount() > 0 && count < 5) {
                System.out.println(folder.address() + " " + folder.getVisitedCount());
                count += 1;
            } else if (count == 5)
                return;
            if (folder.getVisitedCount() == 0)
                return;
        }
    }

    @Override
    public String toString() {
        return "OS is " + this.operatingSystemName + " " + this.version;
    }

    static class SortFolders implements Comparator<Folder> {
        @Override
        public int compare(Folder folder1, Folder folder2) {
            int nameCompare = folder1.address().compareToIgnoreCase(folder2.address());
            int compareVisited = folder2.getVisitedCount().compareTo(folder1.getVisitedCount());
            if (compareVisited == 0)
                return nameCompare;
            else
                return compareVisited;
        }
    }

}
