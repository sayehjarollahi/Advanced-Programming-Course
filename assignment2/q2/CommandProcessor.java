import java.util.ArrayList;
import java.util.Scanner;

public class CommandProcessor {
    private Scanner scanner = new Scanner(System.in);
    private String input;
    public Computer computer;

    public void setup() {
        while (true) {
            input = scanner.nextLine().trim();
            String[] splitInput = input.split(" ");
            if (input.matches("install OS (\\S+) (\\S+)")) {
                computer = new Computer(splitInput[2], splitInput[3]);
                break;
            } else
                System.out.println("invalid command");
        }
        while (true) {
            input = scanner.nextLine().trim();
            String[] splitInput = input.split(" ");
            if (input.matches("(\\d+) (\\d+)")) {
                getDrives(Integer.parseInt(splitInput[0]), Integer.parseInt(splitInput[1]));
                this.run();
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void run() {
        while (true) {
            input = scanner.nextLine().trim();
            String[] splitInput = input.split(" ");
            if (input.matches("open (\\S+)")) {
                if (!computer.openFolder(splitInput[1]))
                    System.out.println("invalid name");
            } else if (input.matches("go to drive (\\S+)")) {
                if (!computer.changeDrive(splitInput[3]))
                    System.out.println("invalid name");
            } else if (input.matches("back")) {
                computer.back();
            } else if (input.matches("create folder (\\S+)")) {
                if (computer.createFolder(splitInput[2]))
                    System.out.println("folder created");
                else
                    System.out.println("folder exists with this name");
            } else if (input.matches("create file (\\S+) (\\S+) (\\d+)")) {
                if (computer.canCreateFile(splitInput[2], splitInput[3], Integer.parseInt(splitInput[4])))
                    createDifferentFiles(splitInput[2], splitInput[3], Integer.parseInt(splitInput[4]));
            } else if (input.matches("delete file (\\S+)")) {
                if (computer.deleteFile(splitInput[2]))
                    System.out.println("file deleted");
                else
                    System.out.println("invalid name");
            } else if (input.matches("delete folder (\\S+)")) {
                computer.deleteFolder(splitInput[2]);
            } else if (input.matches("rename file (\\S+) (\\S+)")) {
                computer.renameFile(splitInput[2], splitInput[3]);
            } else if (input.matches("rename folder (\\S+) (\\S+)")) {
                computer.renameFolder(splitInput[2], splitInput[3]);
            } else if (input.matches("status")) {
                computer.status();
            } else if (input.matches("print drives status")) {
                computer.driveStatus();
            } else if (input.matches("copy file(\\s\\S+)+")) {
                computer.copyFile(getNamesOutOfInput(input));
            } else if (input.matches("cut file(\\s\\S+)+")) {
                computer.cutFile(getNamesOutOfInput(input));
            } else if (input.matches("cut folder(\\s\\S+)+")) {
                computer.cutFolder(getNamesOutOfInput(input));
            } else if (input.matches("copy folder(\\s\\S+)+")) {
                computer.copyFolder(getNamesOutOfInput(input));
            } else if (input.matches("paste")) {
                computer.paste();
            } else if (input.matches("print file stats (\\S+)")) {
                computer.fileStats(splitInput[3]);
            } else if (input.matches("write text (\\S+)")) {
                computer.writeText(splitInput[2], scanner);
            } else if (input.matches("print frequent folders")) {
                computer.frequentFolders();
            } else if (input.matches("print OS information")) {
                System.out.println(computer.toString());
            } else if (input.matches("end")) {
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }


    private void getDrives(int size, int count) {
        int drivesCreated = 0;
        int totalSize = 0;
        ArrayList<String> name = new ArrayList<>();
        computer.setHardSize(size);
        while (drivesCreated != count) {
            input = scanner.nextLine().trim();
            String[] splitInput = input.split(" ");
            if (input.matches("(\\S+) (\\d+)") && !input.matches("[A-Z] (\\d+)")) {
                System.out.println("invalid name");
            } else if (input.matches("[A-Z] (\\d+)") && name.contains(splitInput[0])) {
                System.out.println("invalid name");
            } else if (input.matches("[A-Z] (\\d+)") && totalSize + Integer.parseInt(splitInput[1]) > size) {
                System.out.println("insufficient hard size");
            } else if (input.matches("[A-Z] (\\d+)") && !name.contains(splitInput[0])) {
                totalSize += Integer.parseInt(splitInput[1]);
                drivesCreated += 1;
                name.add(splitInput[0]);
                computer.setDrive(splitInput[0], Integer.parseInt(splitInput[1]));
            } else
                System.out.println("invalid command");

        }
        computer.setCurrentDriveFirstTime();
    }

    private void createDifferentFiles(String name, String format, int size) {
        if (format.equals("img")) {
            System.out.println("Resolution:");
            String resolution = scanner.nextLine();
            System.out.println("Extension:");
            computer.createNewPhoto(name, resolution, scanner.nextLine(), size);
        } else if (format.equals("mp4")) {
            System.out.println("Quality:");
            String quality = scanner.nextLine();
            System.out.println("Video Length:");
            computer.createNewVideo(name, quality, scanner.nextLine(), size);
        } else {
            System.out.println("Text:");
            String text = scanner.nextLine();
            computer.createNewText(name, text, size);
        }
        System.out.println("file created");

    }

    private ArrayList<String> getNamesOutOfInput(String input) {
        String[] splitInput = input.split(" ");
        ArrayList<String> result = new ArrayList<>();
        int size = splitInput.length;
        for (int i = 2; i < size; i++)
            result.add(splitInput[i]);
        return result;
    }
}
